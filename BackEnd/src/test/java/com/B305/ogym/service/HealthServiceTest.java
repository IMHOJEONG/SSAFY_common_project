package com.B305.ogym.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.B305.ogym.controller.dto.UserDto.SaveUserRequest;
import com.B305.ogym.domain.users.UserRepository;
import com.B305.ogym.domain.users.ptStudent.MonthlyRepository;
import com.B305.ogym.domain.users.ptStudent.PTStudent;
import com.B305.ogym.domain.users.ptStudent.PTStudentRepository;
import com.B305.ogym.domain.users.ptTeacher.PTTeacher;
import com.B305.ogym.domain.users.ptTeacher.PTTeacherRepository;
import com.B305.ogym.exception.user.UserNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HealthServiceTest {


    @Mock
    PTTeacherRepository ptTeacherRepository;
    @Mock
    PTStudentRepository ptStudentRepository;

    @InjectMocks
    HealthService healthService;

    private SaveUserRequest createTeacherRequest() {
        return SaveUserRequest.builder()
            .email("hello@naver.com")
            .password("asdasd")
            .username("juhu")
            .nickname("주현")
            .gender(0)
            .tel("010-0000-0000")
            .zipCode("12345")
            .street("road 17")
            .detailedAddress("juhu")
            .role("ROLE_PTTEACHER")
            .major("재활")
            .certificates(new ArrayList<>())
            .careers(new ArrayList<>())
            .price(1000)
            .description("트레이너")
            .build();
    }

    private SaveUserRequest createStudentRequest() {
        return SaveUserRequest.builder()
            .email("hello@naver.com")
            .password("비밀번호")
            .username("한솥")
            .nickname("닉네임")
            .gender(0)
            .tel("010-0000-0000")
            .zipCode("12345")
            .street("도로명")
            .detailedAddress("상세주소")
            .role("ROLE_PTSTUDENT")
            .monthlyWeights(new ArrayList<Integer>(Arrays
                .asList(180, 200, 210, 180, 200, 210, 180, 200, 210, 180, 200, 210)))
            .monthlyHeights(new ArrayList<Integer>(Arrays
                .asList(180, 200, 210, 180, 200, 210, 180, 200, 210, 180, 200, 210)))
            .build();
    }

    private PTStudent createStudent() {
        return createStudentRequest().toPTStudentEntity();
    }

    private PTTeacher createTeacher() {
        return createTeacherRequest().toPTTeacherEntity();
    }

    @DisplayName("내 건강정보 확인 - 성공")
    @Test
    public void getMyHealth_success() throws Exception {
        //given
        var user = createStudent();
        given(ptStudentRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));
        //when
        healthService.getMyHealth(user.getEmail());
        //then
        verify(ptStudentRepository, atLeastOnce()).findByEmail(user.getEmail());
    }

    @DisplayName("내 건강정보 확인 - 이미 탈퇴한 회원 정보로 인한 실패")
    @Test
    public void getMyHealth_failure() throws Exception {
        //given
        var user = createStudent();
        given(ptStudentRepository.findByEmail(user.getEmail()))
            .willThrow(new UserNotFoundException("이미 탈퇴한 회원입니다."));
        //when
        assertThrows(UserNotFoundException.class, () -> healthService.getMyHealth(user.getEmail()));
        //then
        verify(ptStudentRepository, atLeastOnce()).findByEmail(user.getEmail());
    }


    @DisplayName("내 학생들의 건강정보 리스트 확인 - 성공")
    @Test
    public void findMyStudentsHealth_success() throws Exception {
        //given
        var user = createTeacher();

        given(ptTeacherRepository.existsByEmail(user.getEmail())).willReturn(true);
        //when
        healthService.findMyStudentsHealth(user.getEmail());
        //then
        verify(ptTeacherRepository, atLeastOnce()).existsByEmail(user.getEmail());

    }

    //예외처리 코드 생기면 테스트
    @DisplayName("내 학생들의 건강정보 리스트 확인 - 이미 탈퇴한 회원 정보로 인한 실패")
    @Test
    public void findMyStudentsHealth_failure() throws Exception {
        //given
        var user = createTeacher();

        given(ptTeacherRepository.existsByEmail(user.getEmail()))

            .willThrow(new UserNotFoundException("이미 탈퇴한 회원입니다"));
        //when
        assertThrows(UserNotFoundException.class,
            () -> healthService.findMyStudentsHealth(user.getEmail()));
        //then

        verify(ptTeacherRepository, atLeastOnce()).existsByEmail(user.getEmail());

    }

}