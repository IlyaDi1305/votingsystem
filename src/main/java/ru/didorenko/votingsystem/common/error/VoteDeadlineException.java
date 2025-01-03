package ru.didorenko.votingsystem.common.error;

import static ru.didorenko.votingsystem.common.error.ErrorType.BAD_REQUEST;

public class VoteDeadlineException extends AppException {
    public VoteDeadlineException(String message) {
        super(message, BAD_REQUEST);
    }
}