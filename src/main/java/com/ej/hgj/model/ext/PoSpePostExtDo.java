package com.ej.hgj.model.ext;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * @author lx
 * @version $Id: PoSpePostExtDo.java, v 0.1 2020-12-23 下午5:39:26 lx Exp $
 */
public class PoSpePostExtDo {
	private String custId;//企业客户号。
	private String poSeqId;//物管人员序号。每个企业从0000001开始
	private String postId;//岗位代号。‘01’ -- 维修员,‘02’ -- 维修分配员,‘03’ -- 邻里圈审核员,‘04’ -- 门禁安保,‘05’ -- 住户入住审核员,‘06’ -- 客服人员,‘07’ -- 财务记账员
	private String stat;//状态	。‘N’ – 正常,‘C’ – 被关闭
	private String poName;//物管人员名称。企业自己定义
	private String deptName;//部门名称。企业自己定义
	private String poiStat;//状态。‘N’ – 正常,‘C’ – 被关闭
	private String poWxId;//物管微信号。企微Plugid，可以选择是否公开给业主
	private String poMp;//物管手机号。可以选择是否公开给业主
	private String isInfoPub;//是否可信息公开。物管员联系方式是否可以公开,‘Y’ – 是，可公开；‘N’ – 否，不能公开
	private String hgjOpenId;//慧管家。慧管家物管小程序绑定的微信openId
	private Integer rateScore;//评价总分数。
	private Integer rateCnt;//评价总次数。
	private String openDate;//开户日期。

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

	public String getPoiStat() {
		return poiStat;
	}

	public void setPoiStat(String poiStat) {
		this.poiStat = poiStat;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
