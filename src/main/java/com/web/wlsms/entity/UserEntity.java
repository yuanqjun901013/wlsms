package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserEntity {
    private long id;
    private String userNo;
    private String userName;
    private String pwd;
    private Integer sex;
    private Integer age;
    private String job;
    private String tel;
    private String phone;
    private String email;
}
