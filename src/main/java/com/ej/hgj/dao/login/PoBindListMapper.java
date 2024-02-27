package com.ej.hgj.dao.login;

import com.ej.hgj.model.PoBindListDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper
public interface PoBindListMapper {

    PoBindListDo selectByPrimaryKey(@Param("authCorpId") String authCorpId, @Param("ewUsrId") String ewUsrId);

    
}