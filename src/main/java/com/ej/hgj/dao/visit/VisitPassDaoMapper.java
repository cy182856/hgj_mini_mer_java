package com.ej.hgj.dao.visit;

import com.ej.hgj.entity.visit.VisitPass;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface VisitPassDaoMapper {

    VisitPass getById(String id);

    VisitPass getByRandNum(String randNum);

    List<VisitPass> getList(VisitPass visitPass);

    List<VisitPass> getCstRanNumList(VisitPass visitPass);

    void save(VisitPass visitPass);

    void update(VisitPass visitPass);

    void delete(String id);

}
