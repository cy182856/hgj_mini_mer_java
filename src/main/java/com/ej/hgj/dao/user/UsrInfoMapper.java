package com.ej.hgj.dao.user;


import com.ej.hgj.model.UsrInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UsrInfoMapper {

    UsrInfoDO selectByPrimaryKey(String custId);
}