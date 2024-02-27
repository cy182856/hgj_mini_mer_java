package com.ej.hgj.enums;

public enum LoginTypeEnum {
    EW("EW", "企业微信"),
    DD("DD", "钉钉"),
    WX("WX", "微信公众号"),
    AM("AM", "安卓手机APP"),
    IM("IM", "苹果手机APP"),
    PC("PC", "PC网页版"),
    AP("AP", "安卓平板"),
    IP("IP", "IOS平板");

    private String loginType;
    private String loginTypeDesc;

    private LoginTypeEnum(String loginType, String loginTypeDesc) {
        this.loginType = loginType;
        this.loginTypeDesc = loginTypeDesc;
    }

    public String getLoginType() {
        return this.loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getLoginTypeDesc() {
        return this.loginTypeDesc;
    }

    public void setLoginTypeDesc(String loginTypeDesc) {
        this.loginTypeDesc = loginTypeDesc;
    }
}
