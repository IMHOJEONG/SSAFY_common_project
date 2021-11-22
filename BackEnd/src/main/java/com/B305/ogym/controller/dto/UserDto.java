package com.B305.ogym.controller.dto;

import com.B305.ogym.domain.users.common.Address;
import com.B305.ogym.domain.users.common.Gender;
import com.B305.ogym.domain.users.ptStudent.PTStudent;
import com.B305.ogym.domain.users.ptTeacher.Career;
import com.B305.ogym.domain.users.ptTeacher.Certificate;
import com.B305.ogym.domain.users.ptTeacher.PTTeacher;
import com.B305.ogym.domain.users.ptTeacher.Sns;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveUserRequest {

        @NotEmpty
        @Size(min = 3, max = 50, message = "이메일을 올바르게 입력하세요.")
        @Pattern(regexp = "\\S+@\\S+(.com)$")
        private String email;

        @NotEmpty
        @Size(min = 3, max = 20, message = "비밀번호는 3자이상 20자 이하로 입력해주세요.")
        private String password;

        @NotEmpty
        @Size(min = 2, max = 10, message = "이름을 올바르게 입력하세요.")
        private String username;

        @NotNull
        @Positive(message = "나이를 올바르게 입력하세요.")
        @Max(value = 150, message = "나이를 올바르게 입력하세요.")
        private int age;

        @NotEmpty
        @Size(min = 3, max = 50, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
        private String nickname;

        @NotNull(message = "성별 입력하세요.")
        private Integer gender; // 0 MAN , 1 WOMAN

        @NotEmpty(message = "올바른 휴대폰 번호를 입력하세요.")
        @Pattern(regexp = "\\d{3}-\\d{3,4}-\\d{4}")
        private String tel;

        @NotEmpty
        @Pattern(regexp = "\\d{5}", message = "올바른 우편번호를 입력하세요.")
        private String zipCode; // 우편번호

        @NotEmpty(message = "도로명 주소를 입력하세요.")
        private String street; // 도로명 주소

        @NotEmpty(message = "상세주소를 입력하세요.")
        private String detailedAddress; // 상세 주소 ?

        @NotEmpty(message = "권한 주세요.")
        private String role;

        // --------------------------

        private List<Integer> monthlyHeights;


        private List<Integer> monthlyWeights;

        public PTStudent toPTStudentEntity() {

            Gender gender = Gender.MAN;
            if (this.gender == 1) {
                gender = Gender.WOMAN;
            }

            Address address = Address.createAddress(
                zipCode,
                street,
                detailedAddress
            );

            return PTStudent.builder()
                .email(email)
                .username(username)
                .nickname(nickname)
                .gender(gender)
                .age(age)
                .tel(tel)
                .address(address)
                .build();
        }
        // ----------

        private String major;

        private List<Certificate> certificates;

        public List<Career> careers;

        private int price;

        private String description;

        private List<Sns> snsAddrs;

        public PTTeacher toPTTeacherEntity() {

            Gender gender = Gender.MAN;
            if (this.gender == 1) {
                gender = Gender.WOMAN;
            }

            Address address = Address.createAddress(
                zipCode,
                street,
                detailedAddress
            );

            return PTTeacher.builder()
                .email(email)
                .username(username)
                .nickname(nickname)
                .gender(gender)
                .age(age)
                .tel(tel)
                .address(address)
                .description(description)
                .price(price)
                .major(major)
                .certificates(new ArrayList<>())
                .careers(new ArrayList<>())
                .build();
        }
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetUserInfoRequest {

        @NotEmpty
        private List<String> req;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class CareerDto {

        private String role;

        private LocalDate startDate;
        private LocalDate endDate;

        private String company;

        @Builder
        public CareerDto(String role, LocalDate startDate, LocalDate endDate, String company) {
            this.role = role;
            this.startDate = startDate;
            this.endDate = endDate;
            this.company = company;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class CertificateDto {

        private String name; // 자격증 명칭

        private String publisher; // 발급 기관

        private LocalDate date; // 획득일

        @Builder
        public CertificateDto(String name, String publisher, LocalDate date) {
            this.name = name;
            this.publisher = publisher;
            this.date = date;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class SnsDto {

        private String url; // 주소

        private String platform; // 플랫폼

        @Builder
        public SnsDto(String url, String platform) {
            this.url = url;
            this.platform = platform;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class ProfileDto {

        private String url; // 프로필 이미지 주소

        @Builder
        public ProfileDto(String url) {
            this.url = url;
        }
    }


}
