package com.egor.socialapi.exception;

public class SocialNetworkException extends RuntimeException {

    public SocialNetworkException() {
    }

    public SocialNetworkException(String message) {
        super(message);
    }

    public SocialNetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public SocialNetworkException(Throwable cause) {
        super(cause);
    }
}
