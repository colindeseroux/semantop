package fr.phenix333.semantop.service.user;

import java.io.IOException;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.phenix333.logger.MyLogger;
import fr.phenix333.semantop.exception.IncorrectException;
import fr.phenix333.semantop.exception.NotFoundException;
import fr.phenix333.semantop.model.user.User;
import fr.phenix333.semantop.repository.user.UserRepository;
import fr.phenix333.semantop.service.security.VerificationCodeService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private static final MyLogger L = MyLogger.create(UserService.class);

	private static final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

	private final UserRepository userRepository;

	private final VerificationCodeService verificationCodeService;

	/**
	 * Validates the user object.
	 *
	 * @param user -> User : the user to validate
	 * 
	 * @return boolean -> true if the user is valid
	 * 
	 * @throws IncorrectException if the email is incorrect
	 */
	private boolean properUser(User user) throws IncorrectException {
		L.function("email : {}", user.getEmail());

		if (!user.getEmail().matches(emailRegex)) {
			L.error("IncorrectEmail : {}", user.getEmail());

			throw new IncorrectException("IncorrectEmail");
		}

		return true;
	}

	/**
	 * Gets a user by their email.
	 *
	 * @param email -> String : the email of the user
	 * 
	 * @return User -> The user with the specified email
	 * 
	 * @throws NotFoundException if no user is found with the specified email
	 */
	public User getUserByEmail(String email) throws NotFoundException {
		L.function("email : {}", email);

		User user = this.userRepository.findUserByEmail(email);

		if (user == null) {
			L.error("IncorrectEmail : {}", email);

			throw new NotFoundException("IncorrectEmail");
		}

		return user;
	}

	/**
	 * Gets a user by their ID.
	 *
	 * @param id -> String : the ID of the user
	 * 
	 * @return User -> The user with the specified ID
	 * 
	 * @throws NotFoundException if no user is found with the specified ID
	 */
	public User getUserById(String id) throws NotFoundException {
		L.function("id : {}", id);

		long idConverted = Long.parseLong(id);

		User user = this.userRepository.findUserById(idConverted);

		if (user == null) {
			L.error("IncorrectId : {}", id);

			throw new NotFoundException("IncorrectId");
		}

		return user;
	}

	/**
	 * Signs up a new user.
	 *
	 * @param user -> User : the user to sign up
	 *
	 * @return String -> A message indicating the result of the signup
	 *
	 * @throws IncorrectException              if the user is not valid
	 * @throws MessagingException              if an error occurs while sending the
	 *                                         email
	 * @throws IOException                     if an error occurs while processing
	 *                                         the user data
	 * @throws DataIntegrityViolationException if there is a database integrity
	 *                                         violation
	 */
	@Transactional
	public String signup(User user)
			throws IncorrectException, MessagingException, IOException, DataIntegrityViolationException {
		L.function("email : {}", user.getEmail());

		String randomUserId = SecurityContextHolder.getContext().getAuthentication().getName();

		user.setId(Long.valueOf(randomUserId));

		this.properUser(user);
		this.checkUniqueness(user);

		this.verificationCodeService.createAndSendVerificationCode(user.getEmail(), "new_account");

		this.userRepository.save(user);

		return "VerificationCodeSentToEmail";
	}

	/**
	 * Verify the uniqueness of the user's email and pseudo.
	 *
	 * @param user -> User : the user to check
	 *
	 * @return boolean -> true if the email and pseudo are unique
	 */
	private boolean checkUniqueness(User user) throws DataIntegrityViolationException {
		L.function("email : {}, pseudo : {}", user.getEmail(), user.getPseudo());

		if (this.userRepository.existsByEmail(user.getEmail())) {
			throw new DataIntegrityViolationException("duplicate key : Key (email)=");
		}

		if (this.userRepository.existsByPseudo(user.getPseudo())) {
			throw new DataIntegrityViolationException("duplicate key : Key (pseudo)=");
		}

		return true;
	}

	/**
	 * Changes the password of a user.
	 *
	 * @param user -> User : the user whose password is to be changed
	 * 
	 * @return String -> "VerificationCodeSentToEmail"
	 * 
	 * @throws NotFoundException  if the user is not found
	 * @throws MessagingException if an error occurs while sending the email
	 * @throws IOException        if an error occurs while processing the user data
	 */
	@Transactional
	public String passwordForgotten(String email) throws NotFoundException, MessagingException, IOException {
		L.function("email : {}", email);

		User user = this.getUserByEmail(email);

		user.setPasswordWithoutBCrypt("*****");
		user.setLoginAttempts(-1);

		this.userRepository.save(user);

		this.verificationCodeService.createAndSendVerificationCode(email, "password_forgotten");

		return "VerificationCodeSentToEmail";
	}

	/**
	 * Changes the password of a user using a verification code.
	 *
	 * @param email    -> String : the email of the user
	 * @param code     -> String : the verification code
	 * @param password -> String : the new password
	 * 
	 * @return String -> "OK"
	 * 
	 * @throws IncorrectException if the verification code is incorrect or expired
	 * @throws MessagingException if an error occurs while sending the email
	 * @throws IOException        if an error occurs while processing the email
	 */
	@Transactional
	public String newPassword(String email, String code, String password)
			throws IncorrectException, MessagingException, IOException {
		L.function("email : {}", email);

		this.verificationCodeService.correctVerificationCode(email, code);

		User user = this.userRepository.findUserByEmail(email);

		user.setPassword(password);
		user.setLoginAttempts(0);

		this.userRepository.save(user);

		return "OK";
	}

	/**
	 * Validates the user account using a verification code.
	 *
	 * @param email -> String : the email of the user
	 * @param code  -> String : the verification code
	 * 
	 * @return String -> "OK"
	 * 
	 * @throws IncorrectException if the verification code is incorrect or expired
	 * @throws MessagingException if an error occurs while sending the email
	 * @throws NotFoundException  if no user is found with the specified email
	 * @throws IOException        if an error occurs while processing the email
	 */
	@Transactional
	public String validateAccount(String email, String code)
			throws IncorrectException, MessagingException, NotFoundException, IOException {
		L.function("email : {}", email);

		User user = this.getUserByEmail(email);

		this.verificationCodeService.correctVerificationCode(email, code);

		user.setLoginAttempts(0);

		this.userRepository.save(user);

		return "OK";
	}

	/**
	 * Handles login attempts for a user.
	 *
	 * @param email -> String : the email of the user
	 * 
	 * @return User -> The updated user with login attempts incremented
	 * 
	 * @throws IncorrectException if the number of login attempts exceeds the limit
	 * @throws NotFoundException  if no user is found with the specified email
	 */
	@Transactional
	public User loginAttempts(String email) throws IncorrectException, NotFoundException {
		L.function("email : {}", email);

		User user = this.getUserByEmail(email);

		if (user.getLoginAttempts() == -1) {
			L.error("The user '{}' must change their password.", email);

			throw new IncorrectException("ChangePassword");
		}

		if (user.getLoginAttempts() == 5) {
			L.error("The number of connection errors has been exceeded for {}", email);

			user.setLoginAttempts(-1);

			this.userRepository.save(user);

			throw new IncorrectException("ChangePassword");
		}

		if (user.getLoginAttempts() == -333) {
			L.error("Validation is required for {}", email);

			throw new IncorrectException("AccountLocked");
		}

		user.addLoginAttempts();

		return this.userRepository.save(user);
	}

	/**
	 * Resets the login attempts for a user.
	 *
	 * @param user -> User : the user whose login attempts are to be reset
	 * 
	 * @return User -> The updated user with login attempts reset
	 */

	@Transactional
	public User resetLoginAttempts(User user) {
		L.function("email : {}", user.getEmail());

		user.setLoginAttempts(0);

		return this.userRepository.save(user);
	}

	/**
	 * Creates a new user with a random pseudo and password.
	 *
	 * @return User -> The newly created user
	 */
	public User createUser() {
		L.function("");

		String uuid = UUID.randomUUID().toString();

		User user = this.saveRandomUser(uuid);

		user.setPasswordWithoutBCrypt(uuid);

		return user;
	}

	/**
	 * Saves a random user with a generated UUID as the password.
	 * 
	 * @param uuid -> String : the UUID to use as the password
	 * 
	 * @return User -> The newly created user
	 */
	@Transactional
	public User saveRandomUser(String uuid) {
		L.function("uuid : {}", uuid);

		User user = new User();
		user.setPseudo(this.userRepository.getNextPseudo());
		user.setEmail(String.format("%s@random.fr", user.getPseudo()));
		user.setPassword(uuid);
		user.setLoginAttempts(0);

		return this.userRepository.save(user);
	}

}
