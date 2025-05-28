package pmanager.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pmanager.domain.document.ApiKey;

import java.util.Optional;

@Repository
public interface ApiKeyRepository extends MongoRepository<ApiKey, String> {

    Optional<ApiKey> findByValue(String value);
}
