package ru.didorenko.votingsystem.common.error;

public class NotFoundException extends AppException {
    public NotFoundException(String msg) {
        super(msg, ErrorType.NOT_FOUND);
    }
}