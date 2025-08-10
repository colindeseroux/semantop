package fr.phenix333.semantop.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.phenix333.logger.MyLogger;
import fr.phenix333.semantop.exception.NotFoundException;
import fr.phenix333.semantop.model.security.CustomUserDetails;
import fr.phenix333.semantop.model.user.User;
import fr.phenix333.semantop.service.user.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final MyLogger L = MyLogger.create(CustomUserDetailsService.class);

	private final UserService userService;

	public CustomUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		L.function("email : {}", email);

		try {
			User user = this.userService.getUserByEmail(email);

			return CustomUserDetails.build(user);
		} catch (NotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}

	/**
	 * Same as {@link #loadUserByUsername(String)} but using the user ID instead of
	 * the email.
	 *
	 * @param id -> String : the user ID
	 * 
	 * @return UserDetails -> the user details
	 * 
	 * @throws UsernameNotFoundException if the user could not be found or the user
	 *                                   has no GrantedAuthority
	 */
	public UserDetails loadUserById(String id) throws UsernameNotFoundException {
		L.function("id : {}", id);

		try {
			User user = this.userService.getUserById(id);

			return CustomUserDetails.build(user);
		} catch (NotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}

}
