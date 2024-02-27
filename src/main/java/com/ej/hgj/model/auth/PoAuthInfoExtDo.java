/**
 * 
 * 上海慧管信息服务有限公司
 * Copyright (c) 2014-2020 HuiGuan,Inc.All Rights Reserved.
 */
package com.ej.hgj.model.auth;

/**
 * 
 * @author yidan.wu
 * @version $Id: PoAuthInfoExtDo.java, v 0.1 2020年8月11日 下午2:32:36 Exp $
 */
public class PoAuthInfoExtDo extends PoAuthInfoDo{

    private String authLevel;
    private String authDesc;
    private String parentId;
    private String haveSon;
    private String orderNo;
    private String enbFlag;
    private String authType;
    private String action;
    private String isConDisp;
    
    public String getIsConDisp() {
        return isConDisp;
    }
    public void setIsConDisp(String isConDisp) {
        this.isConDisp = isConDisp;
    }
    public String getAuthLevel() {
        return authLevel;
    }
    public void setAuthLevel(String authLevel) {
        this.authLevel = authLevel;
    }
    public String getAuthDesc() {
        return authDesc;
    }
    public void setAuthDesc(String authDesc) {
        this.authDesc = authDesc;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getHaveSon() {
        return haveSon;
    }
    public void setHaveSon(String haveSon) {
        this.haveSon = haveSon;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getEnbFlag() {
        return enbFlag;
    }
    public void setEnbFlag(String enbFlag) {
        this.enbFlag = enbFlag;
    }
    public String getAuthType() {
        return authType;
    }
    public void setAuthType(String authType) {
        this.authType = authType;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    
}
