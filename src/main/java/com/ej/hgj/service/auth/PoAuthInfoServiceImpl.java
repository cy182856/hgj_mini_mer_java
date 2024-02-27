package com.ej.hgj.service.auth;

import com.ej.hgj.common.CommonBusiness;
import com.ej.hgj.dao.auth.PoAuthInfoMapper;
import com.ej.hgj.dao.user.UsrInfoMapper;
import com.ej.hgj.entity.auth.PoAuthInfoVo;
import com.ej.hgj.enums.*;
import com.ej.hgj.model.UsrInfoDO;
import com.ej.hgj.model.auth.PoAuthInfoExtDo;
import com.ej.hgj.request.auth.QueryPoAuthInfoRequest;
import com.ej.hgj.result.auth.QueryPoAuthInfoResult;
import com.ej.hgj.result.dto.PoAuthInfoDto;
import com.ej.hgj.utils.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PoAuthInfoServiceImpl extends CommonBusiness implements PoAuthInfoService{

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UsrInfoMapper usrInfoMapper;

    @Autowired
    private PoAuthInfoMapper poAuthInfoMapper;

    @Override
    public List<PoAuthInfoVo> getPoLoginMenu(String custId, String poSeqId) {
        QueryPoAuthInfoRequest queryPoAuthInfoRequest = new QueryPoAuthInfoRequest();
        queryPoAuthInfoRequest.setCustId(custId);
        queryPoAuthInfoRequest.setPoSeqId(poSeqId);
        queryPoAuthInfoRequest.setStat(NormalStatEnum.N.getCode());
        //获取菜单权限
        List<PoAuthInfoDto> poAuthInfoDoExts = getPoRealAuth(custId, poSeqId, null);
        //构建最终的菜单层级
        List<PoAuthInfoVo> authList = new ArrayList<PoAuthInfoVo>();
        for(PoAuthInfoDto operAuth : poAuthInfoDoExts){
            if(StringUtils.equals(operAuth.getAuthLevel(), "1")){
                PoAuthInfoVo vo = new PoAuthInfoVo();
                convertDoToVo(operAuth, vo);
                authList.add(vo);
            }

            if(StringUtils.equals(operAuth.getAuthLevel(), "2")){
                PoAuthInfoVo vo = new PoAuthInfoVo();
                convertDoToVo(operAuth, vo);

                for(PoAuthInfoVo oneMenu : authList){
                    if(StringUtils.equals(oneMenu.getAuthId(), operAuth.getParentId())){
                        List<PoAuthInfoVo> twoMenuList = null == oneMenu.getPoAuthInfoVoList() ? new ArrayList<PoAuthInfoVo>() : oneMenu.getPoAuthInfoVoList();
                        twoMenuList.add(vo);
                        oneMenu.setPoAuthInfoVoList(twoMenuList);
                        break;
                    }
                }
            }

            if(StringUtils.equals(operAuth.getAuthLevel(), "3")){
                PoAuthInfoVo vo = new PoAuthInfoVo();
                convertDoToVo(operAuth, vo);

                for(PoAuthInfoVo oneMenu : authList){
                    if(StringUtils.equals(oneMenu.getAuthId().substring(0, 1), operAuth.getAuthId().substring(0, 1))
                            && !oneMenu.getPoAuthInfoVoList().isEmpty())
                    {
                        for(PoAuthInfoVo twoMenu : oneMenu.getPoAuthInfoVoList()){
                            if(StringUtils.equals(twoMenu.getAuthId(), operAuth.getParentId())){
                                List<PoAuthInfoVo> threeMenuList = null == twoMenu.getPoAuthInfoVoList() ? new ArrayList<PoAuthInfoVo>() : twoMenu.getPoAuthInfoVoList();
                                threeMenuList.add(vo);
                                twoMenu.setPoAuthInfoVoList(threeMenuList);
                                break;
                            }
                        }
                    }
                }
            }
        }
        logger.info("获取操作员菜单权限结束：", authList.toString());
        return authList;
    }

    /**
     * 非管理员返回：管理员权限+物管权限的交集
     * 是管理员返回：管理员权限
     * @param custId
     * @param poSeqId
     * @param isConDisp
     * @return
     */
    private List<PoAuthInfoDto> getPoRealAuth(String custId, String poSeqId, String isConDisp) {
        UsrInfoDO usrInfoDo = usrInfoMapper.selectByPrimaryKey(custId);
        if(usrInfoDo == null || StringUtils.isBlank(usrInfoDo.getPoSeqId())){
            throw new BusinessException(MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
                    JiamsvBasicRespCode.RESULT_FAILED.getRespCode(),
                    "请联系慧管家设置企业信息！");
        }
        //获取菜单权限：操作员权限+物管权限的交集
        List<PoAuthInfoDto> poAuthInfoDoExts = new ArrayList<PoAuthInfoDto>();
        //物管菜单权限
        List<PoAuthInfoDto> poAuthInfoDtos = doQueryPoAuthInfo(custId, poSeqId, isConDisp);
        if(isNotMatched(poSeqId, usrInfoDo.getPoSeqId())){
            //管理员菜单权限
            List<PoAuthInfoDto> poAuthInfoDto_admins = doQueryPoAuthInfo(custId, usrInfoDo.getPoSeqId(), isConDisp);
            Map<String, PoAuthInfoDto> poAuthInfoDtoMap = new HashMap<String, PoAuthInfoDto>();
            for(PoAuthInfoDto poAuthInfoDto_admin : poAuthInfoDto_admins){
                poAuthInfoDtoMap.put(poAuthInfoDto_admin.getAuthId(), poAuthInfoDto_admin);
            }
            for(PoAuthInfoDto poAuthInfoDto : poAuthInfoDtos){
                if(poAuthInfoDtoMap.containsKey(poAuthInfoDto.getAuthId())){
                    //物管+操作员拥有该权限
                    PoAuthInfoDto poAuthInfoDto_admin = poAuthInfoDtoMap.get(poAuthInfoDto.getAuthId());
                    if(isMatched(poAuthInfoDto_admin.getIsAdd(), CommonYesOrNoEnum.N.getCode())){
                        poAuthInfoDto.setIsAdd(CommonYesOrNoEnum.N.getCode());
                    }
                    if(isMatched(poAuthInfoDto_admin.getIsUpd(), CommonYesOrNoEnum.N.getCode())){
                        poAuthInfoDto.setIsUpd(CommonYesOrNoEnum.N.getCode());
                    }
                    poAuthInfoDoExts.add(poAuthInfoDto);
                }
            }
        }else {
            poAuthInfoDoExts = poAuthInfoDtos;
        }
        return poAuthInfoDoExts;
    }

    private void convertDoToVo(PoAuthInfoDto poAuthInfoDto, PoAuthInfoVo poAuthInfoVo) {
        poAuthInfoVo.setAuthId(poAuthInfoDto.getAuthId());
        poAuthInfoVo.setAuthId(poAuthInfoDto.getAuthId());
        poAuthInfoVo.setAuthLevel(poAuthInfoDto.getAuthLevel());
        poAuthInfoVo.setAuthDesc(poAuthInfoDto.getAuthDesc());
        poAuthInfoVo.setParentId(poAuthInfoDto.getParentId());
        poAuthInfoVo.setAuthType(poAuthInfoDto.getAuthType());
        poAuthInfoVo.setIsAdd(poAuthInfoDto.getIsAdd());
        poAuthInfoVo.setIsUpd(poAuthInfoDto.getIsUpd());
        poAuthInfoVo.setHaveSon(poAuthInfoDto.getHaveSon());
        poAuthInfoVo.setOrderNo(poAuthInfoDto.getOrderNo());
        poAuthInfoVo.setEnbFlag(poAuthInfoDto.getEnbFlag());
        poAuthInfoVo.setAction(poAuthInfoDto.getAction());
    }

    private List<PoAuthInfoDto> doQueryPoAuthInfo(String custId, String poSeqId, String isConDisp) {
        QueryPoAuthInfoRequest queryPoAuthInfoRequest = new QueryPoAuthInfoRequest();
        queryPoAuthInfoRequest.setCustId(custId);
        queryPoAuthInfoRequest.setPoSeqId(poSeqId);
        queryPoAuthInfoRequest.setStat(NormalStatEnum.N.getCode());
        queryPoAuthInfoRequest.setIsConDisp(isConDisp);
        QueryPoAuthInfoResult result = this.doQueryPoAuthInfo(queryPoAuthInfoRequest );
        if(result==null){
            throw new BusinessException(MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
                    JiamsvBasicRespCode.RESULT_FAILED.getRespCode(),
                    "网络异常，请稍后再试！");
        }
        if(isNotMatched(MonsterBasicRespCode.SUCCESS.getReturnCode(), result.getRespCode())){
            throw new BusinessException(result.getRespCode(), result.getErrCode(), result.getErrDesc());
        }
        return result.getPoAuthInfoDto();
    }

    public QueryPoAuthInfoResult doQueryPoAuthInfo(QueryPoAuthInfoRequest queryPoAuthInfoRequest) {
        logger.info("物管人员权限查询请求");
        QueryPoAuthInfoResult queryPoAuthInfoResult = new QueryPoAuthInfoResult();
        try {
            if(StringUtils.isBlank(queryPoAuthInfoRequest.getCustId())
                    || StringUtils.isBlank(queryPoAuthInfoRequest.getPoSeqId())){
                logger.info("物管人员权限查询请求含有空数据");
                throw new BusinessException(MonsterBasicRespCode.RESULT_FAILED.getReturnCode(),
                        JiabsvBasicRespCode.DATA_NULL.getRespCode(),
                        JiabsvBasicRespCode.DATA_NULL.getRespDesc());
            }
            PoAuthInfoExtDo poAuthInfoExtDo = new PoAuthInfoExtDo();
            poAuthInfoExtDo.setCustId(queryPoAuthInfoRequest.getCustId());
            poAuthInfoExtDo.setAuthId(queryPoAuthInfoRequest.getAuthId());
            poAuthInfoExtDo.setPoSeqId(queryPoAuthInfoRequest.getPoSeqId());
            poAuthInfoExtDo.setStat(queryPoAuthInfoRequest.getStat());
            poAuthInfoExtDo.setIsAdd(queryPoAuthInfoRequest.getIsAdd());
            poAuthInfoExtDo.setIsUpd(queryPoAuthInfoRequest.getIsUpd());
            poAuthInfoExtDo.setIsConDisp(queryPoAuthInfoRequest.getIsConDisp());
            poAuthInfoExtDo.setEnbFlag(NormalStatEnum.N.getCode());
            List<PoAuthInfoExtDo> poAuthInfoExtDos = poAuthInfoMapper.selectPoAuthInfo(poAuthInfoExtDo);

            queryPoAuthInfoResult.setPoAuthInfoDto(convertDoToDto(poAuthInfoExtDos));
            queryPoAuthInfoResult.setRespCode(MonsterBasicRespCode.SUCCESS.getReturnCode());
            queryPoAuthInfoResult.setErrCode(JiabsvBasicRespCode.SUCCESS.getRespCode());
            queryPoAuthInfoResult.setErrDesc(JiabsvBasicRespCode.SUCCESS.getRespDesc());
        } catch (BusinessException ex) {
            logger.info(ex.getMessage(), "物管人员权限查询请求发生业务异常");
            queryPoAuthInfoResult.setRespCode(ex.getRespCode());
            queryPoAuthInfoResult.setErrCode(ex.getErrCode());
            queryPoAuthInfoResult.setErrDesc(ex.getErrDesc());
        } catch (Exception ex) {
            logger.info(ex.getMessage(), "物管人员权限查询请求发生系统异常");
            queryPoAuthInfoResult.setRespCode(MonsterBasicRespCode.SYSTEM_ERROR.getReturnCode());
            queryPoAuthInfoResult.setErrCode(JiabsvBasicRespCode.SYSTEM_EXCEPTION.getRespCode());
            queryPoAuthInfoResult.setErrDesc(JiabsvBasicRespCode.SYSTEM_EXCEPTION.getRespDesc());
        }
        return queryPoAuthInfoResult;
    }

    private List<PoAuthInfoDto> convertDoToDto(List<PoAuthInfoExtDo> poAuthInfoExtDos) {
        List<PoAuthInfoDto> poAuthInfoDtos = new ArrayList<PoAuthInfoDto>();
        for(PoAuthInfoExtDo poAuthInfoExtDo : poAuthInfoExtDos){
            PoAuthInfoDto poAuthInfoDto = new PoAuthInfoDto();
            BeanUtils.copyProperties(poAuthInfoExtDo, poAuthInfoDto);
            poAuthInfoDtos.add(poAuthInfoDto);
        }
        return poAuthInfoDtos;
    }
}
