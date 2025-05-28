package pmanager.domain.applicationservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pmanager.domain.document.ApiKey;
import pmanager.domain.exceptionDomain.ApiKeyExpiredException;
import pmanager.domain.exceptionDomain.ApiKeyNotFoundException;
import pmanager.domain.repository.ApiKeyRepository;
import pmanager.infrastucture.config.AppConfigProperties;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

import static java.time.Instant.EPOCH;

@Service
@RequiredArgsConstructor
public class ApiKeyService {
    /**
     *Serviço responsável por gerenciar chaves de API.
     *
     *Funcionalidades principais:
     *-Criação de novas chaves com tempo de validade configurável.
     *-Validação de chaves existentes (incluindo verificação de expiração).
     *-Revogação de chaves, marcando-as como expiradas.
     *-Busca de chaves por ID ou valor.
     *
     * Exceções tratadas:
     *-ApiKeyNotFoundException: Lançada quando a chave não é encontrada.
     *-ApiKeyExpiredException: Lançada quando a chave está expirada.
     */


    private final ApiKeyRepository apiKeyRepository;
    private final AppConfigProperties props;

    public ApiKey createApiKey(){
        ApiKey apiKey = ApiKey
                .builder()
                .value(UUID.randomUUID().toString())
                .expiresWhen(
                        OffsetDateTime
                                .now()
                                .plusDays(props.getSecurity().getExpirationDays())
                                .toInstant()
                )
                .build();
        apiKeyRepository.save(apiKey);
        return apiKey;
    }

    public void revokeApiKey(String id){
        ApiKey apiKey = loadApiKeyById(id);
        apiKey.setExpiresWhen(EPOCH);
        apiKeyRepository.save(apiKey);

    }

    public void validateApiKey(String apiKey){
        ApiKey apiKeyObj = loadApiKeyByValue(apiKey);
        if(apiKeyObj.isExpired(Instant.now())){
            throw new ApiKeyExpiredException(apiKeyObj.getId());
        }
    }

    private ApiKey loadApiKeyById(String id){
        return apiKeyRepository
                    .findById(id)
                    .orElseThrow(()-> new ApiKeyNotFoundException(id));
    }

    private ApiKey loadApiKeyByValue(String apiKey){
        return apiKeyRepository
                .findByValue(apiKey)
                .orElseThrow(()-> new ApiKeyNotFoundException(apiKey));
    }
}
