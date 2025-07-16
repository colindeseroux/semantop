package fr.phenix333.semantop.repository.semantic;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.phenix333.semantop.model.semantic.WordSearch;

public interface WordSearchRepository extends JpaRepository<WordSearch, Long> {

	WordSearch findWordSearchByRef(String ref);

}
