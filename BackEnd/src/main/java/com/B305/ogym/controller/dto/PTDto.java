package com.B305.ogym.controller.dto;

import com.B305.ogym.domain.users.common.Gender;
import com.B305.ogym.domain.users.common.ProfilePicture;
import com.B305.ogym.domain.users.ptTeacher.Career;
import com.B305.ogym.domain.users.ptTeacher.PTTeacher;
import com.B305.ogym.domain.users.ptTeacher.Sns;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

public class PTDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class reservationRequest {

        @NotNull
        private String ptTeacherEmail;

        @FutureOrPresent(message = "현재/미래만 입력 가능합니다")
        private LocalDateTime reservationTime;

        @NotBlank
        private String description;

        @Builder
        public reservationRequest(String ptTeacherEmail, LocalDateTime reservationTime,
            String description) {
            this.ptTeacherEmail = ptTeacherEmail;
            this.reservationTime = reservationTime;
            this.description = description;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetTeacherListRequest {

        @NotEmpty
        private List<Map<String, String>> filter;
    }


    /////////////////////////////////////////////////////
    // 모든 선생님의 리스트를 출력
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class AllTeacherListResponse {

        private List<PTTeacherDto> teacherList;

        private int totalPages; // 전체 페이지 수

        private long totalElements; // 전체 요소의 수

        private int numberOfElements; // 현재 페이지에 조회된 요소의 수

    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class PTTeacherDto {

        // 이름
        private String username;

        // 성별
        private Gender gender;

        // 나이
        private int age;

        // 닉네임
        private String nickname;

        // 연락처
        private String tel;

        // 이메일
        private String email;

        // 프로필사진
        private ProfilePicture profilePicture;

        // 평점
        private float starRating;

        // 전문분야
        private String major;

        // 가격
        private int price;

        // 자기소개
        private String description;

        // sns 정보
        private List<SnsDto> snsList = new ArrayList<>();

        // 커리어 정보
        private List<CareerDto> careers = new ArrayList<>();

        // 예약된 시간정보
        private List<LocalDateTime> reservations = new ArrayList<>();

        // 자격증 정보
        private List<CertificateDto> certificates = new ArrayList<>();


    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CareerDto {

        private String role;
        private LocalDate startDate;
        private LocalDate endDate;
        private String company;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CertificateDto {

        private String name;
        private String publisher;
        private LocalDate date;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class SnsDto {

        private String url;
        private String platform;
    }

    /////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class SearchDto {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SearchDto searchDto = (SearchDto) o;
            return Objects.equals(getName(), searchDto.getName()) && getGender() == searchDto
                .getGender() && Objects.equals(getMinAge(), searchDto.getMinAge())
                && Objects.equals(getMaxAge(), searchDto.getMaxAge()) && Objects
                .equals(getMinPrice(), searchDto.getMinPrice()) && Objects
                .equals(getMaxPrice(), searchDto.getMaxPrice());
        }

        @Override
        public int hashCode() {
            return Objects
                .hash(getName(), getGender(), getMinAge(), getMaxAge(), getMinPrice(),
                    getMaxPrice());
        }

        @Builder.Default
        private String name = null;
        @Builder.Default
        private Gender gender = null;
        @Builder.Default
        private Integer minAge = null;
        @Builder.Default
        private Integer maxAge = null;
        @Builder.Default
        private Integer minPrice = null;
        @Builder.Default
        private Integer maxPrice = null;
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class reservationDto {

        private String description; // 설명
        private String nickname; // 상대방 닉네임
        private String username; // 상대방 이름
        private String profileUrl; // 상대방 프로필
        private String email; // 상대방 이메일
        private LocalDateTime reservationTime; // 예약 시간,

        @Builder
        public reservationDto(String description, String nickname, String username,
            String profileUrl, String email,
            LocalDateTime reservationTime) {
            this.description = description;
            this.nickname = nickname;
            this.username = username;
            this.profileUrl = profileUrl;
            this.email = email;
            this.reservationTime = reservationTime;
        }

        public void setProfileUrl(String url){
            profileUrl = url;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class nowReservationDto {

        private String teacherNickname;
        private String studentNickname;

        @Builder
        public nowReservationDto(String teacherNickname, String studentNickname) {
            this.teacherNickname = teacherNickname;
            this.studentNickname = studentNickname;
        }
    }
}
