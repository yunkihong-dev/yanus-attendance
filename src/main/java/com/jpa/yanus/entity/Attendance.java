package com.jpa.yanus.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity @Getter
@ToString @Setter
@NoArgsConstructor
@Table(name = "tbl_attendance")
public class Attendance {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull private ZonedDateTime checkInTime;
    private ZonedDateTime checkOutTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;


    @Builder
    public Attendance(Long id, ZonedDateTime checkInTime,  ZonedDateTime checkOutTime, Member member){
        this.id = id;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.member = member;
    }

}
