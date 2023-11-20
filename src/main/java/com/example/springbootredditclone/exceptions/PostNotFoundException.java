package com.example.springbootredditclone.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
    }

    public PostNotFoundException(String message){
        super(message);
    }
}
