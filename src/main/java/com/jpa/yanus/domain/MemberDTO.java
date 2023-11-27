package com.jpa.yanus.domain;

import com.jpa.yanus.type.MemberType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

}
