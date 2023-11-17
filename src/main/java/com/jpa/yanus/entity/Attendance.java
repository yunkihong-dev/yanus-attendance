package com.jpa.yanus.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Getter
@ToString @Setter
@NoArgsConstructor
@Table(name = "tbl_attendance")
public class Attendance {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    @Builder
    public Attendance(Long id, LocalDateTime checkInTime,  LocalDateTime checkOutTime, Member member){
        this.id = id;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.member = member;
    }

}
