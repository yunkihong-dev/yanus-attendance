package com.jpa.yanus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttendanceTime is a Querydsl query type for AttendanceTime
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttendanceTime extends EntityPathBase<AttendanceTime> {

    private static final long serialVersionUID = 1781479415L;

    public static final QAttendanceTime attendanceTime = new QAttendanceTime("attendanceTime");

    public final NumberPath<Long> fri = createNumber("fri", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> mon = createNumber("mon", Long.class);

    public final NumberPath<Long> sat = createNumber("sat", Long.class);

    public final NumberPath<Long> sun = createNumber("sun", Long.class);

    public final NumberPath<Long> thur = createNumber("thur", Long.class);

    public final NumberPath<Long> tue = createNumber("tue", Long.class);

    public final NumberPath<Long> wed = createNumber("wed", Long.class);

    public QAttendanceTime(String variable) {
        super(AttendanceTime.class, forVariable(variable));
    }

    public QAttendanceTime(Path<? extends AttendanceTime> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttendanceTime(PathMetadata metadata) {
        super(AttendanceTime.class, metadata);
    }

}

