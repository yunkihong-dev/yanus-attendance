package com.jpa.yanus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1243670075L;

    public static final QMember member = new QMember("member1");

    public final ListPath<Attendance, QAttendance> attendances = this.<Attendance, QAttendance>createList("attendances", Attendance.class, QAttendance.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memberId = createString("memberId");

    public final StringPath memberName = createString("memberName");

    public final StringPath memberPassword = createString("memberPassword");

    public final EnumPath<com.jpa.yanus.type.StatusType> memberStatus = createEnum("memberStatus", com.jpa.yanus.type.StatusType.class);

    public final NumberPath<Integer> memberTeamNum = createNumber("memberTeamNum", Integer.class);

    public final EnumPath<com.jpa.yanus.type.MemberType> memberType = createEnum("memberType", com.jpa.yanus.type.MemberType.class);

    public final ListPath<NoWork, QNoWork> noWorks = this.<NoWork, QNoWork>createList("noWorks", NoWork.class, QNoWork.class, PathInits.DIRECT2);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

