/**
 * 
 * 上海云之富金融信息服务有限公司
 * Copyright (c) 2014 YunCF,Inc.All Rights Reserved.
 */
package com.ej.hgj.common;

import com.ej.hgj.utils.ValidateUtils;
import java.text.SimpleDateFormat;


/**
 * 提供一些公共的方法，供使用
 * 简化代码
 * @author master.yang
 * @version $Id: CommonBusiness.java, v 0.1 2014-6-30 下午1:58:14 master.yang Exp $
 */
public abstract class CommonBusiness {

	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");


    protected boolean isMatched(String source, String target) {
        return ValidateUtils.isStrValueMatched(source, target);
    }
    protected boolean isNotMatched(String source, String target) {
        return !isMatched(source, target);
    }


}
