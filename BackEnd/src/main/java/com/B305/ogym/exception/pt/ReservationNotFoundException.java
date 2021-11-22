package com.B305.ogym.exception.pt;

// 예약정보를 찾을 수 없을때 발생하는 에러
public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String message) {
        super(message);
    }
}
