package com.jpa.yanus.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jpa.yanus.type.MemberType;
import lombok.*;

import javax.persistence.*;
import java.util.List;
@Entity @ToString
@Getter @Setter
@NoArgsConstructor
@Table(name = "tbl_member")
public class Member {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    private String memberName;
    private String memberId;
    private String memberPassword;
    private MemberType memberType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Attendance> attendances;


    @Builder
    public Member(Long id, String memberName, String memberId, String memberPassword, MemberType memberType, List<Attendance> attendances) {
        this.id = id;
        this.memberName = memberName;
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberType = memberType;
        this.attendances = attendances;
    }
}

