package com.B305.ogym.domain.users.ptTeacher;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSns is a Querydsl query type for Sns
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSns extends EntityPathBase<Sns> {

    private static final long serialVersionUID = -1079406673L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSns sns = new QSns("sns");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath platform = createString("platform");

    public final QPTTeacher ptTeacher;

    public final StringPath url = createString("url");

    public QSns(String variable) {
        this(Sns.class, forVariable(variable), INITS);
    }

    public QSns(Path<? extends Sns> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSns(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSns(PathMetadata metadata, PathInits inits) {
        this(Sns.class, metadata, inits);
    }

    public QSns(Class<? extends Sns> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ptTeacher = inits.isInitialized("ptTeacher") ? new QPTTeacher(forProperty("ptTeacher"), inits.get("ptTeacher")) : null;
    }

}

