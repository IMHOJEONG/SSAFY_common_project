package com.B305.ogym.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// resource가 존재하지 않을때 5xx 에러 대신에 HttpStatus.NOT_FOUND를 보낸다.
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
