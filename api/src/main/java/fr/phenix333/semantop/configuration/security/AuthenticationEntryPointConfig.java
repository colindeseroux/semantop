package fr.phenix333.semantop.configuration.security;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import fr.phenix333.logger.MyLogger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationEntryPointConfig implements AuthenticationEntryPoint, Serializable {

	private static final MyLogger L = MyLogger.create(AuthenticationEntryPointConfig.class);

	private static final long serialVersionUID = 333L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		L.function("");

		if (response.getStatus() == 403 && response.getContentType() == null) {
			response.setContentType("text/plain");
			response.setContentLength("PermissionDeniedDisappear".length());
			response.getWriter().write("PermissionDeniedDisappear");
			response.getWriter().flush();
		}
	}

}
