package fr.phenix333.semantop.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Component;

import fr.phenix333.logger.MyLogger;

@Component
public class HandleException {

	private static final MyLogger L = MyLogger.create(HandleException.class);

	/**
	 * General handler for NotFoundException.
	 * 
	 * @param e -> NotFoundException : the NotFoundException
	 * 
	 * @return ResponseEntity<String> -> With NOT_FOUND status and the error message
	 */
	private ResponseEntity<String> handleNotFoundException(NotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	/**
	 * General handler for IncorrectException.
	 * 
	 * @param e -> IncorrectException : the exception to handle
	 * 
	 * @return ResponseEntity<String> -> With BAD_REQUEST status and a specific
	 *         error message
	 */
	private ResponseEntity<String> handleIncorrectException(IncorrectException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	/**
	 * General handler for MailSendException.
	 * 
	 * @param e -> MailSendException : the MailSendException
	 * 
	 * @return ResponseEntity<String> -> With BAD_REQUEST status and a specific
	 *         error message
	 */
	private ResponseEntity<String> handleMailSendingException(MailSendException e) {
		L.error("An exception is raised", e);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NotPossibleToSendEmailToThisEmailAddress");
	}

	/**
	 * General handler for MailAuthenticationException.
	 * 
	 * @param e -> MailAuthenticationException : the MailAuthenticationException
	 * 
	 * @return ResponseEntity<String> -> With BAD_REQUEST status and a specific
	 *         error message
	 */
	private ResponseEntity<String> handleMailAuthenticationException(MailAuthenticationException e) {
		L.error("An exception is raised", e);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MailAuthenticationFailed");
	}

	/**
	 * General handler for BadCredentialsException.
	 * 
	 * @param e -> BadCredentialsException : the BadCredentialsException
	 * 
	 * @return ResponseEntity<String> -> With UNAUTHORIZED status and a specific
	 *         error message
	 */
	private ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
		L.error("An exception is raised", e);

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("InvalidCredentials");
	}

	/**
	 * General handler for DataIntegrityViolationException.
	 * 
	 * @param e -> DataIntegrityViolationException : the
	 *          DataIntegrityViolationException
	 * 
	 * @return ResponseEntity<String> -> With CONFLICT status and a specific error
	 *         message
	 */
	private ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		L.error("An exception is raised", e);

		String error = e.getMessage();

		if (error.contains("duplicate key")) {
			if (error.contains("Key (email)=")) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("DuplicateKeyEmail");
			}

			if (error.contains("Key (pseudo)=")) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("DuplicateKeyPseudo");
			}

			if (error.contains("Key (ref)=")) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("DuplicateKeyRef");
			}

			if (error.contains("Key (word)=")) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("DuplicateKeyWord");
			}
		}

		return ResponseEntity.status(HttpStatus.CONFLICT).body("DataIntegrityViolation.");
	}

	/**
	 * General handler for LockedException.
	 * 
	 * @param e -> LockedException : the LockedException
	 * 
	 * @return ResponseEntity<String> -> With FORBIDDEN status and a specific error
	 *         message
	 */
	private ResponseEntity<String> handleLockedException(LockedException e) {
		L.error("An exception is raised", e);

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("AccountLocked");
	}

	/**
	 * General handler for exceptions.
	 * 
	 * @param e -> Exception : the exception to handle
	 * 
	 * @return ResponseEntity<String> -> With INTERNAL_SERVER_ERROR status and a
	 *         generic error message
	 */
	public ResponseEntity<String> handleException(Exception e) {
		if (e instanceof NotFoundException) {
			return this.handleNotFoundException((NotFoundException) e);
		}

		if (e instanceof IncorrectException) {
			return this.handleIncorrectException((IncorrectException) e);
		}

		if (e instanceof MailSendException) {
			return this.handleMailSendingException((MailSendException) e);
		}

		if (e instanceof MailAuthenticationException) {
			return this.handleMailAuthenticationException((MailAuthenticationException) e);
		}

		if (e instanceof DataIntegrityViolationException) {
			return this.handleDataIntegrityViolationException((DataIntegrityViolationException) e);
		}

		if (e instanceof BadCredentialsException) {
			return this.handleBadCredentialsException((BadCredentialsException) e);
		}

		if (e instanceof LockedException) {
			return this.handleLockedException((LockedException) e);
		}

		L.error("An exception is raised", e);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("OopsErrorPleaseContactAnAdministrator");
	}

}
