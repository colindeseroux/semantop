package fr.phenix333.semantop.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.phenix333.semantop.model.security.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

	VerificationCode findVerificationCodeByEmail(String email);

}
