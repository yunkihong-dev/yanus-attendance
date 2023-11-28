package com.jpa.yanus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNoWork is a Querydsl query type for NoWork
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoWork extends EntityPathBase<NoWork> {

    private static final long serialVersionUID = 1280891923L;

    public static final QNoWork noWork = new QNoWork("noWork");

    public final StringPath category = createString("category");

    public final StringPath detail = createString("detail");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> uploadDate = createDate("uploadDate", java.time.LocalDate.class);

    public QNoWork(String variable) {
        super(NoWork.class, forVariable(variable));
    }

    public QNoWork(Path<? extends NoWork> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNoWork(PathMetadata metadata) {
        super(NoWork.class, metadata);
    }

}

