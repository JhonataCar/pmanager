package pmanager.domain.exceptionDomain;

import pmanager.infrastucture.exception.RequestException;

public class InvalidProjectStatusException extends RequestException {
    public InvalidProjectStatusException(String statusStr){
        super("InvalidProjectStatus", "Invalid project status" + statusStr);
    }
}
