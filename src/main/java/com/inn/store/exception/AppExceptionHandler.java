package com.inn.store.exception;

import com.inn.store.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value  ={UserExceptionHandler.class})
    public ResponseEntity<Object> HandleUserException(UserExceptionHandler ex , WebRequest request )
    {

        // si je veux afficher juste le message  => ex.getMessage()
        // si je veux affichier  l'exception avec tous les champs => ex
        // si je veux presonaliser le retour de ce error

        ErrorMessage errorMessage=new ErrorMessage(new Date(),ex.getMessage());

        return new ResponseEntity<>(errorMessage,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value  =Exception.class)
    public ResponseEntity<Object> HandleOtherException(Exception ex , WebRequest request )
    {

        ErrorMessage errorMessage=new ErrorMessage(new Date(),ex.getMessage());

        return new ResponseEntity<>(errorMessage,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // les errors de validation

    @ExceptionHandler(value  = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> HandleMethodeArgumentNotValid(MethodArgumentNotValidException ex , WebRequest request )
    {
        Map<String, String> errors = new HashMap<>();
        // ex :est un table des exception qui s'affiche par defaut
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(),error.getDefaultMessage())
        );



        return new ResponseEntity<>(errors,new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }
}
