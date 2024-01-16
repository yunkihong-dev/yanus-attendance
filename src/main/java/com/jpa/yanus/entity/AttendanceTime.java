package com.jpa.yanus.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@ToString
@Setter
@NoArgsConstructor
@Table(name = "tbl_attendance_time")
public class AttendanceTime {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    private Long mon;
    private Long tue;
    private Long wed;
    private Long thur;
    private Long fri;
    private Long sat;
    private Long sun;

    @Builder
    public AttendanceTime(Long id, Long mon,Long tue,Long wed,Long thur,Long fri,Long sat,Long sun ){
        this.id = id;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thur = thur;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
    }
}
