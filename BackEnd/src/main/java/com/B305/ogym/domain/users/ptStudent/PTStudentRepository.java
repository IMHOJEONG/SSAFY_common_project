package com.B305.ogym.domain.users.ptStudent;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PTStudentRepository extends JpaRepository<PTStudent, Long>,
    PTStudentRepositoryCustom {

    Optional<PTStudent> findByEmail(String email);

}
