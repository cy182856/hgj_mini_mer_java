package com.ej.hgj.result.login;

import lombok.Data;

@Data
public class LoginResult {

    private String respCode;

    private String errCode;

    private String errDesc;

    private String token;

    private String userName;

}
