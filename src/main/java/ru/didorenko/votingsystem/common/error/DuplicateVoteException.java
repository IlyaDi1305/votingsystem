package ru.didorenko.votingsystem.common.error;

import static ru.didorenko.votingsystem.common.error.ErrorType.VOTE_CONFLICT;

public class DuplicateVoteException extends AppException{
    public DuplicateVoteException(String message) {
        super(message, VOTE_CONFLICT);
    }
}
