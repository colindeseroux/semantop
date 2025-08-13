package fr.phenix333.semantop.service.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import fr.phenix333.logger.MyLogger;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtUtilService {

	private static final MyLogger L = MyLogger.create(JwtUtilService.class);

	private final SecretKey secretKey;

	@Value("${jwt.expiration}")
	private int jwtExpiration;

	public String getIdFromToken(String token) {
		L.function("token : {}", token);

		return this.getClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * Extracts the user ID from the Authorization header.
	 *
	 * @param authorization -> String : the Authorization header
	 * 
	 * @return String -> The user ID
	 */
	public String getIdFromAuthorization(String authorization) {
		L.function("authorization : {}", authorization);

		String token = authorization.replace("Bearer ", "");

		return this.getClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * Gets the expiration date from the JWT token.
	 *
	 * @param token -> String : the JWT token
	 * 
	 * @return Date -> The expiration date
	 */
	public Date getExpirationDateFromToken(String token) {
		L.function("token : {}", token);

		return this.getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * Extracts a specific claim from the JWT token.
	 * 
	 * @param <T>            -> The type of the claim to extract
	 * @param token          -> String : the JWT token
	 * @param claimsResolver -> Function<Claims, T> : a function to extract the
	 *                       claim
	 * 
	 * @return T -> The extracted claim
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		L.function("token : {}", token);

		Claims claims = this.getAllClaimsFromToken(token);

		return claimsResolver.apply(claims);
	}

	/**
	 * Extracts all claims from the JWT token.
	 *
	 * @param token -> String : the JWT token
	 * 
	 * @return Claims -> The extracted claims
	 */
	private Claims getAllClaimsFromToken(String token) {
		L.function("token : {}", token);

		return Jwts.parser().verifyWith(this.secretKey).build().parseSignedClaims(token).getPayload();
	}

	/**
	 * Checks if the JWT token has expired.
	 *
	 * @param token -> String : the JWT token
	 * 
	 * @return Boolean -> true if the token has expired, false otherwise
	 */
	private Boolean isTokenExpired(String token) {
		L.function("token : {}", token);

		Date expiration = this.getExpirationDateFromToken(token);

		return expiration.before(new Date());
	}

	/**
	 * Generates a JWT token for the given user ID.
	 *
	 * @param userId -> String : the user ID
	 * 
	 * @return String -> The generated JWT token
	 */
	public String generateToken(String userId) {
		L.function("userId : {}", userId);

		Map<String, Object> claims = new HashMap<>();

		return this.doGenerateToken(claims, userId);
	}

	/**
	 * Generates a JWT token with the specified claims and subject.
	 *
	 * @param claims  -> Map<String, Object> : the claims to include in the token
	 * @param subject -> String : the subject of the token (usually the user ID)
	 * 
	 * @return String -> The generated JWT token
	 */
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		L.function("subject : {}", subject);

		return Jwts.builder().claims(claims).subject(subject).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + this.jwtExpiration * 1000)).signWith(this.secretKey)
				.compact();
	}

	/**
	 * Validates the JWT token against the user details.
	 *
	 * @param token -> String : the JWT token
	 * @param user  -> UserDetails : the user details to validate against
	 * 
	 * @return Boolean -> true if the token is valid, false otherwise
	 */
	public Boolean validateToken(String token, UserDetails user) {
		L.function("token : {}", token);

		String id = this.getIdFromToken(token);

		return (id.equals(user.getUsername()) && !this.isTokenExpired(token));
	}

}
