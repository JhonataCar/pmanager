package pmanager.domain.exceptionDomain;

import pmanager.infrastucture.exception.RequestException;

public class InvalidTaskStatusException extends RequestException {
    public InvalidTaskStatusException(String statusStr){
        super("InvalidTaskStatus", "Invalid task status" + statusStr);
    }
}
