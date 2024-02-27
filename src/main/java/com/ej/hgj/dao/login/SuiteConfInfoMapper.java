package com.ej.hgj.dao.login;

import com.ej.hgj.model.SuiteConfInfoDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SuiteConfInfoMapper {

    SuiteConfInfoDo selectByPrimaryKey(String suiteId);

}