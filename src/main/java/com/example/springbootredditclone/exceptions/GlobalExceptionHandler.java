package com.example.springbootredditclone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> userNameNotFound(UsernameNotFoundException usernameNotFoundException){
    return new ResponseEntity<>("This user does not exist", HttpStatus.BAD_GATEWAY);
}

@ExceptionHandler
public ResponseEntity<String> PostNotFoundException(PostNotFoundException postNotFoundException){
    return new ResponseEntity<>("PostId does not exist",HttpStatus.BAD_REQUEST);
}

}
