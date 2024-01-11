package com.jpa.yanus.domain;

import com.jpa.yanus.entity.Member;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttendanceDTO {
    private Long id;
    private ZonedDateTime checkInTime;
    private ZonedDateTime checkOutTime;
    private Member member;

    @Builder
    public AttendanceDTO(Long id,ZonedDateTime checkInTime,ZonedDateTime checkOutTime,Member member){
        this.id = id;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.member = member;
    }

}
