package com.jpa.yanus.domain;


import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttendanceForWeekDTO {
    private Long Mon;
    private Long Tue;
    private Long Wed;
    private Long Thur;
    private Long Fri;
    private Long Sat;
    private Long Sun;

    @Builder
    public AttendanceForWeekDTO(Long Mon,Long Tue, Long Wed, Long Thur,Long Fri,Long Sat, Long Sun){
        this.Mon = Mon;
        this.Tue = Tue;
        this.Wed = Wed;
        this.Thur = Thur;
        this.Fri = Fri;
        this.Sat = Sat;
        this.Sun = Sun;
    }
}
