package com.ej.hgj.entity.login;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author  xia
 * @version $Id: LoginInfo.java, v 0.1 2020年3月23日 下午1:03:08 xia Exp $
 */
@Data
public class LoginInfo implements Serializable {

    /**  */
    private static final long serialVersionUID = 2384876843255931214L;

    private String respCode;

    private String errCode;

    private String errDesc;

    private String sessionId;
    
    //小区全称
    private String usrName;

    //小区简称称
    private String shortName;
    
    // 用户企业微信标识
    private String userId;

    // 企业客户号
    private String custId;

    // 物管人员序列号
    private String poSeqId;

    // 物管人员名称
    private String poName;
    
    //物管人员部门名称
    private String deptName;

    // 物管微信号
    private String poWxId;

    // 物管手机号
    private String poMp;
    
    //物管人员评价总分数
    private Integer rateScore;
    
    //物管人员评价总次数
    private Integer rateCnt;

    private String isInfoPub;

    private String hgjOpenId;

    private String openDate;

    // 个人中心 页面底部描述
    private String desc1;

    private String desc2;

    private String desc3;
    
    private String propType;//USR_CONF - PROP_TYPE:物业类型 ‘R’ — 住宅（residence）‘B’ — 商业（business）
    
    private List<PoSpePostVo> poSpePostVoList;
    
    private String moduleBitmap;
    
    private String repairAssign;
    
    private String hgjWyModuleInfos;

    private String token;

}
