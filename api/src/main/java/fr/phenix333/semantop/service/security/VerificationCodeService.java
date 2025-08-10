package fr.phenix333.semantop.service.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.phenix333.logger.MyLogger;
import fr.phenix333.mail.service.EmailService;
import fr.phenix333.semantop.exception.IncorrectException;
import fr.phenix333.semantop.model.security.VerificationCode;
import fr.phenix333.semantop.repository.security.VerificationCodeRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
public class VerificationCodeService {

	private static final MyLogger L = MyLogger.create(VerificationCodeService.class);

	private final VerificationCodeRepository verificationCodeRepository;

	private final EmailService emailService;

	@Autowired
	public VerificationCodeService(VerificationCodeRepository verificationCodeRepository, EmailService emailService) {
		this.verificationCodeRepository = verificationCodeRepository;
		this.emailService = emailService;
	}

	/**
	 * Generates a random 6-digit verification code.
	 *
	 * @return String -> The generated verification code
	 */
	private String generateCode() {
		L.function("");

		Random random = new Random();
		StringBuilder code = new StringBuilder();

		for (int i = 0; i < 6; i++) {
			int chiffre = random.nextInt(10);
			code.append(chiffre);
		}

		return code.toString();
	}

	/**
	 * Creates and sends a verification code to the specified email.
	 *
	 * @param email        -> String : the email address to send the code to
	 * @param templateName -> String : the name of the email template to use
	 * 
	 * @return VerificationCode -> The created verification code
	 * 
	 * @throws MessagingException if an error occurs while sending the email
	 * @throws IOException        if an error occurs while processing the email
	 *                            template
	 */
	@Transactional
	public VerificationCode createAndSendVerificationCode(String email, String templateName)
			throws MessagingException, IOException {
		L.function("email : {}, templateName : {}", email, templateName);

		VerificationCode verificationCode = this.addVerificationCode(email, 1);

		Map<String, Object> infos = new HashMap<>();
		infos.put("code", verificationCode.getCode());
		infos.put("email", email);

		this.emailService.sendEmailWithTemplate(email, templateName, infos);

		return verificationCode;
	}

	/**
	 * Adds a verification code for the specified email with a delta value.
	 *
	 * @param email -> String : the email address to associate with the code
	 * @param delta -> int : the delta value to add to the existing code (if any)
	 * 
	 * @return VerificationCode -> The added or updated verification code
	 */
	@Transactional
	public VerificationCode addVerificationCode(String email, int delta) {
		L.function("email : {}, delta {}", email, delta);

		VerificationCode oldVerificationCode = this.verificationCodeRepository.findVerificationCodeByEmail(email);

		String code = this.generateCode();
		VerificationCode verificationCode = new VerificationCode(code, email, delta);

		if (oldVerificationCode != null) {
			verificationCode.setId(oldVerificationCode.getId());
		}

		return this.verificationCodeRepository.save(verificationCode);
	}

	/**
	 * Checks if the provided verification code matches the one stored for the
	 * specified email.
	 *
	 * @param email -> String : the email address associated with the verification
	 *              code
	 * @param code  -> String : the verification code to check
	 * 
	 * @return boolean -> true if the code is correct, false otherwise
	 * 
	 * @throws IncorrectException if the verification code is incorrect or expired
	 * @throws IOException        if an error occurs while processing the email
	 *                            template
	 * @throws MessagingException if an error occurs while sending the email
	 */
	@Transactional
	public boolean correctVerificationCode(String email, String code)
			throws IncorrectException, IOException, MessagingException {
		L.function("email : {}", email);

		VerificationCode verificationCode = this.verificationCodeRepository.findVerificationCodeByEmail(email);

		if (verificationCode == null || verificationCode.getExpirationDate().before(new Date())) {
			if (verificationCode != null) {
				this.verificationCodeRepository.delete(verificationCode);
			}

			L.error("VerificationCodeExpired : email : {}", email);

			this.createAndSendVerificationCode(email, "verification_code_expired");

			throw new IncorrectException("VerificationCodeExpired");
		}

		if (!verificationCode.getCode().equals(code)) {
			L.error("IncorrectVerificationCode : email : {}", email);

			throw new IncorrectException("IncorrectVerificationCode");
		}

		this.verificationCodeRepository.delete(verificationCode);

		return true;
	}

}
