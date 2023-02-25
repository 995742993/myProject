package com.xwz.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 1122587455842307444L;
    private String userAccount;
    private String userPassword;

}
