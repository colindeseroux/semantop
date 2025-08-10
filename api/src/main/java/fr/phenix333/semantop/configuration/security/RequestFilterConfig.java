package fr.phenix333.semantop.configuration.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import fr.phenix333.logger.MyLogger;
import fr.phenix333.semantop.service.security.CustomUserDetailsService;
import fr.phenix333.semantop.service.security.JwtUtilService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestFilterConfig extends OncePerRequestFilter {

	private static final MyLogger L = MyLogger.create(RequestFilterConfig.class);

	private CustomUserDetailsService customUserDetailsService;

	private JwtUtilService jwtUtilService;

	private AuthRequestMatcher authRequestMatcher;

	public RequestFilterConfig(CustomUserDetailsService customUserDetailsService, JwtUtilService jwtUtilService,
			AuthRequestMatcher authRequestMatcher) {
		this.customUserDetailsService = customUserDetailsService;
		this.jwtUtilService = jwtUtilService;
		this.authRequestMatcher = authRequestMatcher;
	}

	/**
	 * Parses the JWT from the Authorization header.
	 *
	 * @param request -> HttpServletRequest : the HTTP request
	 * 
	 * @return String -> The JWT as a String, or null if not found
	 */
	private String parseJwt(HttpServletRequest request) {
		L.function("");

		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}

		return null;
	}

	/**
	 * Checks if the JWT is valid.
	 * 
	 * @param jwt -> String : the JWT to validate
	 * 
	 * @return boolean -> true if the JWT is valid, false otherwise
	 */
	public boolean correctJwt(String jwt) {
		L.function("token : {}", jwt);

		if (jwt == null) {
			return false;
		}

		try {
			String id = this.jwtUtilService.getIdFromToken(jwt);

			UserDetails user = this.customUserDetailsService.loadUserById(id);

			return this.jwtUtilService.validateToken(jwt, user);
		} catch (Exception e) {
			L.error("An exception has been thrown : {}", e.getMessage());

			return false;
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		L.function("");

		String jwt = this.parseJwt(request);

		if (this.correctJwt(jwt)) {
			String id = this.jwtUtilService.getIdFromToken(jwt);

			UserDetails user = this.customUserDetailsService.loadUserById(id);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
					user.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else if (!this.authRequestMatcher.requestMatches(request.getRequestURI())) {
			response.setStatus(400);
			response.setContentType("text/plain");
			response.setContentLength("InvalidBearerToken".length());
			response.getWriter().write("InvalidBearerToken");
			response.getWriter().flush();
		}

		filterChain.doFilter(request, response);
	}

}
