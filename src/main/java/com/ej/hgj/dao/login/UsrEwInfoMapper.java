package com.ej.hgj.dao.login;

import com.ej.hgj.model.UsrEwInfoDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UsrEwInfoMapper {

    UsrEwInfoDo selectByPrimaryKey(String authCorpId);

}