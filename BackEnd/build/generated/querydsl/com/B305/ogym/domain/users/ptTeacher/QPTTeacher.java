package com.B305.ogym.domain.users.ptTeacher;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPTTeacher is a Querydsl query type for PTTeacher
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPTTeacher extends EntityPathBase<PTTeacher> {

    private static final long serialVersionUID = 1639365205L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPTTeacher pTTeacher = new QPTTeacher("pTTeacher");

    public final com.B305.ogym.domain.users.common.QUserBase _super;

    // inherited
    public final com.B305.ogym.domain.users.common.QAddress address;

    //inherited
    public final NumberPath<Integer> age;

    // inherited
    public final com.B305.ogym.domain.authority.QAuthority authority;

    public final ListPath<Career, QCareer> careers = this.<Career, QCareer>createList("careers", Career.class, QCareer.class, PathInits.DIRECT2);

    public final ListPath<Certificate, QCertificate> certificates = this.<Certificate, QCertificate>createList("certificates", Certificate.class, QCertificate.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate;

    public final StringPath description = createString("description");

    //inherited
    public final StringPath email;

    //inherited
    public final EnumPath<com.B305.ogym.domain.users.common.Gender> gender;

    //inherited
    public final NumberPath<Long> id;

    public final StringPath major = createString("major");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate;

    //inherited
    public final StringPath nickname;

    //inherited
    public final StringPath password;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    // inherited
    public final com.B305.ogym.domain.users.common.QProfilePicture profilePicture;

    public final SetPath<com.B305.ogym.domain.mappingTable.PTStudentPTTeacher, com.B305.ogym.domain.mappingTable.QPTStudentPTTeacher> ptStudentPTTeachers = this.<com.B305.ogym.domain.mappingTable.PTStudentPTTeacher, com.B305.ogym.domain.mappingTable.QPTStudentPTTeacher>createSet("ptStudentPTTeachers", com.B305.ogym.domain.mappingTable.PTStudentPTTeacher.class, com.B305.ogym.domain.mappingTable.QPTStudentPTTeacher.class, PathInits.DIRECT2);

    public final ListPath<Sns, QSns> snss = this.<Sns, QSns>createList("snss", Sns.class, QSns.class, PathInits.DIRECT2);

    public final NumberPath<Float> starRating = createNumber("starRating", Float.class);

    //inherited
    public final StringPath tel;

    //inherited
    public final StringPath username;

    public QPTTeacher(String variable) {
        this(PTTeacher.class, forVariable(variable), INITS);
    }

    public QPTTeacher(Path<? extends PTTeacher> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPTTeacher(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPTTeacher(PathMetadata metadata, PathInits inits) {
        this(PTTeacher.class, metadata, inits);
    }

    public QPTTeacher(Class<? extends PTTeacher> type, PathMetadata metadata, PathInits inits) {
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

