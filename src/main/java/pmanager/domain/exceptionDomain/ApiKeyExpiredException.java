package pmanager.domain.exceptionDomain;

import pmanager.infrastucture.exception.RequestException;

//serve para lançar uma exceção personalizada quando uma chave de API está expirada. (semelhante as outras)
public class ApiKeyExpiredException extends RequestException {
    public ApiKeyExpiredException(String apiKeyId) {
        super("ApiKeyExpired", "The API key expired: " + apiKeyId);
    }
}
