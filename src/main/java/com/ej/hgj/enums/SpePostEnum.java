package com.ej.hgj.enums;

import org.apache.commons.lang3.StringUtils;

public enum SpePostEnum {
    S_01("01", "维修员"),
    S_02("02", "维修分配员"),
    S_03("03", "邻里圈审核员"),
    S_04("04", "门禁安保"),
    S_05("05", "住户入住审核员"),
    S_06("06", "客服人员"),
    S_07("07", "财务记账员");

    private String code;
    private String desc;

    private SpePostEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static SpePostEnum getMatched(String code) {
        SpePostEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            SpePostEnum spePostEnum = var1[var3];
            if (StringUtils.equals(code, spePostEnum.getCode())) {
                return spePostEnum;
            }
        }

        return null;
    }

}
