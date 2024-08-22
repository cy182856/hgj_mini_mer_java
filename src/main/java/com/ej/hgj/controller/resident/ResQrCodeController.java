package com.ej.hgj.controller.resident;

import com.alibaba.fastjson.JSONObject;
import com.ej.hgj.controller.base.BaseController;
import com.ej.hgj.dao.config.ProConfDaoMapper;
import com.ej.hgj.dao.cst.HgjCstDaoMapper;
import com.ej.hgj.dao.hu.CstIntoMapper;
import com.ej.hgj.dao.hu.HgjHouseDaoMapper;
import com.ej.hgj.dao.user.UserDaoMapper;
import com.ej.hgj.dao.visit.VisitLogDaoMapper;
import com.ej.hgj.dao.visit.VisitPassDaoMapper;
import com.ej.hgj.entity.config.ProConfig;
import com.ej.hgj.entity.cst.HgjCst;
import com.ej.hgj.entity.hu.CstInto;
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
public class ResQrCodeController extends BaseController {

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

    @Autowired
    private CstIntoMapper cstIntoMapper;

    @Autowired
    private ProConfDaoMapper proConfDaoMapper;

    @Autowired
    private HgjCstDaoMapper hgjCstDaoMapper;

    @RequestMapping("/res/checkResQrCode")
    @ResponseBody
    public JSONObject checkHuCheckCode(CheckCodeVo checkCodeVo) {
        JSONObject jsonObject = new JSONObject();
        String checkCodeParam = checkCodeVo.getCheckCodeParam();
        if(StringUtils.isBlank(checkCodeParam)){
            jsonObject.put("RESPCODE", "999");
            jsonObject.put("ERRDESC", "请求参数错误！");
            return jsonObject;
        }
        String[] param = checkCodeParam.split(",");
        String wxOpenId = param[0];
        // 查询住户绑定信息
        CstInto cstInto = cstIntoMapper.getByWxOpenIdAndStatus_1(wxOpenId);
        if(cstInto != null){
            // 获取项目名
            ProConfig proConfig = proConfDaoMapper.getByProjectNum(cstInto.getProjectNum());
            // 获取客户名称
            String newCstName = "";
            String userName = cstInto.getUserName();
            if(StringUtils.isNotBlank(userName)){
                newCstName = userName.substring(0,1) + "***";
            }
            /**
            HgjCst hgjCst = hgjCstDaoMapper.getByCstCode(cstInto.getCstCode());
            if(hgjCst != null){
                String cstName = hgjCst.getCstName();
                if(StringUtils.isNotBlank(cstName)) {
                    String[] strCstName = cstName.split(",");
                    if (strCstName.length == 1) {
                        newCstName = strCstName[0].substring(0, 1) + "***";
                    }
                    if (strCstName.length > 1) {
                        for (int i = 0; i<strCstName.length; i++){
                            newCstName += strCstName[i].substring(0, 1) + "***，";
                        }
                        newCstName = newCstName.substring(0,newCstName.length()-1);
                    }
                }
            }else {
                jsonObject.put("RESPCODE", "999");
                jsonObject.put("ERRDESC", "验证失败！");
                return jsonObject;
            }
            */

            // 二维码创建时间
            Long createDate = Long.valueOf(param[1]);
            // 当前时间
            Long sysTemDate = System.currentTimeMillis();
            // 计算时间差
            long diff = sysTemDate - createDate;
            if(diff > 5 * 60 * 1000){
                jsonObject.put("RESPCODE", "999");
                jsonObject.put("ERRDESC", "二维码已失效！！");
                return jsonObject;
            }
            jsonObject.put("ERRCODE", "01010000");
            jsonObject.put("proName", proConfig.getProjectName());
            jsonObject.put("cstName", newCstName);
        }else {
          jsonObject.put("RESPCODE", "999");
          jsonObject.put("ERRDESC", "验证失败！");
          return jsonObject;
        }
    	return jsonObject;
    }

}
