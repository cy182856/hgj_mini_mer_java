package com.ej.hgj.dao.login;

import com.ej.hgj.model.PoSpePostDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface PoSpePostMapper {

	List<PoSpePostDo> queryPoSpePostByCondition(PoSpePostDo poSpePostDo);

}
