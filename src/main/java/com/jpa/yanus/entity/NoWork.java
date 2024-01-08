package com.jpa.yanus.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jpa.yanus.type.StatusType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @ToString(exclude = "member")
@Getter @Setter
@NoArgsConstructor
@Table(name = "tbl_nowork")
public class NoWork {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    private String category;
    private String detail;
    private LocalDate selectedDate;
    private LocalDate uploadDate;
    private StatusType status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    @Builder
    public NoWork(Long id, String category, String detail,LocalDate selectedDate, LocalDate uploadDate, StatusType status, Member member){
        this.id = id;
        this.category = category;
        this.detail = detail;
        this.selectedDate = selectedDate;
        this.uploadDate = uploadDate;
        this.status = status;
        this.member = member;
    }
}
