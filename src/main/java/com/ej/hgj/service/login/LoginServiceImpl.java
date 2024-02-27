package com.ej.hgj.service.login;

import com.alibaba.fastjson.JSONObject;
import com.ej.hgj.constant.Constant;
import com.ej.hgj.dao.config.ConstantConfDaoMapper;
import com.ej.hgj.dao.login.*;
import com.ej.hgj.dao.user.UserDaoMapper;
import com.ej.hgj.dao.user.UsrConfMapper;
import com.ej.hgj.dao.user.UsrInfoMapper;
import com.ej.hgj.entity.config.ConstantConfig;
import com.ej.hgj.entity.login.LoginInfo;
import com.ej.hgj.entity.login.PoSpePostVo;
import com.ej.hgj.entity.user.User;
import com.ej.hgj.enums.*;
import com.ej.hgj.model.*;
import com.ej.hgj.request.login.PropLoginRequest;
import com.ej.hgj.result.dto.PoSpePostDto;
import com.ej.hgj.result.login.PropLoginResult;
import com.ej.hgj.utils.LoggerUtil;
import com.ej.hgj.utils.QyApiUtils;
import com.ej.hgj.utils.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService{
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDaoMapper userDaoMapper;

    @Autowired
    private ConstantConfDaoMapper constantConfDaoMapper;

    @Override
    public String login(String code, String suiteId) {
        logger.info("企业微信登录, code:", code, "， 企业ID:", suiteId);
        ConstantConfig constantConfig = constantConfDaoMapper.getByKey(Constant.WE_COM_APP);
        String token = QyApiUtils.getToken(suiteId, constantConfig.getAppSecret());
        JSONObject jsonObject = QyApiUtils.jsCode2_Session(token, code);
        if (jsonObject == null) {
            throw new BusinessException(
                    MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
                    JiamsvBasicRespCode.INFO_IS_NOT_EXIST.getRespCode(),
                    "企业微信登录失败");
        }
        String userId = jsonObject.getString("userid");
        User user = userDaoMapper.getById(userId);
        if(user == null){
            throw new BusinessException(
                    MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
                    JiamsvBasicRespCode.INFO_IS_NOT_EXIST.getRespCode(),
                    "查询用户信息失败");
        }
        return user.getStaffId();
    }


    @Override
    public String serviceLogin(String code, String suiteId) {
        logger.info("企业微信服务商登录, code:", code, "， 应用ID:", suiteId);
        ConstantConfig suiteTicketConfig = constantConfDaoMapper.getByKey(Constant.SUITE_TICKET);
        ConstantConfig miniProgramAppEj = constantConfDaoMapper.getByKey(Constant.MINI_PROGRAM_APP_EJ);
        // 获取第三方token
        JSONObject jsonObjectToken = QyApiUtils.getSuiteAccessToken(suiteId, miniProgramAppEj.getAppSecret(), suiteTicketConfig.getConfigValue());
        String token = jsonObjectToken.getString("suite_access_token");
        // 获取用户登录身份
        JSONObject jsonObject = QyApiUtils.getUserInfo3rd(token, code);
        if (jsonObject == null) {
            throw new BusinessException(
                    MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
                    JiamsvBasicRespCode.INFO_IS_NOT_EXIST.getRespCode(),
                    "企业微信登录失败");
        }
        String userId = jsonObject.getString("userid");
        User user = userDaoMapper.getById(userId);
        if(user == null){
            throw new BusinessException(
                    MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
                    JiamsvBasicRespCode.INFO_IS_NOT_EXIST.getRespCode(),
                    "查询用户信息失败");
        }
        return user.getStaffId();
    }


//    @Override
//    public LoginInfo login(String code, String suiteId, String ip) {
//        logger.info("企业微信登录, code:", code, "， 应用ID:", suiteId);
//        SuiteConfInfoDo suiteConfInfoDo = suiteConfInfoMapper.selectByPrimaryKey(suiteId);
//        if (suiteConfInfoDo == null) {
//            logger.info("根据应用ID查询应用配置无数据，应用ID:", suiteId);
//            throw new BusinessException(
//                    MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
//                    JiamsvBasicRespCode.INFO_IS_NOT_EXIST.getRespCode(),
//                    "应用配置信息不存在");
//        }
//
//        String token = QyApiUtils.getToken(suiteId,"SystB7aGmQE6_tdFSgHejiSGhyfeY0u42ATpKcwLgfU");
//        JSONObject jsonObject = QyApiUtils.jsCode2_Session(token, code);
//        if (jsonObject == null) {
//            logger.info("调用临时登录凭证校验接口返回null", code);
//            throw new BusinessException(
//                    MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
//                    JiamsvBasicRespCode.INFO_IS_NOT_EXIST.getRespCode(),
//                    "网络异常，请稍后重试[jsCode2_Session]");
//        }
//        PropLoginRequest propLoginRequest = new PropLoginRequest();
//        propLoginRequest.setAuthCorpId(jsonObject.getString("corpid"));
//        propLoginRequest.setUserId(jsonObject.getString("userid"));
//        propLoginRequest.setLoginType(LoginTypeEnum.EW.getLoginType());
//        propLoginRequest.setIp(ip);
//        PropLoginResult loginResult = this.propLogin(propLoginRequest);
//        if (loginResult == null) {
//            throw new BusinessException(
//                    MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
//                    JiamsvBasicRespCode.RESULT_FAILED.getRespCode(),
//                    "网络异常，请稍后重试[ws]");
//        } else if (!StringUtils.equals(MonsterBasicRespCode.SUCCESS.getReturnCode(), loginResult.getRespCode())) {
//            throw new BusinessException(
//                    MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
//                    loginResult.getErrCode(),
//                    loginResult.getErrDesc());
//        }
//        LoginInfo loginInfo = new LoginInfo();
//        BeanUtils.copyProperties(loginResult, loginInfo);
//        if(!loginResult.getPoSpePostDtoList().isEmpty()) {
//            List<PoSpePostVo> poSpePostVoList = new ArrayList<PoSpePostVo>();
//            for(PoSpePostDto dto : loginResult.getPoSpePostDtoList()) {
//                PoSpePostVo vo = new PoSpePostVo();
//                vo.setPostId(dto.getPostId());
//                vo.setPostName(SpePostEnum.getMatched(dto.getPostId()).getDesc());
//                poSpePostVoList.add(vo);
//            }
//            loginInfo.setPoSpePostVoList(poSpePostVoList);
//        }
//
//        loginInfo.setUserId(propLoginRequest.getUserId());
//        loginInfo.setHgjWyModuleInfos(HgjWyModuleEnum.transferJsonStr());
//        return loginInfo;
//    }
//
//    // 物管人员登录
//    public PropLoginResult propLogin(PropLoginRequest propLoginRequest) {
//        String logCode = LoggerUtil.getRandomCode();
//        PropLoginResult propLoginResult = new PropLoginResult();
//        try {
//            checkParams(logCode, propLoginRequest);
//            checkUsrEwInfo(logCode, propLoginRequest);
//            PoBindListDo poBindListDo = getPoBindListDO(propLoginRequest, logCode);
//            UsrInfoDO usrInfoDO = getUsrInfoDO(logCode, poBindListDo.getCustId());
//            propLoginResult.setUsrName(usrInfoDO.getUsrName());
//            propLoginResult.setShortName(usrInfoDO.getShortName());
//            PropOperInfoDo propOperInfoDo = getPropOperInfoDO(logCode, poBindListDo);
//            BeanUtils.copyProperties(propOperInfoDo, propLoginResult);
//            UsrConfDO usrConfDO = getUsrConf(propOperInfoDo.getCustId());
//            propLoginResult.setPropType(usrConfDO.getPropType());
//            propLoginResult.setPoName(propOperInfoDo.getPoName());
//            propLoginResult.setRepairTimeCnt(usrConfDO.getRepairTimeCnt());
//            propLoginResult.setModuleBitmap(usrConfDO.getModuleBitmap());
//            propLoginResult.setRepairAssign(usrConfDO.getRepairAssign());
//            //查询特殊岗位列表
//            propLoginResult.setPoSpePostDtoList(getPoSpePost(poBindListDo.getCustId(), propOperInfoDo.getPoSeqId()));
//            propLoginResult.setRespCode(MonsterBasicRespCode.SUCCESS.getReturnCode());
//            propLoginResult.setErrCode(JiabsvBasicRespCode.SUCCESS.getRespCode());
//            propLoginResult.setErrDesc(JiabsvBasicRespCode.SUCCESS.getRespDesc());
//        } catch (BusinessException ex) {
//            logger.info(ex.getMessage(), "物管人员登录请求发生业务异常");
//            propLoginResult.setRespCode(ex.getRespCode());
//            propLoginResult.setErrCode(ex.getErrCode());
//            propLoginResult.setErrDesc(ex.getErrDesc());
//        } catch (Exception ex) {
//            logger.info(ex.getMessage()+"物管人员登录请求发生系统异常");
//            propLoginResult.setRespCode(MonsterBasicRespCode.SYSTEM_ERROR.getReturnCode());
//            propLoginResult.setErrCode(JiabsvBasicRespCode.SYSTEM_EXCEPTION.getRespCode());
//            propLoginResult.setErrDesc(JiabsvBasicRespCode.SYSTEM_EXCEPTION.getRespDesc());
//        }
//        return propLoginResult;
//    }
//
//    private void checkParams(String logCode, PropLoginRequest propLoginRequest) {
//        if (StringUtils.isBlank(propLoginRequest.getAuthCorpId())
//                || StringUtils.isBlank(propLoginRequest.getUserId())
//                || StringUtils.isBlank(propLoginRequest.getLoginType())
//                || StringUtils.isBlank(propLoginRequest.getIp())) {
//            logger.info(logCode, "物管人员登录请求含有空数据");
//            throw new BusinessException(MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
//                    JiabsvBasicRespCode.DATA_NULL.getRespCode(),
//                    JiabsvBasicRespCode.DATA_NULL.getRespDesc());
//        }
//    }
//
//    private void checkUsrEwInfo(String logCode, PropLoginRequest propLoginRequest) {
//        logger.info(logCode, "物业人员登录校验物业企业微信表信息");
//        UsrEwInfoDo usrEwInfoDo = usrEwInfoMapper.selectByPrimaryKey(propLoginRequest.getAuthCorpId());
//        if(null == usrEwInfoDo) {
//            logger.info("物业人员登录校验物业企业微信表信息不存在");
//            throw new BusinessException(MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
//                    JiabsvBasicRespCode.EW_INFO_NOT_EXIST.getRespCode(),
//                    JiabsvBasicRespCode.EW_INFO_NOT_EXIST.getRespDesc());
//        }
//
//    }
//
//    private PoBindListDo getPoBindListDO(PropLoginRequest propLoginRequest, String logCode) {
//        PoBindListDo poBindListDo = poBindListMapper.selectByPrimaryKey(propLoginRequest.getAuthCorpId(), propLoginRequest.getUserId());
//        if (poBindListDo == null) {
//            logger.info(logCode, "查询物管人员绑定表不存在");
//            throw new BusinessException(MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
//                    JiabsvBasicRespCode.BIND_LIST_NOT_EXIST.getRespCode(),
//                    "该用户账户信息不存在！");
//        } else if (!StringUtils.equals(StatEnum.N.getCode(), poBindListDo.getStat())) {
//            logger.info(logCode, "物管人员绑定状态非正常");
//            throw new BusinessException(MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
//                    JiabsvBasicRespCode.BIND_LIST_STAT_ISCLOSED.getRespCode(),
//                    "该用户账户信息已注销！");
//        }
//        logger.info(logCode, "物管人员绑定数据");
//        return poBindListDo;
//    }
//
//    private UsrInfoDO getUsrInfoDO(String logCode, String custId) {
//        UsrInfoDO usrInfoDO = usrInfoMapper.selectByPrimaryKey(custId);
//        if (usrInfoDO == null) {
//            logger.info(logCode, "物业企业信息不存在, 客户号：", custId);
//            throw new BusinessException(MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
//                    JiabsvBasicRespCode.USR_INFO_NOT_EXIST.getRespCode(),
//                    JiabsvBasicRespCode.USR_INFO_NOT_EXIST.getRespDesc());
//        } else if (!StringUtils.equals(StatEnum.N.getCode(), usrInfoDO.getUsrStat())) {
//            logger.info(logCode, "物业企业状态非正常");
//            throw new BusinessException(MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
//                    JiabsvBasicRespCode.PROP_OPER_INFO_STAT_ISCLOSED.getRespCode(),
//                    JiabsvBasicRespCode.PROP_OPER_INFO_STAT_ISCLOSED.getRespDesc());
//        }
//        return usrInfoDO;
//    }
//
//    // 查询通讯录
//    // todo 修改为新的通讯录表
//    private PropOperInfoDo getPropOperInfoDO(String logCode, PoBindListDo poBindListDo) {
//        PropOperInfoDo propOperInfoDo = propOperInfoMapper.selectByPrimaryKey(poBindListDo.getCustId(), poBindListDo.getPoSeqId());
//        if (propOperInfoDo == null) {
//            logger.info(logCode, "物管人员信息不存在, 客户号：", poBindListDo.getCustId(), ", 物管人员序号：", poBindListDo.getPoSeqId());
//            throw new BusinessException(MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
//                    JiabsvBasicRespCode.PROP_OPER_INFO_NOT_EXIST.getRespCode(),
//                    JiabsvBasicRespCode.PROP_OPER_INFO_NOT_EXIST.getRespDesc());
//        } else if (!StringUtils.equals(StatEnum.N.getCode(), propOperInfoDo.getStat())) {
//            logger.info(logCode, "物管人员信息状态非正常");
//            throw new BusinessException(MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
//                    JiabsvBasicRespCode.PROP_OPER_INFO_STAT_ISCLOSED.getRespCode(),
//                    JiabsvBasicRespCode.PROP_OPER_INFO_STAT_ISCLOSED.getRespDesc());
//        }
//        return propOperInfoDo;
//    }
//
//    private UsrConfDO getUsrConf(String custId){
//        UsrConfDO usrConfDO = usrConfMapper.selectByPrimaryKey(custId);
//        if (usrConfDO == null) {
//            logger.info("未配置该企业信息,请联系管理员添加企业信息",custId);
//            throw new BusinessException(MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
//                    JiabsvBasicRespCode.USR_CONF_NOT_EXIST.getRespCode(),
//                    JiabsvBasicRespCode.USR_CONF_NOT_EXIST.getRespDesc());
//        }
//        return usrConfDO;
//    }
//
//    private List<PoSpePostDto> getPoSpePost(String custId, String poSeqId) {
//        PoSpePostDo queryDo = new PoSpePostDo();
//        queryDo.setCustId(custId);
//        queryDo.setPoSeqId(poSeqId);
//        queryDo.setStat(NormalStatEnum.N.getCode());
//        logger.info("查询特殊岗位信息请求参数封装后为");
//        List<PoSpePostDo> poSpePostDos = poSpePostMapper.queryPoSpePostByCondition(queryDo);
//        if(!poSpePostDos.isEmpty()) {
//            List<PoSpePostDto> poSpePostDtos = new ArrayList<PoSpePostDto>();
//            for(PoSpePostDo poSpePostDo : poSpePostDos) {
//                PoSpePostDto poSpePostDto = new PoSpePostDto();
//                BeanUtils.copyProperties(poSpePostDo, poSpePostDto);
//                poSpePostDtos.add(poSpePostDto);
//            }
//            return poSpePostDtos;
//        }
//        return null;
//    }



}
