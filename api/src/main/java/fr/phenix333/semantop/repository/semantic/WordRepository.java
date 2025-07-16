package fr.phenix333.semantop.repository.semantic;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.phenix333.semantop.model.semantic.Word;

public interface WordRepository extends JpaRepository<Word, Long> {

	Word findWordByName(String name);

}
