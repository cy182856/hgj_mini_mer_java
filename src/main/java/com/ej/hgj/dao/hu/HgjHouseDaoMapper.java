package com.ej.hgj.dao.hu;

import com.ej.hgj.entity.hu.HgjHouse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface HgjHouseDaoMapper {

    HgjHouse getById(String id);

    List<HgjHouse> getList(HgjHouse hgjHouse);

    List<HgjHouse> getListByCstCode(String cstCode);

    List<HgjHouse> queryStages (String orgId);

    List<HgjHouse> queryBuilding (String grpCode, String orgId);

    List<HgjHouse> queryRoomNum (String budId);

    List<HgjHouse> queryByMobile (String mobile);




}
