package com.example.springbootredditclone.exceptions;

public class SpringRedditException extends RuntimeException {

    public SpringRedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }
    public SpringRedditException(String exMsg) {
        super(exMsg);
    }

}
