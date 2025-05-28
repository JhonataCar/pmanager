package pmanager.infrastucture.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    //Handler específico para a exceção personalizada RequestException
    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<Object> handlerRequestException(RequestException ex, WebRequest request){
        return handlerException(ex, ex.getErrorCode(), ex.getMessage(), null, BAD_REQUEST, request);
    }

    //Handler genérico para qualquer exceção não tratada anteriormente
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handlerRequestException(Exception ex, WebRequest request){
        return handlerException(ex, null, ex.getMessage(), null, INTERNAL_SERVER_ERROR, request);
    }

    @Override
    //Trata automaticamente exceções de validação
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        //Extrai todas as mensagens de erro de validação dos campos com falha
        List<String> details = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .filter(Objects::nonNull)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return handlerException(ex, "ValidationError",null, details, BAD_REQUEST, request);
    }

    //Método privado e reutilizável que monta a resposta de erro completa
    private ResponseEntity<Object> handlerException(
            Exception ex,
            String errorCode,
            String message,
            List<String> details,
            HttpStatus status,
            WebRequest request
    ){
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;

        return handleExceptionInternal(
                ex,
                RestError
                        .builder()
                        .errorCode(errorCode)
                        .errorMessage(message)
                        .details(details)
                        .status(status.value())
                        .path(servletWebRequest.getRequest().getRequestURI())
                        .build(),
                new HttpHeaders(),
                status,
                request
        );
    }

}
