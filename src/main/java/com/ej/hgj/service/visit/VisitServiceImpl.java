package com.ej.hgj.service.visit;

import com.ej.hgj.dao.visit.VisitLogDaoMapper;
import com.ej.hgj.dao.visit.VisitPassDaoMapper;
import com.ej.hgj.entity.visit.VisitLog;
import com.ej.hgj.entity.visit.VisitPass;
import com.ej.hgj.utils.GenerateUniqueIdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Transactional
@Service
public class VisitServiceImpl implements VisitService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VisitLogDaoMapper visitLogDaoMapper;

    @Autowired
    private VisitPassDaoMapper visitPassDaoMapper;

    @Override
    public VisitPass saveVisitLog(VisitPass visitPass) {
        // 保存通行记录
        VisitLog visitLog = new VisitLog();
        visitLog.setId(GenerateUniqueIdUtil.getGuid());
        visitLog.setPassId(visitPass.getId());
        visitLog.setProNum(visitPass.getProNum());
        visitLog.setProName(visitPass.getProName());
        visitLog.setPassUrl(visitPass.getPassUrl());
        visitLog.setRandNum(visitPass.getRandNum());
        visitLog.setWxOpenId(visitPass.getWxOpenId());
        visitLog.setCstCode(visitPass.getCstCode());
        visitLog.setCstName(visitPass.getCstName());
        visitLog.setCstMobile(visitPass.getCstMobile());
        visitLog.setHouseId(visitPass.getHouseId());
        visitLog.setVisitName(visitPass.getVisitName());
        visitLog.setCarNum(visitPass.getCarNum());
        visitLog.setCreateTime(new Date());
        visitLog.setUpdateTime(new Date());
        visitLog.setDeleteFlag(0);
        visitLogDaoMapper.save(visitLog);
        //更新通行码有效次数
        VisitPass vp = new VisitPass();
        vp.setId(visitPass.getId());
        // 剩余次数
        Integer resNum = visitPass.getResNum();
        if(resNum != -1){
            vp.setResNum(resNum - 1);
            visitPass.setResNum(resNum - 1);
        }
        vp.setUpdateTime(new Date());
        visitPassDaoMapper.update(vp);
        return visitPass;
    }
}
