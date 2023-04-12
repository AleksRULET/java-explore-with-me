package ru.practicum.ewm.error.exceptions;

public class CategoryConstraintViolationException extends RuntimeException {

    public CategoryConstraintViolationException(String message) {
        super(message);
    }
}
