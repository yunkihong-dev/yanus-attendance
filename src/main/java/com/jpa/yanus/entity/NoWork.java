package com.jpa.yanus.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@ToString(exclude = "member")
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tbl_nowork")
public class NoWork {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    private String category;
    private String detail;
    private LocalDate uploadDate;
}
