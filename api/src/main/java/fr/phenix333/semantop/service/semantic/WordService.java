package fr.phenix333.semantop.service.semantic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.phenix333.logger.MyLogger;
import fr.phenix333.semantop.exception.semantic.WordNotFoundException;
import fr.phenix333.semantop.exception.semantic.WordSearchAlreadyExistException;
import fr.phenix333.semantop.exception.semantic.WordSearchNotFoundException;
import fr.phenix333.semantop.model.semantic.SimilarWord;
import fr.phenix333.semantop.model.semantic.Word;
import fr.phenix333.semantop.model.semantic.WordSearch;
import fr.phenix333.semantop.repository.semantic.WordRepository;
import fr.phenix333.semantop.repository.semantic.WordSearchRepository;
import jakarta.annotation.PostConstruct;

@Service
public class WordService {

	private static final MyLogger L = MyLogger.create(WordService.class);

	private WordRepository wordRepository;

	private WordSearchRepository wordSearchRepository;

	private WordVectors wordVectors = null;

	private List<String> vocabularies = null;

	@Value("${model.path}")
	private String modelPath;

	@Autowired
	public WordService(WordRepository wordRepository, WordSearchRepository wordSearchRepository) {
		this.wordRepository = wordRepository;
		this.wordSearchRepository = wordSearchRepository;
	}

	@PostConstruct
	private void initModel() {
		File modelFile = new File(this.modelPath);

		if (!modelFile.exists()) {
			throw new RuntimeException(String.format("Model file not found at: %s", modelFile.getAbsolutePath()));
		}

		this.wordVectors = WordVectorSerializer.readWord2VecModel(modelFile, true);
		this.vocabularies = new ArrayList<>(this.wordVectors.vocab().words());
	}

	/**
	 * Retrieves a random word from the vocabulary. If the word does not exist in
	 * the database, it will be created and saved.
	 * 
	 * @return Word -> a randomly selected Word object.
	 */
	private Word getRandomWord() {
		L.function("Getting a random word from the vocabulary");

		int randomIndex = (int) (Math.random() * this.vocabularies.size());
		String randomWordName = this.vocabularies.get(randomIndex);

		Word word = this.wordRepository.findWordByName(randomWordName);

		if (word == null) {
			word = new Word();
			word.setName(randomWordName);
			this.wordRepository.save(word);
		}

		return word;
	}

	/**
	 * Finds the top 999 similar words for a given word name.
	 * 
	 * @param name -> String : the name of the word to find similar words for.
	 * 
	 * @return List<SimilarWord> -> a list of SimilarWord objects containing the
	 *         similar words, their similarity scores, and their places.
	 */
	private List<SimilarWord> find999SimilarWords(String name) {
		L.function("Finding 999 similar words | name: {}", name);

		List<String> similarWordNames = (List<String>) this.wordVectors.wordsNearest(name, 999);
		List<SimilarWord> similarWords = new ArrayList<>();

		for (String word : similarWordNames) {
			double sim = this.wordVectors.similarity(name, word);
			int place = similarWordNames.indexOf(word) + 2;
			similarWords.add(new SimilarWord(word, sim, place));
		}

		return similarWords;
	}

	/**
	 * Generates a new WordSearch object based on the provided name and reference.
	 * If the name is null or empty, a random word will be selected.
	 * 
	 * @param name -> String : the name of the word to search for.
	 * @param ref  -> String : the reference for the word search.
	 * 
	 * @return String -> the reference of the created WordSearch.
	 * 
	 * @throws WordNotFoundException
	 * @throws WordSearchAlreadyExistException
	 */
	public String generateNewWordSearch(String name, String ref)
			throws WordNotFoundException, WordSearchAlreadyExistException {
		L.function("Generating new WordSearch | word: {}, ref: {}", name, ref);

		WordSearch wordSearch = this.wordSearchRepository.findWordSearchByRef(ref);

		if (wordSearch != null) {
			L.warn("Found existing WordSearch for ref: {}", ref);

			throw new WordSearchAlreadyExistException(String.format("WordSearch already exists for ref: %s", ref));
		}

		Word word = null;

		if (name == null || name.isEmpty()) {
			word = this.getRandomWord();
		} else {
			word = this.wordRepository.findWordByName(name);

			if (word == null) {
				if (!this.vocabularies.contains(name)) {
					L.error("Word not found in vocabulary: {}", name);

					throw new WordNotFoundException(String.format("Word not found in vocabulary: %s", name));
				}

				word = new Word();
				word.setName(name);
				this.wordRepository.save(word);
			}
		}

		List<SimilarWord> similarWords = this.find999SimilarWords(word.getName());
		similarWords.add(new SimilarWord(word.getName(), 1.0, 1));

		wordSearch = new WordSearch();
		wordSearch.setWord(word);
		wordSearch.setRef(ref);
		wordSearch.setSimilarWords(similarWords);

		this.wordSearchRepository.save(wordSearch);

		L.info("Created new WordSearch: {}", wordSearch);

		return ref;
	}

	/**
	 * Retrieves the similarity place between two words.
	 * 
	 * @param ref  -> String : the reference for the word search.
	 * @param name -> String : the name of the word to find similar words for.
	 * 
	 * @return SimilarWord -> the SimilarWord object containing the similarity
	 *         information.
	 * 
	 * @throws WordSearchNotFoundException
	 * @throws WordNotFoundException
	 */
	public SimilarWord getSimilarityPlaceBetweenWord(String ref, String name)
			throws WordSearchNotFoundException, WordNotFoundException {
		L.function("Getting similarity between words | ref: {}, name: {}", ref, name);

		WordSearch wordSearch = this.wordSearchRepository.findWordSearchByRef(ref);

		if (wordSearch == null) {
			L.error("WordSearch not found for ref: {}", ref);

			throw new WordSearchNotFoundException(String.format("WordSearch not found for ref: %s", ref));
		}

		if (!this.vocabularies.contains(name)) {
			L.error("Word not found in vocabulary: {}", name);

			throw new WordNotFoundException(String.format("Word not found in vocabulary: %s", name));
		}

		SimilarWord similarWord = wordSearch.getSimilarWords().stream().filter(sw -> sw.getName().equals(name))
				.findFirst().orElse(null);

		if (similarWord == null) {
			similarWord = new SimilarWord();
			similarWord.setName(name);
			similarWord.setSimilarityScore(this.wordVectors.similarity(wordSearch.getWord().getName(), name));
			similarWord.setPlace(-1);
			wordSearch.getSimilarWords().add(similarWord);
			this.wordSearchRepository.save(wordSearch);
		}

		if (similarWord.getPlace() == 1) {
			wordSearch.setNbFound(wordSearch.getNbFound() + 1);
			this.wordSearchRepository.save(wordSearch);
		}

		return similarWord;
	}

}
