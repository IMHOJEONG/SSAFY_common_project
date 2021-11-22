package com.B305.ogym.common.util;

import com.B305.ogym.common.annotation.WithAuthUser;
import com.B305.ogym.domain.authority.Authority;
import com.B305.ogym.domain.users.common.UserBase;
import java.lang.annotation.Annotation;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/*
    @WithAuthUser 어노테이션의 동작을 위한 SecurityContextFactory
 */

public class WithAuthUserSecurityContextFactory implements
    WithSecurityContextFactory<WithAuthUser> {

    @Override
    public SecurityContext createSecurityContext(WithAuthUser annotation) {
        String email = annotation.email();
        String role = annotation.role();

        UserBase authUser = UserBase.builder()
            .email(email)
            .authority(new Authority(role))
            .build();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
            authUser, "password",
            List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
