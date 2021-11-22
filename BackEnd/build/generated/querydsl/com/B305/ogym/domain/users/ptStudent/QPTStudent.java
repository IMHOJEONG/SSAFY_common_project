package com.B305.ogym.domain.users.ptStudent;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPTStudent is a Querydsl query type for PTStudent
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPTStudent extends EntityPathBase<PTStudent> {

    private static final long serialVersionUID = -1192834105L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPTStudent pTStudent = new QPTStudent("pTStudent");

    public final com.B305.ogym.domain.users.common.QUserBase _super;

    // inherited
    public final com.B305.ogym.domain.users.common.QAddress address;

    //inherited
    public final NumberPath<Integer> age;

    // inherited
    public final com.B305.ogym.domain.authority.QAuthority authority;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate;

    //inherited
    public final StringPath email;

    //inherited
    public final EnumPath<com.B305.ogym.domain.users.common.Gender> gender;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate;

    public final ListPath<Monthly, QMonthly> monthly = this.<Monthly, QMonthly>createList("monthly", Monthly.class, QMonthly.class, PathInits.DIRECT2);

    //inherited
    public final StringPath nickname;

    //inherited
    public final StringPath password;

    // inherited
    public final com.B305.ogym.domain.users.common.QProfilePicture profilePicture;

    public final ListPath<com.B305.ogym.domain.mappingTable.PTStudentPTTeacher, com.B305.ogym.domain.mappingTable.QPTStudentPTTeacher> ptStudentPTTeachers = this.<com.B305.ogym.domain.mappingTable.PTStudentPTTeacher, com.B305.ogym.domain.mappingTable.QPTStudentPTTeacher>createList("ptStudentPTTeachers", com.B305.ogym.domain.mappingTable.PTStudentPTTeacher.class, com.B305.ogym.domain.mappingTable.QPTStudentPTTeacher.class, PathInits.DIRECT2);

    //inherited
    public final StringPath tel;

    //inherited
    public final StringPath username;

    public QPTStudent(String variable) {
        this(PTStudent.class, forVariable(variable), INITS);
    }

    public QPTStudent(Path<? extends PTStudent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPTStudent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPTStudent(PathMetadata metadata, PathInits inits) {
        this(PTStudent.class, metadata, inits);
    }

    public QPTStudent(Class<? extends PTStudent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.B305.ogym.domain.users.common.QUserBase(type, metadata, inits);
        this.address = _super.address;
        this.age = _super.age;
        this.authority = _super.authority;
        this.createdDate = _super.createdDate;
        this.email = _super.email;
        this.gender = _super.gender;
        this.id = _super.id;
        this.modifiedDate = _super.modifiedDate;
        this.nickname = _super.nickname;
        this.password = _super.password;
        this.profilePicture = _super.profilePicture;
        this.tel = _super.tel;
        this.username = _super.username;
    }

}

