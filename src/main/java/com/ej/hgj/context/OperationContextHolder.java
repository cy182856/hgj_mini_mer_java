/**
 * 
 * 上海云之富金融信息服务有限公司
 * Copyright (c) 2014 YunCF,Inc.All Rights Reserved.
 */
package com.ej.hgj.context;

/**
 * 
 * @author master.yang
 * @version $Id: OperationContextHolder.java, v 0.1 2014-6-7 下午5:08:26 Administrator Exp $
 */
public class OperationContextHolder {

    public static final String operationContextSessionKey = "jiamsvOperationContextSessionKey";

    private static final ThreadLocal threadLocal = new ThreadLocal();

    public static OperationContext get() {
        return (OperationContext) threadLocal.get();
    }

    public static void setOperationContext(OperationContext context) {
        threadLocal.set(context);
    }
    
}
