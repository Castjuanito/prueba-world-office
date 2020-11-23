package com.prueba.world.office.employees.exception.custom_exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
