package ru.practicum.exception;

public class InvalidParamException extends IllegalArgumentException {
    public InvalidParamException(String message) {
        super(message);
    }
}
