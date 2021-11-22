package com.B305.ogym.common.annotation;


import com.B305.ogym.common.util.WithAuthUserSecurityContextFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

/*
    UnitTest 에서 @AuthenticalPrincipal 어노테이션을 구현하기 위해 만든 커스텀 어노테이션
 */

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactory.class)
public @interface WithAuthUser {

    String email();

    String role();
}