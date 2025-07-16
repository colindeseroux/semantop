package fr.phenix333.semantop.model.semantic;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Embeddable
@NoArgsConstructor
public class SimilarWord {

	private String name;

	private double similarityScore;

	private int place;

}
