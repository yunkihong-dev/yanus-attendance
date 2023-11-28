package com.jpa.yanus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoWork is a Querydsl query type for NoWork
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoWork extends EntityPathBase<NoWork> {

    private static final long serialVersionUID = 1280891923L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoWork noWork = new QNoWork("noWork");

    public final StringPath category = createString("category");

    public final StringPath detail = createString("detail");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final DatePath<java.time.LocalDate> selectedDate = createDate("selectedDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> uploadDate = createDate("uploadDate", java.time.LocalDate.class);

    public QNoWork(String variable) {
        this(NoWork.class, forVariable(variable), INITS);
    }

    public QNoWork(Path<? extends NoWork> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoWork(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoWork(PathMetadata metadata, PathInits inits) {
        this(NoWork.class, metadata, inits);
    }

    public QNoWork(Class<? extends NoWork> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

