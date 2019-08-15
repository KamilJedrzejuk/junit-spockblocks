package com.twitter.kamilyedrzejuq.veryfier;

public class TestVerificationFailed extends RuntimeException {

    public TestVerificationFailed(String message) {
        super(message);
    }

    public TestVerificationFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
