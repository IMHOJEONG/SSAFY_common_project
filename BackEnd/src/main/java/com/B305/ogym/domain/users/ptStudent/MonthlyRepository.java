package com.B305.ogym.domain.users.ptStudent;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyRepository extends JpaRepository<Monthly, Long> {

    @EntityGraph(attributePaths = "ptStudent")
    List<Monthly> findAllByPtStudent(Long Id);

    Monthly findMonthlyByPtStudentAndMonth(PTStudent ptStudent, int month);
}
