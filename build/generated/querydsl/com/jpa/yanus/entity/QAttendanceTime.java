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

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final BooleanPath fri = createBoolean("fri");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath mon = createBoolean("mon");

    public final BooleanPath sat = createBoolean("sat");

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final BooleanPath sun = createBoolean("sun");

    public final NumberPath<Integer> teamNum = createNumber("teamNum", Integer.class);

    public final BooleanPath thu = createBoolean("thu");

    public final BooleanPath tue = createBoolean("tue");

    public final DateTimePath<java.time.LocalDateTime> uploadDate = createDateTime("uploadDate", java.time.LocalDateTime.class);

    public final BooleanPath wed = createBoolean("wed");

    public final DateTimePath<java.time.LocalDateTime> workStartTime = createDateTime("workStartTime", java.time.LocalDateTime.class);

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

