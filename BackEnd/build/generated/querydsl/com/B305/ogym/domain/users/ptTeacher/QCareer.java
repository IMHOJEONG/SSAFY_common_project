package com.B305.ogym.domain.users.ptTeacher;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCareer is a Querydsl query type for Career
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCareer extends EntityPathBase<Career> {

    private static final long serialVersionUID = -654051865L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCareer career = new QCareer("career");

    public final StringPath company = createString("company");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPTTeacher ptTeacher;

    public final StringPath role = createString("role");

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public QCareer(String variable) {
        this(Career.class, forVariable(variable), INITS);
    }

    public QCareer(Path<? extends Career> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCareer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCareer(PathMetadata metadata, PathInits inits) {
        this(Career.class, metadata, inits);
    }

    public QCareer(Class<? extends Career> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ptTeacher = inits.isInitialized("ptTeacher") ? new QPTTeacher(forProperty("ptTeacher"), inits.get("ptTeacher")) : null;
    }

}

