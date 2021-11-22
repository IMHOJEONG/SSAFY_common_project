package com.B305.ogym.domain.users;

import com.B305.ogym.domain.users.common.UserBase;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserBase, Long> {

    @EntityGraph(attributePaths = "authority")
    Optional<UserBase> findOneWithAuthoritiesByEmail(String email);

    Optional<UserBase> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
