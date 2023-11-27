package com.jpa.yanus.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@ToString(exclude = "member")@Setter
@NoArgsConstructor
@Table(name = "tbl_attendance")
public class Attendance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;


    @Builder
    public Attendance(Long id, LocalDateTime checkInTime,  LocalDateTime checkOutTime, Member member){
        this.id = id;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.member = member;
    }

}
