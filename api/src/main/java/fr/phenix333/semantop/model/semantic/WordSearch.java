package fr.phenix333.semantop.model.semantic;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "word_searchs", indexes = { @Index(name = "idx_word_search_ref", columnList = "ref"), })
public class WordSearch {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * DayWordSearch_<YYYYMMDD> / RandomWordSearch_<word name> /
	 * WordSearch_customParty ...
	 */
	@Column(nullable = false, unique = true)
	private String ref;

	@Column(nullable = false)
	private int nbFound = 0;

	@ManyToOne
	@JsonIncludeProperties({ "name" })
	private Word word;

	/**
	 * Word, similarity score, place (1000 to 1 and above -1)
	 */
	@ElementCollection
	@CollectionTable
	private List<SimilarWord> similarWords = new ArrayList<>();

	@Override
	public String toString() {
		return String.format("WordSearch(id=%d, word=%s)", this.id, this.word.getName());
	}

}
