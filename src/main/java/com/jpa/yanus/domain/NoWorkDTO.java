package com.jpa.yanus.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpa.yanus.entity.Member;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class NoWorkDTO {
    private Long id;
    private String category;
    private String detail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate selectedDate;
    private LocalDate uploadDate;
    private Member member;

    @Builder
    public NoWorkDTO(Long id, String category, String detail, LocalDate selectedDate, LocalDate uploadDate, Member member){
        this.id = id;
        this.category = category;
        this.detail = detail;
        this.selectedDate = selectedDate;
        this.uploadDate = uploadDate;
        this.member = member;
    }

}
