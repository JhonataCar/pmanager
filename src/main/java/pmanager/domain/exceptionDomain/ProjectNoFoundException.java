package pmanager.domain.exceptionDomain;

import pmanager.infrastucture.exception.RequestException;

public class ProjectNoFoundException extends RequestException {
    public ProjectNoFoundException(String projectId){
        super("ProjectNotFound", "Project not found" + projectId);
    }
}
