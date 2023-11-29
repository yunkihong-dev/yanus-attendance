package com.jpa.yanus.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jpa.yanus.type.MemberType;
import com.jpa.yanus.type.StatusType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @ToString(exclude = {"attendances", "noWorks"})
@Getter @Setter
@NoArgsConstructor
@Table(name = "tbl_member")
public class Member {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    private String memberName;
    private String memberId;
    private String memberPassword;
    private int memberTeamNum;
    private StatusType memberStatus;
    private MemberType memberType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<NoWork> noWorks;

    @Builder
    public Member(Long id, String memberName, String memberId, String memberPassword, MemberType memberType, List<Attendance> attendances, List<NoWork> noWorks) {
        this.id = id;
        this.memberName = memberName;
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberType = memberType;
        this.attendances = attendances;
        this.noWorks = noWorks;
    }
}

