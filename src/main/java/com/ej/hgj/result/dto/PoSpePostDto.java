package com.ej.hgj.result.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class PoSpePostDto implements Serializable, Comparable<PoSpePostDto> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1598216062651622688L;
	
	private String custId;
	
    private String poSeqId;
    
    private String postId;
    
    private String stat;
    
    
    private String poName;//物管人员名称
    private String deptName;//部门名称
    private String poWxId;//物管微信号
    private String poMp;//物管手机号
    private String isInfoPub;//
    private String hgjOpenId;//慧管家openId
    private Integer rateScore;//评价总分数
    private Integer rateCnt;//评价总次数
    private String openDate;
    private String aveScore;//平均分
    private String poiStat;//状态。‘N’ – 正常,‘C’ – 被关闭
    private String postIdDesc;//岗位代号描述。
    private String statDesc;//状态描述。
    private String openDateDesc;//开户日期描述。
    private String averageScore;//物管人员平均分。
    
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getPoSeqId() {
		return poSeqId;
	}
	public void setPoSeqId(String poSeqId) {
		this.poSeqId = poSeqId;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
    public String getPoName() {
        return poName;
    }
    public void setPoName(String poName) {
        this.poName = poName;
    }
    public String getDeptName() {
        return deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String getPoWxId() {
        return poWxId;
    }
    public void setPoWxId(String poWxId) {
        this.poWxId = poWxId;
    }
    public String getPoMp() {
        return poMp;
    }
    public void setPoMp(String poMp) {
        this.poMp = poMp;
    }
    public String getIsInfoPub() {
        return isInfoPub;
    }
    public void setIsInfoPub(String isInfoPub) {
        this.isInfoPub = isInfoPub;
    }
    public String getHgjOpenId() {
        return hgjOpenId;
    }
    public void setHgjOpenId(String hgjOpenId) {
        this.hgjOpenId = hgjOpenId;
    }
    public Integer getRateScore() {
        return rateScore;
    }
    public void setRateScore(Integer rateScore) {
        this.rateScore = rateScore;
    }
    public Integer getRateCnt() {
        return rateCnt;
    }
    public void setRateCnt(Integer rateCnt) {
        this.rateCnt = rateCnt;
    }
    public String getOpenDate() {
        return openDate;
    }
    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }
    public String getAveScore() {
        return aveScore;
    }
    public void setAveScore(String aveScore) {
        this.aveScore = aveScore;
    }
    public String getPoiStat() {
        return poiStat;
    }
    public void setPoiStat(String poiStat) {
        this.poiStat = poiStat;
    }
    public String getPostIdDesc() {
        return postIdDesc;
    }
    public void setPostIdDesc(String postIdDesc) {
        this.postIdDesc = postIdDesc;
    }
    public String getStatDesc() {
        return statDesc;
    }
    public void setStatDesc(String statDesc) {
        this.statDesc = statDesc;
    }
    public String getOpenDateDesc() {
        return openDateDesc;
    }
    public void setOpenDateDesc(String openDateDesc) {
        this.openDateDesc = openDateDesc;
    }
    public String getAverageScore() {
        return averageScore;
    }
    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }
    /** 
     * @see Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(PoSpePostDto o) {
        return o.getAveScore().compareTo(this.aveScore);
    }
    /** 
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}
