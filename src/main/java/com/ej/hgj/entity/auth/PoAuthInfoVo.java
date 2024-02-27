/**
 * 
 * 上海慧管信息服务有限公司
 * Copyright (c) 2014-2020 HuiGuan,Inc.All Rights Reserved.
 */
package com.ej.hgj.entity.auth;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 
 * @author yidan.wu
 * @version $Id: PoAuthInfoVo.java, v 0.1 2020年8月11日 下午1:57:21 Exp $
 */
public class PoAuthInfoVo {
    private String authId;
    private String authLevel;
    private String authDesc;
    private String parentId;
    private String authType;
    private String isAdd;
    private String isUpd;
    private String haveSon;
    private String orderNo;
    private String enbFlag;
    private String action;
    private String isConDisp;
    private List<PoAuthInfoVo> poAuthInfoVoList;

    
    public String getIsConDisp() {
        return isConDisp;
    }
    public void setIsConDisp(String isConDisp) {
        this.isConDisp = isConDisp;
    }
    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
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

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<PoAuthInfoVo> getPoAuthInfoVoList() {
        return poAuthInfoVoList;
    }

    public void setPoAuthInfoVoList(List<PoAuthInfoVo> poAuthInfoVoList) {
        this.poAuthInfoVoList = poAuthInfoVoList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}
