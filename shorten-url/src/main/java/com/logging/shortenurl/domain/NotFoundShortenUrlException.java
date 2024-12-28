package com.logging.shortenurl.domain;

public class NotFoundShortenUrlException extends RuntimeException {

    public NotFoundShortenUrlException(String message) {
        super(message);
    }
}
