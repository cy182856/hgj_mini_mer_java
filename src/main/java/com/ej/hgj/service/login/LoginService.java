package com.ej.hgj.service.login;

public interface LoginService {

    // 企业微信登录
    String login(String code, String suiteId);

    // 企业微信服务商登录
    String serviceLogin(String code, String suiteId);



}
