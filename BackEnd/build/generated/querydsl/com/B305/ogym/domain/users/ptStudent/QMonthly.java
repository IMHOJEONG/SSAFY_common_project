package com.B305.ogym.domain.users.ptStudent;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMonthly is a Querydsl query type for Monthly
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMonthly extends EntityPathBase<Monthly> {

    private static final long serialVersionUID = -55788515L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMonthly monthly = new QMonthly("monthly");

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> month = createNumber("month", Integer.class);

    public final QPTStudent ptStudent;

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QMonthly(String variable) {
        this(Monthly.class, forVariable(variable), INITS);
    }

    public QMonthly(Path<? extends Monthly> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMonthly(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMonthly(PathMetadata metadata, PathInits inits) {
        this(Monthly.class, metadata, inits);
    }

    public QMonthly(Class<? extends Monthly> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ptStudent = inits.isInitialized("ptStudent") ? new QPTStudent(forProperty("ptStudent"), inits.get("ptStudent")) : null;
    }

}

