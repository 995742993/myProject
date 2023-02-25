package com.xwz.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 1364795366637242166L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;
}
