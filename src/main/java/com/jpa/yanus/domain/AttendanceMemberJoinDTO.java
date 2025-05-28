package com.jpa.yanus.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttendanceMemberJoinDTO {
    private Long id;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private LocalDateTime checkOutDate; // 체크아웃 날짜 그대로 사용
    private String memberName;
    private int memberTeamNum;

    @Builder
    public AttendanceMemberJoinDTO(Long id, LocalDateTime checkInTime, LocalDateTime checkOutTime, LocalDateTime checkOutDate, String memberName, int memberTeamNum) {
        this.id = id;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.checkOutDate = checkOutDate;
        this.memberName = memberName;
        this.memberTeamNum = memberTeamNum;
    }

    public String getFormattedCheckInTime() {
        if (this.checkInTime != null) {
            return this.checkInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } else {
            return "";
        }
    }

    public String getFormattedCheckOutTime() {
        if (this.checkOutTime != null) {
            return this.checkOutTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } else {
            return "";
        }
    }

    public String getFormattedCheckOutDate() {
        if (this.checkOutDate != null) {
            return this.checkOutDate.format(DateTimeFormatter.ofPattern("MM월 dd일"));
        } else {
            return "";
        }
    }
}
