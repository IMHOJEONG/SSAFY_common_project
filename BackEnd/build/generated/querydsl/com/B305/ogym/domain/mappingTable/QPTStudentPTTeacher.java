package com.B305.ogym.domain.mappingTable;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPTStudentPTTeacher is a Querydsl query type for PTStudentPTTeacher
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPTStudentPTTeacher extends EntityPathBase<PTStudentPTTeacher> {

    private static final long serialVersionUID = -110649982L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPTStudentPTTeacher pTStudentPTTeacher = new QPTStudentPTTeacher("pTStudentPTTeacher");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.B305.ogym.domain.users.ptStudent.QPTStudent ptStudent;

    public final com.B305.ogym.domain.users.ptTeacher.QPTTeacher ptTeacher;

    public final DateTimePath<java.time.LocalDateTime> reservationDate = createDateTime("reservationDate", java.time.LocalDateTime.class);

    public QPTStudentPTTeacher(String variable) {
        this(PTStudentPTTeacher.class, forVariable(variable), INITS);
    }

    public QPTStudentPTTeacher(Path<? extends PTStudentPTTeacher> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPTStudentPTTeacher(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPTStudentPTTeacher(PathMetadata metadata, PathInits inits) {
        this(PTStudentPTTeacher.class, metadata, inits);
    }

    public QPTStudentPTTeacher(Class<? extends PTStudentPTTeacher> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ptStudent = inits.isInitialized("ptStudent") ? new com.B305.ogym.domain.users.ptStudent.QPTStudent(forProperty("ptStudent"), inits.get("ptStudent")) : null;
        this.ptTeacher = inits.isInitialized("ptTeacher") ? new com.B305.ogym.domain.users.ptTeacher.QPTTeacher(forProperty("ptTeacher"), inits.get("ptTeacher")) : null;
    }

}

