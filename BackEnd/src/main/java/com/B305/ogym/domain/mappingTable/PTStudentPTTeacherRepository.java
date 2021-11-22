package com.B305.ogym.domain.mappingTable;

import com.B305.ogym.domain.users.ptStudent.PTStudent;
import com.B305.ogym.domain.users.ptTeacher.PTTeacher;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PTStudentPTTeacherRepository extends JpaRepository<PTStudentPTTeacher, Long>,
    PTStudentPTTeacherCustom {

    Optional<PTStudentPTTeacher> findByPtTeacherAndPtStudentAndReservationDate(PTTeacher ptTeacher,
        PTStudent ptStudent, LocalDateTime reservationTime);
}
