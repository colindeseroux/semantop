package fr.phenix333.semantop.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.phenix333.logger.MyLogger;
import fr.phenix333.semantop.exception.HandleException;
import fr.phenix333.semantop.model.user.User;
import fr.phenix333.semantop.service.auth.AuthService;
import fr.phenix333.semantop.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private static final MyLogger L = MyLogger.create(AuthController.class);

	private AuthService authService;

	private UserService userService;

	private HandleException handleException;

	public AuthController(AuthService authService, UserService userService, HandleException handleException) {
		this.authService = authService;
		this.userService = userService;
		this.handleException = handleException;
	}

	/**
	 * Handles exceptions thrown by the controller.
	 * 
	 * @param e -> Exception : the exception to handle
	 * 
	 * @return ResponseEntity<String> -> A response entity containing the status and
	 *         the message
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return this.handleException.handleException(e);
	}

	/**
	 * Validates the user's account using the provided email and verification code.
	 * 
	 * @param email -> String : the email of the user
	 * @param code  -> String : the verification code
	 * 
	 * @return ResponseEntity<String> -> "OK"
	 */
	@GetMapping("/validate")
	public ResponseEntity<String> validateAccount(@RequestParam String email, @RequestParam String code)
			throws Exception {
		L.function("email : {}", email);

		return ResponseEntity.status(HttpStatus.OK).body(this.authService.validateAccount(email, code));
	}

	/**
	 * Authenticates a user with the provided email and password.
	 * 
	 * @param email    -> String : the email of the user
	 * @param password -> String : the password of the user
	 * 
	 * @return ResponseEntity<String> -> JSON string containing user ID, pseudo, and
	 *         JWT token
	 */
	@GetMapping
	public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) throws Exception {
		L.function("email : {}", email);

		return ResponseEntity.status(HttpStatus.OK).body(this.authService.login(email, password));
	}

	/**
	 * Password forgotten endpoint.
	 * 
	 * @param email -> String : the email of the user who forgot their password.
	 * 
	 * @return ResponseEntity<String> -> "VerificationCodeSentToEmail"
	 */
	@PatchMapping
	public ResponseEntity<String> passwordForgotten(@RequestParam String email) throws Exception {
		L.function("email : {}", email);

		return ResponseEntity.status(HttpStatus.OK).body(this.authService.passwordForgotten(email));
	}

	/**
	 * Endpoint to change the user's password using a verification code.
	 * 
	 * @param email    -> String : the email of the user
	 * @param code     -> String : the verification code
	 * @param password -> String : the new password
	 * 
	 * @return ResponseEntity<String> -> "OK"
	 */
	@PutMapping
	public ResponseEntity<String> newPassword(@RequestParam String email, @RequestParam String code,
			@RequestParam String password, HttpServletRequest request) throws Exception {
		L.function("email : {}", email);

		return ResponseEntity.status(HttpStatus.OK).body(this.authService.newPassword(email, code, password));
	}

	/**
	 * Endpoint for user registration.
	 * 
	 * @param user -> User : the user to register
	 * 
	 * @return ResponseEntity<String> -> "VerificationCodeSentToEmail"
	 */
	@PostMapping
	public ResponseEntity<String> signup(@RequestBody User user) throws Exception {
		L.function("email : {}", user.getEmail());

		return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.signup(user));
	}

}
