package pmanager.domain.exceptionDomain;

import pmanager.infrastucture.exception.RequestException;

public class TaskNoFoundException extends RequestException {
    public TaskNoFoundException(String TaskId){
        super("TaskNotFound", "Task not found" + TaskId);
    }
}
