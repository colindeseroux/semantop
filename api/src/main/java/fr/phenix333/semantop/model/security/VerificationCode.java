package fr.phenix333.semantop.model.security;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "verificationCodes")
public class VerificationCode {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String code;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;

	public VerificationCode(String code, String email, int delta) {
		this.code = code;
		this.email = email;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, delta);
		this.expirationDate = calendar.getTime();
	}

}
