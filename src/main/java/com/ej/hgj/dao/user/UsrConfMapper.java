package com.ej.hgj.dao.user;

import com.ej.hgj.model.UsrConfDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UsrConfMapper {

    UsrConfDO selectByPrimaryKey(String custId);

}