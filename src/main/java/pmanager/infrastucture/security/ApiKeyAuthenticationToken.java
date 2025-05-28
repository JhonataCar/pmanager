package pmanager.infrastucture.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {
    private final String apiKey;

    public ApiKeyAuthenticationToken(String apiKey) {
        super(NO_AUTHORITIES);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}
