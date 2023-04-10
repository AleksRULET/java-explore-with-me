package ru.practicum.ewm.error.model;

public class ApiError {

    private final String error;

    public ApiError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

}