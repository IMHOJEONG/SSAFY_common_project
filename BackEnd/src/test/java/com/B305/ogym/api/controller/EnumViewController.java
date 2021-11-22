package com.B305.ogym.api.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnumViewController {

    @GetMapping("/docs/commonResponse")
    public Map<String, String> commonResponse() {

        Map<String, String> commonResponse = new HashMap<>();
        commonResponse.put("statusCode", "HTTP STATUS");
        commonResponse.put("message", "메시지");
        commonResponse.put("data", "데이터");

        return commonResponse;
    }

    @GetMapping("/docs/apiResponseCode")
    public Map<String, String> apiResponseCode() {

        Map<String, String> commonResponse = new HashMap<>();
        commonResponse.put("VALIDATION_FAILED", "유효성 조건을 만족하지 못한 경우 발생하는 에러입니다.");
        commonResponse.put("DUPLICATION_EMAIL", "이메일 중복 시 발생하는 에러입니다.");
        commonResponse.put("DUPLICATION_NICKNAME", "닉네임 중복 시 발생하는 에러입니다.");
        commonResponse.put("DUPLICATION_RESERVATION", "예약 중복 시 발생하는 에러입니다.");
        commonResponse.put("DUPLICATION_MONTH", "월별 데이터 중복 시 발생하는 에러입니다.");
        commonResponse.put("TEACHER_NOT_FOUND", "대상 트레이너를 찾을 수 없을 때 발생하는 에러입니다.");
        commonResponse.put("RESERVATION_NOT_FOUND", "예약을 찾을 수 없을 때 발생하는 에러입니다.");
        commonResponse.put("USER_NOT_FOUND", "유저를 찾을 수 없을 때 발생하는 에러입니다.");
        commonResponse.put("NOT_VALID_PARAM", "파라미터가 유효하지 않을 때 발생하는 에러입니다.");
        commonResponse.put("UNAUTHORIZED_USER", "인증되지 않는 유저에게 발생하는 에러입니다.");
        commonResponse.put("OK", "요청이 성공하였습니다.");

        return commonResponse;
    }

}
