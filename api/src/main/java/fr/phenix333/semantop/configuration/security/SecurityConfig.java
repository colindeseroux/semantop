package fr.phenix333.semantop.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import fr.phenix333.logger.MyLogger;
import fr.phenix333.semantop.service.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private static final MyLogger L = MyLogger.create(SecurityConfig.class);

	private AuthenticationEntryPointConfig authenticationEntryPointConfig;

	private CustomUserDetailsService customUserDetailsService;

	private AuthRequestMatcher authRequestMatcher;

	private RequestFilterConfig requestFilterConfig;

	public SecurityConfig(AuthenticationEntryPointConfig authenticationEntryPointConfig,
			CustomUserDetailsService customUserDetailsService, AuthRequestMatcher authRequestMatcher,
			RequestFilterConfig requestFilterConfig) {
		this.authenticationEntryPointConfig = authenticationEntryPointConfig;
		this.customUserDetailsService = customUserDetailsService;
		this.authRequestMatcher = authRequestMatcher;
		this.requestFilterConfig = requestFilterConfig;
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		L.function("");

		return new BCryptPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		L.debug("");

		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(this.customUserDetailsService);
		authProvider.setPasswordEncoder(this.passwordEncoder());

		return authProvider;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		L.function("");

		http.csrf(CsrfConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.requestMatchers(this.authRequestMatcher.getRequestMatchers())
						.permitAll().anyRequest().authenticated())
				.exceptionHandling(exce -> exce.authenticationEntryPoint(this.authenticationEntryPointConfig))
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(this.authenticationProvider())
				.addFilterBefore(this.requestFilterConfig, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		L.function("");

		return authenticationConfiguration.getAuthenticationManager();
	}

}
