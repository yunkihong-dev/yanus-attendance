package com.jpa.yanus.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpa.yanus.type.StatusType;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class NoWorkWithOutMemberDTO {
    private Long id;
    private String category;
    private String detail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate selectedDate;
    private LocalDate uploadDate;
    private StatusType status;

    @Builder
    public NoWorkWithOutMemberDTO(Long id, String category, String detail, LocalDate selectedDate, LocalDate uploadDate, StatusType status) {
        this.id = id;
        this.category = category;
        this.detail = detail;
        this.selectedDate = selectedDate;
        this.uploadDate = uploadDate;
        this.status = status;
    }
}