package com.B305.ogym.service;

import com.B305.ogym.controller.dto.HealthDto;
import com.B305.ogym.controller.dto.HealthDto.MyHealthResponse;
import com.B305.ogym.domain.users.UserRepository;
import com.B305.ogym.domain.users.ptStudent.MonthlyRepository;
import com.B305.ogym.domain.users.ptStudent.PTStudent;
import com.B305.ogym.domain.users.ptStudent.PTStudentRepository;
import com.B305.ogym.domain.users.ptTeacher.PTTeacherRepository;
import com.B305.ogym.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HealthService {

    private final PTTeacherRepository ptTeacherRepository;
    private final PTStudentRepository ptStudentRepository;

    // 로그인한 사용자의 건강정보 조회
    @Transactional
    public MyHealthResponse getMyHealth(String userEmail) {
        PTStudent ptStudent = ptStudentRepository.findByEmail(userEmail)
            .orElseThrow(() -> new UserNotFoundException("해당하는 이메일이 존재하지 않습니다."));
        return ptStudent.getMyHealthResponse(ptStudent);
    }

    // 선생님 : 학생들에 대한 건강정보 조회
    public HealthDto.MyStudentsHealthListResponse findMyStudentsHealth(String teacherEmail) {

        if (!ptTeacherRepository.existsByEmail(teacherEmail)) {
            throw new UserNotFoundException("해당하는 이메일이 존재하지 않습니다.");
        }

        return ptTeacherRepository.findMyStudentsHealth(teacherEmail);
    }
}
