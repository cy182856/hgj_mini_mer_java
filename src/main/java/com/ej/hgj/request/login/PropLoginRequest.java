package com.ej.hgj.request.login;

import lombok.Data;

import java.io.Serializable;

@Data
public class PropLoginRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -547864018670877067L;
    
    // 授权企业微信ID
    private String authCorpId;
    
    // 登录用户userid
    private String userId;
    
    private String ip;
    
    // 登录来源 
    private String loginType;

}
