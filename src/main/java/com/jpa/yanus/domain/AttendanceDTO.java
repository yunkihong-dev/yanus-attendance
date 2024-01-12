package com.jpa.yanus.domain;

import com.jpa.yanus.entity.Member;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttendanceDTO {
    private Long id;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private Member member;

    @Builder
    public AttendanceDTO(Long id,LocalDateTime checkInTime,LocalDateTime checkOutTime,Member member){
        this.id = id;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.member = member;
    }

}
