package pmanager.domain.exceptionDomain;

import pmanager.infrastucture.exception.RequestException;

public class ApiKeyNotFoundException extends RequestException {
    public ApiKeyNotFoundException(String apiKeyId) {
        super("ApiKeyNotFound", "The API key was not found: " + apiKeyId);
    }
}
