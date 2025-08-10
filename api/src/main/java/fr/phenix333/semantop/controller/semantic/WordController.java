package fr.phenix333.semantop.controller.semantic;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.phenix333.logger.MyLogger;
// import fr.phenix333.semantop.model.semantic.SimilarWord;
// import fr.phenix333.semantop.service.semantic.WordService;

@RestController
@RequestMapping("/api/word")
public class WordController {

	private static final MyLogger L = MyLogger.create(WordController.class);

	// private WordService wordService;

	// public WordController(WordService wordService) {
	// this.wordService = wordService;
	// }

	// /**
	// * Generates a new word search for the given word name and reference.
	// *
	// * @param name -> String : the name of the word to search for.
	// * @param ref -> String : the reference for the word search.
	// *
	// * @return ResponseEntity<String> -> a response entity containing the status
	// and
	// * message.
	// */
	// @PostMapping
	// public ResponseEntity<String> generateNewWordSearch(@RequestParam String
	// name, @RequestParam String ref) {
	// L.function("Generating a new word search for | name: {}, ref: {}", name,
	// ref);

	// try {
	// String searchRef = this.wordService.generateNewWordSearch(name, ref);

	// return ResponseEntity.status(HttpStatus.CREATED).body(searchRef);
	// } catch (NotFoundException e) {
	// return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	// } catch (AlreadyExistException e) {
	// return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	// } catch (Exception e) {
	// L.error("Error creating word search", e);

	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	// .body("OopsErrorPlsContactAdmin");
	// }
	// }

	// /**
	// * Retrieves the similarity place between two words.
	// *
	// * @param ref -> String : the reference for the word search.
	// * @param name -> String : the name of the word to find similar words for.
	// *
	// * @return ResponseEntity<?>
	// */
	// @GetMapping
	// public ResponseEntity<?> getSimilarityPlaceBetweenWord(@RequestParam String
	// ref, @RequestParam String name) {
	// L.function("Getting similarity place between words | ref: {}, name: {}", ref,
	// name);

	// try {
	// SimilarWord similarWord = this.wordService.getSimilarityPlaceBetweenWord(ref,
	// name);

	// return ResponseEntity.status(HttpStatus.OK).body(similarWord);
	// } catch (NotFoundException e) {
	// return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	// } catch (Exception e) {
	// L.error("Error getting similarity place between words", e);

	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	// .body("OopsErrorPlsContactAdmin");
	// }
	// }

}
