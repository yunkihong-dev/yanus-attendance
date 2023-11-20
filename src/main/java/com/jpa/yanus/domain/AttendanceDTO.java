package com.jpa.yanus.domain;

import com.jpa.yanus.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
}
