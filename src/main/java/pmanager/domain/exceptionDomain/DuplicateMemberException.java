package pmanager.domain.exceptionDomain;

import pmanager.infrastucture.exception.RequestException;

public class DuplicateMemberException extends RequestException {
    public DuplicateMemberException(String email) {
        super("MemberDuplicate", "A member with the e-mail already exists: " + email);
    }
}
