package com.B305.ogym.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponseDto<T> {

    private int statusCode; // 상태 코드

    private String message; // 상태 메시지

    private T data; // response 데이터

}
