package pmanager.domain.exceptionDomain;

import pmanager.infrastucture.exception.RequestException;

public class MemberNoFoundException extends RequestException {
    public MemberNoFoundException(String memberId){
        super("MemberNotFound", "Member not found" + memberId);
    }
}
