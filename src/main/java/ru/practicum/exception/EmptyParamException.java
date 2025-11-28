package ru.practicum.exception;

public class EmptyParamException extends IllegalArgumentException {
    public EmptyParamException(String message) {
        super(message);
    }
}
