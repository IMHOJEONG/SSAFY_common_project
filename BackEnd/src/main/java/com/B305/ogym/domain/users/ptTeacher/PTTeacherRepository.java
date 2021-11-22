package com.B305.ogym.domain.users.ptTeacher;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PTTeacherRepository extends JpaRepository<PTTeacher, Long>,
    PTTeacherRepositoryCustom {

    Optional<PTTeacher> findByEmail(String email);

    boolean existsByEmail(String email);

}
