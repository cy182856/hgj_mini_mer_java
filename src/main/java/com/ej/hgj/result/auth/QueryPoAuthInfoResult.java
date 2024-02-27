/**
 * 
 * 上海慧管信息服务有限公司
 * Copyright (c) 2014-2020 HuiGuan,Inc.All Rights Reserved.
 */
package com.ej.hgj.result.auth;

import com.ej.hgj.result.base.BaseResult;
import com.ej.hgj.result.dto.PoAuthInfoDto;

import java.util.List;

/**
 * 
 * @author yidan.wu
 * @version $Id: QueryPoAuthInfoResult.java, v 0.1 2020年8月18日 下午1:48:21 Exp $
 */
public class QueryPoAuthInfoResult extends BaseResult {

    /**  */
    private static final long serialVersionUID = 8431158413841169738L;

    private List<PoAuthInfoDto> poAuthInfoDto;

    public List<PoAuthInfoDto> getPoAuthInfoDto() {
        return poAuthInfoDto;
    }

    public void setPoAuthInfoDto(List<PoAuthInfoDto> poAuthInfoDto) {
        this.poAuthInfoDto = poAuthInfoDto;
    }
    
}
