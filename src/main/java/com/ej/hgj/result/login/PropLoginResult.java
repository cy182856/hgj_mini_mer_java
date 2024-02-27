package com.ej.hgj.result.login;

import com.ej.hgj.result.base.BaseResult;
import com.ej.hgj.result.dto.PoSpePostDto;
import lombok.Data;

import java.util.List;

@Data
public class PropLoginResult extends BaseResult {

    /**  */
    private static final long serialVersionUID = -7619287154094072656L;
    
    private String custId;
    
    private String usrName;
    
    private String shortName;
    
    private String poSeqId;
    
    private String poName;
    
    private String deptName;
    
    private String stat;
    
    private String poWxId;
    
    private String poMp;
    
    private String isInfoPub;
    
    private String hgjOpenId;
    
    private Integer rateScore;
    
    private Integer rateCnt;
    
    private String openDate;
    
    private String propType;//USR_CONF - PROP_TYPE:物业类型 ‘R’ — 住宅（residence）‘B’ — 商业（business）
    
    //更改到场次数
    private Integer repairTimeCnt;
    
    private List<PoSpePostDto> poSpePostDtoList;
    
    private String moduleBitmap;
    
    private String repairAssign;

}
