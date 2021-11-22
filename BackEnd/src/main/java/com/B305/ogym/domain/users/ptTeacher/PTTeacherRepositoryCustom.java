package com.B305.ogym.domain.users.ptTeacher;

import com.B305.ogym.common.util.RestResponsePage;
import com.B305.ogym.controller.dto.HealthDto.MyStudentsHealthListResponse;
import com.B305.ogym.controller.dto.PTDto.SearchDto;
import com.B305.ogym.domain.mappingTable.PTStudentPTTeacher;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PTTeacherRepositoryCustom {

    MyStudentsHealthListResponse findMyStudentsHealth(String teacherEmail);

    RestResponsePage<PTTeacher> searchAll(SearchDto searchDto, Pageable pageable);

    List<LocalDateTime> reservationTime(String teacherEmail);

    List<PTStudentPTTeacher> getReservationTime(String teacherEamil);
}
