package com.ej.hgj.model.auth;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PoAuthInfoKeyDo {
    private String authId;

    private String custId;

    private String poSeqId;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPoSeqId() {
        return poSeqId;
    }

    public void setPoSeqId(String poSeqId) {
        this.poSeqId = poSeqId;
    }
    
    /** 
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}