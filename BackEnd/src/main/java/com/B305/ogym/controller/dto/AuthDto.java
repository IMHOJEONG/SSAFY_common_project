package com.B305.ogym.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginDto {

        @NotNull
        @Size(min = 3, max = 50, message = "email은 3자 이상 50자 이하로 작성해주세요")
        private String email;

        @NotNull
        @Size(min = 3, max = 100, message = "패스워드는 3자 이상 100자 이하로 작성해주세요")
        private String password;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TokenDto {

        private String accessToken;
        private String refreshToken;

        @Builder
        public TokenDto(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

}
