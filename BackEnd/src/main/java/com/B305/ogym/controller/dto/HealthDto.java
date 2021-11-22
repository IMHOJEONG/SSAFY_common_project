package com.B305.ogym.controller.dto;

import com.B305.ogym.domain.users.common.Gender;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class HealthDto {

    // 건강정보 조회시 사용
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MyHealthResponse {

        private List<Integer> heightList;
        private List<Integer> weightList;

        @Builder
        public MyHealthResponse(List<Integer> heightList, List<Integer> weightList) {
            this.heightList = heightList;
            this.weightList = weightList;
        }
    }

    // 건강정보 수정시 사용
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SetMyHealthRequest {

        @Min(1)
        @Max(12)
        @NotNull
        private int month;

        @Positive
        private int height;
        @Positive
        private int weight;

        @Builder
        public SetMyHealthRequest(int month, int height, int weight) {
            this.month = month;
            this.height = height;
            this.weight = weight;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class MyStudentsHealthListResponse {

        private List<StudentHealth> studentHealthList;

        public void setStudentHealthList(List<StudentHealth> studentHealthList) {
            this.studentHealthList = studentHealthList;
        }

    }

    @NoArgsConstructor
    @Getter
    public static class StudentHealth {

        private String username;
        private String nickname;
        private int age;
        private Gender gender;
        private String profileUrl;
        private List<Integer> heightList;
        private List<Integer> weightList;

        public void addHeight(int height) {
            if (heightList == null) {
                heightList = new ArrayList<>();
            }
            heightList.add(height);
        }

        public void addWeight(int weight) {
            if (weightList == null) {
                weightList = new ArrayList<>();
            }
            weightList.add(weight);
        }
        public void setProfileUrl(String url){
            profileUrl = url;
        }
        @Builder
        public StudentHealth(String username, String nickname, int age,
            Gender gender, String profileUrl, List<Integer> heightList,
            List<Integer> weightList) {
            this.username = username;
            this.nickname = nickname;
            this.age = age;
            this.gender = gender;
            this.profileUrl = profileUrl;
            this.heightList = heightList;
            this.weightList = weightList;
        }
    }

}
