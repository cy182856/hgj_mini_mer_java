package com.ej.hgj.enums;

public enum NormalStatEnum {
    N("N", "正常"),
    C("C", "关闭");

    private String code;
    private String codeDesc;

    private NormalStatEnum(String code, String codeDesc) {
        this.code = code;
        this.codeDesc = codeDesc;
    }

    public String getCode() {
        return this.code;
    }

    public String getCodeDesc() {
        return this.codeDesc;
    }

    public static NormalStatEnum valueByCode(String code) {
        NormalStatEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            NormalStatEnum stat = var1[var3];
            if (stat.getCode().equals(code)) {
                return stat;
            }
        }

        return null;
    }
}
