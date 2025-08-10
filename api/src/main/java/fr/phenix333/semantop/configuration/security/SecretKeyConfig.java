package fr.phenix333.semantop.configuration.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretKeyConfig {

	@Value("${jwt.secret}")
	private String secret;

	@Bean
	SecretKey secretKey() {
		byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);

		SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA512");
		return secretKey;
	}

}
