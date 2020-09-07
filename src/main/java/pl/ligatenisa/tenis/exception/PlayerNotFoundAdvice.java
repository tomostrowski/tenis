package pl.ligatenisa.tenis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class PlayerNotFoundAdvice {

    @ExceptionHandler(PlayerNotFoundExeption.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String PlayerNotFoundHandler(PlayerNotFoundExeption ex){
        return ex.getMessage();
    }
}
