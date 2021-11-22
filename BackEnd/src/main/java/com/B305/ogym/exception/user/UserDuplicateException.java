package com.B305.ogym.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 이미 존재하는 유저 가입시도 시 BAD_REQUEST 에러 반환
//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserDuplicateException extends RuntimeException {

    public UserDuplicateException(String message) {
        super(message);
    }
}
