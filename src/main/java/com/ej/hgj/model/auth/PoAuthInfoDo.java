package com.ej.hgj.model.auth;

public class PoAuthInfoDo extends PoAuthInfoKeyDo {
    private String stat;

    private String isAdd;

    private String isUpd;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(String isAdd) {
        this.isAdd = isAdd;
    }

    public String getIsUpd() {
        return isUpd;
    }

    public void setIsUpd(String isUpd) {
        this.isUpd = isUpd;
    }
    
    /** 
     * @see com.monster.common.dal.model.PoAuthInfoKeyDo#toString()
     */
    @Override
    public String toString() {
        return super.toString();
    }
}