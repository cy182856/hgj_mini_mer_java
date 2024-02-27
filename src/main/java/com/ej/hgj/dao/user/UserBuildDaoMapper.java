package com.ej.hgj.dao.user;

import com.ej.hgj.entity.user.UserBuild;
import com.ej.hgj.entity.user.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserBuildDaoMapper {



    List<UserBuild> getList(UserBuild userBuild);


}
