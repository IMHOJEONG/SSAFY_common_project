package com.B305.ogym.domain.users.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserBase is a Querydsl query type for UserBase
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserBase extends EntityPathBase<UserBase> {

    private static final long serialVersionUID = 752545606L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserBase userBase = new QUserBase("userBase");

    public final com.B305.ogym.domain.users.QBaseTimeEntity _super = new com.B305.ogym.domain.users.QBaseTimeEntity(this);

    public final QAddress address;

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final com.B305.ogym.domain.authority.QAuthority authority;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final QProfilePicture profilePicture;

    public final StringPath tel = createString("tel");

    public final StringPath username = createString("username");

    public QUserBase(String variable) {
        this(UserBase.class, forVariable(variable), INITS);
    }

    public QUserBase(Path<? extends UserBase> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserBase(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserBase(PathMetadata metadata, PathInits inits) {
        this(UserBase.class, metadata, inits);
    }

    public QUserBase(Class<? extends UserBase> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
        this.authority = inits.isInitialized("authority") ? new com.B305.ogym.domain.authority.QAuthority(forProperty("authority")) : null;
        this.profilePicture = inits.isInitialized("profilePicture") ? new QProfilePicture(forProperty("profilePicture")) : null;
    }

}

