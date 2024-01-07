package com.jpa.yanus.domain;

import com.jpa.yanus.type.MemberType;
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
    private String memberName;
    private int memberTeamNum;

    @Builder
    public AttendanceMemberJoinDTO(Long id,LocalDateTime checkInTime,LocalDateTime checkOutTime,String memberName, int memberTeamNum){
        this.id = id;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.memberName = memberName;
        this.memberTeamNum = memberTeamNum;
    }
    public String getFormattedCheckInTime() {
        if (this.checkInTime != null) {
            return this.checkInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } else {
            return ""; // 또는 null을 반환하거나 적절한 기본값을 설정
        }
    }
    public String getFormattedCheckOutTime() {
        if (this.checkOutTime != null) {
            return this.checkOutTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } else {
            return ""; // 또는 null을 반환하거나 적절한 기본값을 설정
        }
    }
}
