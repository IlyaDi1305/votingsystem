package ru.didorenko.votingsystem.common.error;

import static ru.didorenko.votingsystem.common.error.ErrorType.DATA_CONFLICT;

public class DataConflictException extends AppException {
    public DataConflictException(String msg) {
        super(msg, DATA_CONFLICT);
    }
}