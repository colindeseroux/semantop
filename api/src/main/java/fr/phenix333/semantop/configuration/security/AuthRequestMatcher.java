package fr.phenix333.semantop.configuration.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import fr.phenix333.logger.MyLogger;

@Component
public class AuthRequestMatcher {

    @Value("${uri.authorized}")
    private List<String> uriAuthorized;

    private static final MyLogger L = MyLogger.create(AuthRequestMatcher.class);

    /**
     * Returns a RequestMatcher that checks if the request URI matches any of the
     * authorized URIs.
     *
     * @return RequestMatcher
     */
    public RequestMatcher getRequestMatchers() {
        L.function("");

        RequestMatcher requestMatcher = (request) -> this.requestMatches(request.getRequestURI());

        return requestMatcher;
    }

    /**
     * Checks if the given URI matches any of the authorized URIs.
     *
     * @param uri -> String : the URI to check
     * 
     * @return true if the URI matches, false otherwise
     */
    public boolean requestMatches(String uri) {
        L.function("uri : {}", uri);

        for (String testUri : this.uriAuthorized) {
            if (uri.contains(testUri)) {
                return true;
            }
        }

        return false;
    }

}
