package com.example.spring.web.auth.vo;

import lombok.Data;

@Data
public class CustomPrincipal {

    private String account;

    private String username;

    private String email;

    private String phone;

    private Integer workNo;

    private String headerImage;

}
