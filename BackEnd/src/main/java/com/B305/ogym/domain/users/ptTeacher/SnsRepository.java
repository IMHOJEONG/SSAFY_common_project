package com.B305.ogym.domain.users.ptTeacher;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SnsRepository extends JpaRepository<Sns, Long> {

    Sns findOneByPlatform(String platform);

}
