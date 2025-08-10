package fr.phenix333.semantop.service.auth;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import fr.phenix333.logger.MyLogger;
import fr.phenix333.semantop.exception.IncorrectException;
import fr.phenix333.semantop.exception.NotFoundException;
import fr.phenix333.semantop.model.user.User;
import fr.phenix333.semantop.service.security.JwtUtilService;
import fr.phenix333.semantop.service.user.UserService;
import jakarta.mail.MessagingException;

@Service
public class AuthService {

	private static final MyLogger L = MyLogger.create(AuthService.class);

	private UserService userService;

	private AuthenticationManager authenticationManager;

	private JwtUtilService jwtUtilService;

	public AuthService(UserService userService, AuthenticationManager authenticationManager,
			JwtUtilService jwtUtilService) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtUtilService = jwtUtilService;
	}

	/**
	 * Authenticates a user with the provided email and password.
	 * 
	 * @param email    -> String : the email of the user
	 * @param password -> String : the password of the user
	 * 
	 * @return String -> JSON string containing user ID, pseudo, and JWT token
	 * 
	 * @throws AuthenticationException if authentication fails
	 * @throws BadCredentialsException if the credentials are invalid
	 * @throws IncorrectException      if the user is not validated or the
	 *                                 credentials are incorrect
	 * @throws LockedException         if the user account is locked
	 * @throws NotFoundException       if no user is found with the specified email
	 */
	public String login(String email, String password) throws AuthenticationException, BadCredentialsException,
			IncorrectException, LockedException, NotFoundException {
		L.function("email : {}", email);

		User user = this.userService.loginAttempts(email);

		this.authenticate(email, password);

		String token = this.jwtUtilService.generateToken(String.valueOf(user.getId()));

		this.userService.resetLoginAttempts(user);

		JSONObject response = new JSONObject();

		response.put("id", user.getId());
		response.put("pseudo", user.getPseudo());
		response.put("token", token);

		return response.toString();
	}

	/**
	 * Authenticates a user with the provided email and password.
	 * 
	 * @param email    -> String : the email of the user
	 * @param password -> String : the password of the user
	 * 
	 * @return boolean -> true if authentication is successful, false otherwise
	 * 
	 * @throws AuthenticationException if authentication fails
	 * @throws BadCredentialsException if the credentials are invalid
	 * @throws LockedException         if the user account is locked
	 */
	private boolean authenticate(String email, String password)
			throws AuthenticationException, BadCredentialsException, LockedException {
		L.function("email : {}", email);

		this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		return true;
	}

	/**
	 * Validates the user's account using the provided email and verification code.
	 * 
	 * @param email -> String : the email of the user
	 * @param code  -> String : the verification code
	 * 
	 * @return String -> "OK"
	 * 
	 * @throws IncorrectException if the verification code is incorrect or expired
	 * @throws IOException        if an error occurs while processing the email
	 * @throws MessagingException if an error occurs while sending the email
	 * @throws NotFoundException  if no user is found with the specified email
	 */
	public String validateAccount(String email, String code)
			throws IncorrectException, IOException, MessagingException, NotFoundException {
		L.function("email : {}", email);

		return this.userService.validateAccount(email, code);
	}

	/**
	 * Authenticates a user with the provided email and password, and returns a JWT
	 * token.
	 * 
	 * @param email    -> String : the email of the user
	 * @param password -> String : the password of the user
	 * 
	 * @return String -> JSON string containing user ID, pseudo, and JWT token
	 * 
	 * 
	 * 
	 * @throws AuthenticationException if authentication fails
	 * @throws BadCredentialsException if the credentials are invalid
	 * @throws IncorrectException      if the user is not validated or the
	 *                                 credentials are incorrect
	 * @throws LockedException         if the user account is locked
	 * @throws NotFoundException       if no user is found with the specified email
	 */
	public String authentication(String email, String password) throws AuthenticationException, BadCredentialsException,
			IncorrectException, LockedException, NotFoundException {
		L.function("email : {}", email);

		return this.login(email, password);
	}

	/**
	 * Handles the password forgotten process by sending a verification code to the
	 * user's email.
	 * 
	 * @param email -> String : the email of the user
	 * 
	 * @return String -> "VerificationCodeSentToEmail"
	 * 
	 * @throws IOException        if an error occurs while processing the email
	 *                            template
	 * @throws MessagingException if an error occurs while sending the email
	 * @throws NotFoundException  if no user is found with the specified email
	 */
	public String passwordForgotten(String email) throws IOException, MessagingException, NotFoundException {
		L.function("email : {}", email);

		return this.userService.passwordForgotten(email);
	}

	/**
	 * Handles the new password process by updating the user's password with the
	 * provided code and new password.
	 * 
	 * @param email    -> String : the email of the user
	 * @param code     -> String : the verification code
	 * @param password -> String : the new password
	 * 
	 * @return String -> "OK"
	 * 
	 * @throws IncorrectException if the verification code is incorrect or expired
	 * @throws IOException        if an error occurs while processing the email
	 *                            template
	 * @throws MessagingException if an error occurs while sending the email
	 */
	public String newPassword(String email, String code, String password)
			throws IncorrectException, IOException, MessagingException {
		L.function("email : {}", email);

		return this.userService.newPassword(email, code, password);
	}

}
