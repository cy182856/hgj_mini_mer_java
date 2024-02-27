/**
 * 
 * 上海云之富金融信息服务有限公司
 * Copyright (c) 2014 YunCF,Inc.All Rights Reserved.
 */
package com.ej.hgj.context;

import java.io.Serializable;

/**
 * 
 * @author master.yang
 * @version $Id: OperationContext.java, v 0.1 2014-6-7 下午5:03:22 Administrator Exp $
 */
public class OperationContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1807233192228533127L;

    private String custId;

    private String userId;
    
    private String poSeqId;
    
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPoSeqId() {
        return poSeqId;
    }

    public void setPoSeqId(String poSeqId) {
        this.poSeqId = poSeqId;
    }

}
