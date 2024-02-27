package com.ej.hgj.vo.visit;

import lombok.Data;

import java.io.Serializable;
@Data
public class CheckCodeVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5115223846465951522L;

	private String codeType;
	
	private String visitType;
	
	private String checkType;
	
	private String checkCodeParam;

	private String poSeqId;
	
	private String custId;
	
}
