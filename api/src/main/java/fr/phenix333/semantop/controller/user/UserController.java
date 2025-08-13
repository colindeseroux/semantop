package fr.phenix333.semantop.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.phenix333.logger.MyLogger;
import fr.phenix333.semantop.exception.HandleException;
import fr.phenix333.semantop.model.user.User;
import fr.phenix333.semantop.service.user.UserService;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private static final MyLogger L = MyLogger.create(UserController.class);

	private final UserService userService;

	private final HandleException handleException;

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
	 * Create random user.
	 * 
	 * @return ResponseEntity<User>
	 */
	@PostMapping
	public ResponseEntity<User> createUser() {
		L.function("");

		return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.createUser());
	}

}
