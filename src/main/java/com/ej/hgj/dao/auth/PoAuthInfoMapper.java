package com.ej.hgj.dao.auth;

import com.ej.hgj.model.auth.PoAuthInfoExtDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface PoAuthInfoMapper {

    List<PoAuthInfoExtDo> selectPoAuthInfo(PoAuthInfoExtDo poAuthInfoExtDo);

}