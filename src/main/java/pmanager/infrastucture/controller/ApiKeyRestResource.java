package pmanager.infrastucture.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pmanager.domain.applicationservice.ApiKeyService;
import pmanager.domain.document.ApiKey;
import pmanager.infrastucture.dto.ApiKeyDTO;

import java.net.URI;

import static pmanager.infrastucture.controller.RestConstants.PATH_API_KEYS;


@RestController
//http://localhost:8080/projects
@RequestMapping(PATH_API_KEYS)
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ApiKeyRestResource {
    private final ApiKeyService apiKeyService;

    @PostMapping
    public ResponseEntity<ApiKeyDTO> createMember(){
        ApiKey apiKey = apiKeyService.createApiKey();

        return ResponseEntity
                .created(URI.create(PATH_API_KEYS + "/" + apiKey.getId()))
                .body(ApiKeyDTO.create(apiKey));
    }

    @PutMapping("/{id}/revoke")
    public ResponseEntity<Void> revokeApiKey(@PathVariable("id") String id){
        apiKeyService.revokeApiKey(id);
        return ResponseEntity.noContent().build();
    }
}
