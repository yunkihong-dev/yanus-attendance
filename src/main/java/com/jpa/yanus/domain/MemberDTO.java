package com.jpa.yanus.domain;

import com.jpa.yanus.type.MemberType;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String memberName;
    private String memberId;
    private String memberPassword;
    private MemberType memberType;


    @Builder
    public MemberDTO(Long id, String memberName, String memberId, String memberPassword, MemberType memberType) {
        this.id = id;
        this.memberName = memberName;
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberType = memberType;
    }
}
