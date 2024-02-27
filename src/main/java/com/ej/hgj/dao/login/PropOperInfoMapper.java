package com.ej.hgj.dao.login;
import com.ej.hgj.model.PropOperInfoDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper
public interface PropOperInfoMapper {

	PropOperInfoDo selectByPrimaryKey(@Param("custId") String custId, @Param("poSeqId") String poSeqId);

}
