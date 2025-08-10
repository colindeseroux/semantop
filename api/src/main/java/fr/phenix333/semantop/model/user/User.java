package fr.phenix333.semantop.model.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users", indexes = { @Index(columnList = "email"), @Index(columnList = "pseudo") })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true, nullable = false)
	private String pseudo;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password = "*****";

	@Column(nullable = false)
	private int loginAttempts = -333;

	public void setPassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}

	public void setPasswordWithoutBCrypt(String password) {
		this.password = password;
	}

	public int addLoginAttempts() {
		return ++this.loginAttempts;
	}

}
