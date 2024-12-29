package ru.didorenko.votingsystem.common.error;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class AppException extends RuntimeException {
    private final ErrorType errorType;

    public AppException(@NonNull String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
}
