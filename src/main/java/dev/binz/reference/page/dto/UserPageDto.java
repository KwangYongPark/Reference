package dev.binz.reference.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserPageDto {
    private String name;
    private int age;
    private String id;
    private String className; // 'class'는 자바 예약어이므로 'className'으로 변경
}
