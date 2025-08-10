package fr.phenix333.semantop.model.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.phenix333.logger.MyLogger;
import fr.phenix333.semantop.model.user.User;
import lombok.Data;

@Data
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 333L;

	private static final MyLogger L = MyLogger.create(CustomUserDetails.class);

	private String id;
	private String email;
	private int loginAttempts;
	@JsonIgnore
	private String password;

	public CustomUserDetails(User user) {
		L.function("id : {}", user.getId());

		this.id = String.valueOf(user.getId());
		this.email = user.getEmail();
		this.loginAttempts = user.getLoginAttempts();
		this.password = user.getPassword();
	}

	public static CustomUserDetails build(User user) {
		L.function("id : {}", user.getId());

		return new CustomUserDetails(user);
	}

	@Override
	public String getUsername() {
		L.function("");

		return this.id;
	}

	@Override
	public boolean isAccountNonExpired() {
		L.function("");

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		L.function("");

		return this.loginAttempts != -1;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		L.function("");

		return true;
	}

	@Override
	public boolean isEnabled() {
		L.function("");

		return this.loginAttempts != -333;
	}

	@Override
	public String getPassword() {
		L.function("");

		return this.password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		L.function("");

		return List.of();
	}

}
