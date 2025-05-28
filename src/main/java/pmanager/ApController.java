package pmanager;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApController {

    //e você abrir http://localhost:8080/ok no navegador, esse método vai ser chamado.
    @GetMapping("/ok")

    //ResponseEntity é uma classe do Spring que representa toda a resposta(cliente) HTTP.
    public ResponseEntity<String> sayOk(){
        return ResponseEntity.ok("Everything OK!");
    }

    //vai ecoar o que o cliente enviou
    @PostMapping("/echo")
    public ResponseEntity<String>echo(@RequestBody String value){
        StringBuilder sb = new StringBuilder(value);
        return ResponseEntity.ok(sb.reverse().toString());
    }
}
