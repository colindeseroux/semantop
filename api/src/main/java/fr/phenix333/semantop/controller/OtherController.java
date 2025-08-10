package fr.phenix333.semantop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.phenix333.logger.MyLogger;

@Controller
@RequestMapping
public class OtherController {

	private static final MyLogger L = MyLogger.create(OtherController.class);

	@GetMapping({ "/hello", "/api/hello" })
	public ResponseEntity<String> helloWorld() {
		L.function("Hello World!");

		return ResponseEntity.status(HttpStatus.OK).body("Hello World!");
	}

	/**
	 * Endpoint to serve the Swagger UI HTML file. Visible only on localhost (with
	 * my nginx in production, I only redirect to /api.)
	 *
	 * @return String -> Redirects to the static swagger.html file
	 */
	@GetMapping({ "/swagger", "/public" })
	public String redirectToSwagger() {
		return "redirect:/swagger.html";
	}

}
