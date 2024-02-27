package com.ej.hgj.service.auth;

import com.ej.hgj.entity.auth.PoAuthInfoVo;

import java.util.List;

public interface PoAuthInfoService {

    List<PoAuthInfoVo> getPoLoginMenu(String custId, String poSeqId);
}
