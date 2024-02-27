/**
 * 
 * 上海慧管信息服务有限公司
 * Copyright (c) 2014-2020 HuiGuan,Inc.All Rights Reserved.
 */
package com.ej.hgj.request.auth;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 
 * @author yidan.wu
 * @version $Id: QueryPoAuthInfoRequest.java, v 0.1 2020年8月18日 下午1:45:51 Exp $
 */
public class QueryPoAuthInfoRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -547864018670877067L;
    
    private String custId;
    private String authId;
    private String poSeqId;
    private String stat;
    private String isAdd;
    private String isUpd;
    private String isConDisp;//某些权限仅用来控制，不用在菜单显示 Y:是,N:否
    
    public String getIsConDisp() {
        return isConDisp;
    }
    public void setIsConDisp(String isConDisp) {
        this.isConDisp = isConDisp;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getPoSeqId() {
        return poSeqId;
    }

    public void setPoSeqId(String poSeqId) {
        this.poSeqId = poSeqId;
    }

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
