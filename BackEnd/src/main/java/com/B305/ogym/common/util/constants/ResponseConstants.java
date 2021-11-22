package com.B305.ogym.common.util.constants;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseConstants {

    public static final ResponseEntity<Void> OK =
        ResponseEntity.ok().build();

    public static final ResponseEntity<Void> INTERNAL_SERVER_ERROR =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    public static final ResponseEntity<Void> CREATED =
        ResponseEntity.status(HttpStatus.CREATED).build();

    public static final ResponseEntity<Object> BAD_REQUEST =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    public static final ResponseEntity<Object> VALIDATION_FAILED =
        new ResponseEntity<>("Validation Failed.", HttpStatus.BAD_REQUEST);

    public static final ResponseEntity<String> DUPLICATION_EMAIL =
        new ResponseEntity<>("중복된 이메일입니다.", HttpStatus.CONFLICT);

    public static final ResponseEntity<String> DUPLICATION_NICKNAME =
        new ResponseEntity<>("중복된 닉네임입니다.", HttpStatus.CONFLICT);

    public static final ResponseEntity<String> DUPLICATION_MONTH =
        new ResponseEntity<>("이미 해당 월의 정보가 존재합니다.", HttpStatus.CONFLICT);

    public static final ResponseEntity<String> DUPLICATION_RESERVATION =
        new ResponseEntity<>("해당 시간에 예약할 수 없습니다.", HttpStatus.CONFLICT);

    public static final ResponseEntity<String> NOT_VALID_PARAM =
        new ResponseEntity<>("입력하지 않은 파라미터가 존재합니다.", HttpStatus.CONFLICT);

    public static final ResponseEntity<String> USER_NOT_FOUND =
        new ResponseEntity<>(
            "가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.", HttpStatus.NOT_FOUND
        );
    public static final ResponseEntity<String> RESERVATION_NOT_FOUND =
        new ResponseEntity<>("권한이 없거나, 유효한 예약정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    public static final ResponseEntity<String> AUTHORITY_NOT_FOUND =
        new ResponseEntity<>("권한을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    public static final ResponseEntity<String> TEACHER_NOT_FOUND =
        new ResponseEntity<>(
            "선생님 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND
        );
    public static final ResponseEntity<String> UNAUTHORIZED_USER =
        new ResponseEntity<>(
            "Unauthenticated user", HttpStatus.UNAUTHORIZED
        );
}
