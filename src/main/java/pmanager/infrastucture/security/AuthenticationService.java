package pmanager.infrastucture.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pmanager.domain.applicationservice.ApiKeyService;
import pmanager.domain.exceptionDomain.ApiKeyExpiredException;
import pmanager.domain.exceptionDomain.ApiKeyNotFoundException;
import pmanager.infrastucture.config.AppConfigProperties;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ApiKeyService apiKeyService;
    private final AppConfigProperties props;

    private final static String AUTH_TOKEN_HEADER_NAME = "x-api-key";

    public Authentication getAuthentication(HttpServletRequest request){
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);

        if(!Objects.equals(apiKey, props.getSecurity().getMasterApiKey())){
            try {
                apiKeyService.validateApiKey(apiKey);
            } catch (ApiKeyNotFoundException | ApiKeyExpiredException e){
                throw new BadCredentialsException("API key is not valid: "+ apiKey, e);
            }
        }

        return new ApiKeyAuthenticationToken(apiKey);
    }
}
