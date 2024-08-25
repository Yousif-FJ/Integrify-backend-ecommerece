package com.backend.ecommerce.shared.exceptions;

public class UniquenessException extends RuntimeException {
    public UniquenessException(String message) {
        super(message);
    }
}
