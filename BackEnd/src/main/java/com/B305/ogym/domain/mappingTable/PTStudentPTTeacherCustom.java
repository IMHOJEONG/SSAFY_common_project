package com.B305.ogym.domain.mappingTable;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PTStudentPTTeacherCustom {
    List<String> getNowReservation(String teacherEmail, String studentEmail, LocalDateTime now);
}
