package com.B305.ogym.exception.pt;

import org.aspectj.bridge.Message;

public class ReservationDuplicateException extends RuntimeException {

    public ReservationDuplicateException(String message) {
        super(message);
    }
}
