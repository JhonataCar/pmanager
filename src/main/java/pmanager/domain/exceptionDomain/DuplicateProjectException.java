package pmanager.domain.exceptionDomain;

import pmanager.infrastucture.exception.RequestException;

public class DuplicateProjectException extends RequestException {
    public DuplicateProjectException(String name) {
        super("ProjectDuplicate", "Project with the name already exists: " + name);
    }
}
