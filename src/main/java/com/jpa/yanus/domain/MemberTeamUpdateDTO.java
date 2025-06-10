package com.jpa.yanus.domain;


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
public class MemberTeamUpdateDTO {
    private Long memberId;
    private int teamNum;

}