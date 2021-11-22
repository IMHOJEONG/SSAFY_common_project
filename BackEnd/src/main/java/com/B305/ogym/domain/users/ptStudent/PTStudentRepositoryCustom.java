package com.B305.ogym.domain.users.ptStudent;

import com.B305.ogym.domain.mappingTable.PTStudentPTTeacher;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface PTStudentRepositoryCustom {

    List<PTStudentPTTeacher> getReservationTime(String stduentEamil);

}
