package com.B305.ogym.domain.users.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QProfilePicture is a Querydsl query type for ProfilePicture
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QProfilePicture extends BeanPath<ProfilePicture> {

    private static final long serialVersionUID = 1304699295L;

    public static final QProfilePicture profilePicture = new QProfilePicture("profilePicture");

    public final StringPath pictureAddr = createString("pictureAddr");

    public QProfilePicture(String variable) {
        super(ProfilePicture.class, forVariable(variable));
    }

    public QProfilePicture(Path<? extends ProfilePicture> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProfilePicture(PathMetadata metadata) {
        super(ProfilePicture.class, metadata);
    }

}

