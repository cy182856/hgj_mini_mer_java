package com.ej.hgj.controller.visit;

import com.alibaba.fastjson.JSONObject;
import com.ej.hgj.controller.base.BaseController;
import com.ej.hgj.dao.hu.HgjHouseDaoMapper;
import com.ej.hgj.dao.user.UserDaoMapper;
import com.ej.hgj.dao.visit.VisitLogDaoMapper;
import com.ej.hgj.dao.visit.VisitPassDaoMapper;
import com.ej.hgj.entity.hu.HgjHouse;
import com.ej.hgj.entity.user.User;
import com.ej.hgj.entity.visit.VisitLog;
import com.ej.hgj.entity.visit.VisitPass;
import com.ej.hgj.enums.MonsterBasicRespCode;
import com.ej.hgj.service.visit.VisitService;
import com.ej.hgj.utils.DateUtils;
import com.ej.hgj.utils.TokenUtils;
import com.ej.hgj.vo.visit.CheckCodeVo;
import com.ej.hgj.vo.visit.VisitLogInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class VisitController extends BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VisitPassDaoMapper visitPassDaoMapper;

    @Autowired
    private HgjHouseDaoMapper hgjHouseDaoMapper;

    @Autowired
    private VisitService visitService;

    @Autowired
    private UserDaoMapper userDaoMapper;

    @Autowired
    private VisitLogDaoMapper visitLogDaoMapper;

    @RequestMapping("/hu/checkHuCheckCode")
    @ResponseBody
    public JSONObject checkHuCheckCode(CheckCodeVo checkCodeVo) {
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isBlank(checkCodeVo.getCheckCodeParam())){
            jsonObject.put("RESPCODE", "999");
            jsonObject.put("ERRDESC", "请求参数错误");
            return jsonObject;
        }
        // 验证访客信息有效性
        VisitPass visitPass = visitPassDaoMapper.getById(checkCodeVo.getCheckCodeParam());
        if(visitPass != null){
            // 剩余次数
            Integer resNum = visitPass.getResNum();
            // 有效时间-小时  默认为24小时
            // Integer effectiveTime = visitPass.getEffectiveTime();
            // 创建时间
            Date createTime = visitPass.getCreateTime();
            // 生效日期-前端显示用
            visitPass.setEffectuateDate(createTime);
            // 超时
            // if(DateUtils.isOutTime(createTime,effectiveTime) == true){
            if(DateUtils.isToday(createTime) == false){
                jsonObject.put("RESPCODE", "999");
                jsonObject.put("ERRDESC", "访客码超时");
                return jsonObject;
            }
            // 次数已用完
            if(resNum == 0){
                jsonObject.put("RESPCODE", "999");
                jsonObject.put("ERRDESC", "次数已用完");
                return jsonObject;
            }
        }else {
          jsonObject.put("RESPCODE", "999");
          jsonObject.put("ERRDESC", "访客码不存在");
          return jsonObject;
        }
        // 保存通行记录，更新访客码有效次数
        VisitPass vp = visitService.saveVisitLog(visitPass);
        // 前端显示数据
        HgjHouse hgjHouse = hgjHouseDaoMapper.getById(visitPass.getHouseId());
        visitPass.setHouseName(hgjHouse.getBudName()+"-"+hgjHouse.getResName());
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(visitPass.getCreateTime());
//        cal.add(Calendar.HOUR_OF_DAY, visitPass.getEffectiveTime());
//        Date newDate = cal.getTime();
//        visitPass.setExpDate(newDate);
        jsonObject.put("ERRCODE", "01010000");
        jsonObject.put("visitPass", vp);
    	return jsonObject;
    }

    @RequestMapping("/hu/checkHuCheckRanNum")
    @ResponseBody
    public JSONObject checkHuCheckRanNum(CheckCodeVo checkCodeVo) {
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isBlank(checkCodeVo.getCheckCodeParam())){
            jsonObject.put("RESPCODE", "999");
            jsonObject.put("ERRDESC", "请求参数错误");
            return jsonObject;
        }
        // 验证访客信息有效性
        VisitPass visitPass = visitPassDaoMapper.getByRandNum(checkCodeVo.getCheckCodeParam());
        if(visitPass != null){
            // 创建时间
            Date createTime = visitPass.getCreateTime();
            // 生效日期-前端显示用
            visitPass.setEffectuateDate(createTime);
        }else {
            jsonObject.put("RESPCODE", "999");
            jsonObject.put("ERRDESC", "无效通行码");
            return jsonObject;
        }
        // 保存通行记录，更新访客码有效次数
        VisitPass vp = visitService.saveVisitLog(visitPass);
        // 前端显示数据
        //HgjHouse hgjHouse = hgjHouseDaoMapper.getById(visitPass.getHouseId());
        //visitPass.setHouseName(hgjHouse.getBudName()+"-"+hgjHouse.getResName());
        jsonObject.put("ERRCODE", "01010000");
        jsonObject.put("visitPass", vp);
        return jsonObject;
    }

    @RequestMapping("/visitinfo/queryVisitLogInfos")
    @ResponseBody
    public VisitLogInfoVo queryVisitLogInfos(HttpServletRequest request, VisitLogInfoVo visitLogVo) {
        StringBuilder sd = new StringBuilder(visitLogVo.getBeginVisitDate());
        sd.insert(4, "-");
        sd.insert(7, "-");
        String startTime = sd.toString();

        StringBuilder ed = new StringBuilder(visitLogVo.getEndVisitDate());
        ed.insert(4, "-");
        ed.insert(7, "-");
        String endTime = ed.toString();

        String userId = TokenUtils.getUserId(request);
        User user = userDaoMapper.getByStaffId(userId);
        PageHelper.offsetPage((visitLogVo.getPageNum()-1) * visitLogVo.getPageSize(),visitLogVo.getPageSize());
        VisitLog visitLog = new VisitLog();
        visitLog.setProNum(user.getProjectNum());
        visitLog.setStartTime(startTime);
        visitLog.setEndTime(endTime);
        List<VisitLog> list = visitLogDaoMapper.getList(visitLog);
        PageInfo<VisitLog> pageInfo = new PageInfo<>(list);
        int pageNumTotal = (int) Math.ceil((double)pageInfo.getTotal()/(double)visitLogVo.getPageSize());
        list = pageInfo.getList();
        logger.info("list返回记录数");
        logger.info(list != null ? list.size() + "":0 + "");
        visitLogVo.setPages(pageNumTotal);
        visitLogVo.setTotalNum((int) pageInfo.getTotal());
        visitLogVo.setPageSize(visitLogVo.getPageSize());

        for(VisitLog v : list){
            // 前端显示数据
            HgjHouse hgjHouse = hgjHouseDaoMapper.getById(v.getHouseId());
            if(hgjHouse != null){
                v.setHouseName(hgjHouse.getBudName()+"-"+hgjHouse.getResName());
            }
            v.setEffectuateDate(v.getCreateTime());
        }
        visitLogVo.setList(list);
        visitLogVo.setRespCode(MonsterBasicRespCode.SUCCESS.getReturnCode());
        return visitLogVo;
    }

}
