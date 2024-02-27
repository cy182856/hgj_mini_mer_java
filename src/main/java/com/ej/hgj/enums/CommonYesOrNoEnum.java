package com.ej.hgj.enums;

public enum CommonYesOrNoEnum {
    Y("Y", "是"),
    N("N", "否");

    private String code;
    private String codeDesc;

    private CommonYesOrNoEnum(String code, String codeDesc) {
        this.code = code;
        this.codeDesc = codeDesc;
    }

    public String getCode() {
        return this.code;
    }

    public String getCodeDesc() {
        return this.codeDesc;
    }

    public static CommonYesOrNoEnum valueByCode(String code) {
        CommonYesOrNoEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CommonYesOrNoEnum yesOrNoEnum = var1[var3];
            if (yesOrNoEnum.getCode().equals(code)) {
                return yesOrNoEnum;
            }
        }

        return null;
    }
}
