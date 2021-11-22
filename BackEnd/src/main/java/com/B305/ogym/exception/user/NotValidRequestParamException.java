package com.B305.ogym.exception.user;

// 이미 회원가입시 잘못된 파라미터 시도 시 BAD_REQUEST 에러 반환
//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotValidRequestParamException extends RuntimeException {

    public NotValidRequestParamException(String message) {
        super(message);
    }
}
