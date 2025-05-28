package com.jpa.yanus.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    // Boolean for each day to indicate attendance (true: workday, false: non-workday)
    private Boolean mon;
    private Boolean tue;
    private Boolean wed;
    private Boolean thu;
    private Boolean fri;
    private Boolean sat;
    private Boolean sun;

    // Team number reference
    private int teamNum;

    // Start and end dates for the applicable period
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Timestamps for creation or update
    private LocalDateTime uploadDate;

    // Work start and end times
    private LocalDateTime workStartTime;

    @Builder
    public AttendanceTime(Long id, Boolean mon, Boolean tue, Boolean wed, Boolean thu, Boolean fri, Boolean sat, Boolean sun,
                          int teamNum, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime uploadDate,
                          LocalDateTime workStartTime) {
        this.id = id;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        this.teamNum = teamNum;
        this.startDate = startDate;
        this.endDate = endDate;
        this.uploadDate = uploadDate;
        this.workStartTime = workStartTime;
    }
}