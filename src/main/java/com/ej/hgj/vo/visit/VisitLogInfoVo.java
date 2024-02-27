package com.ej.hgj.vo.visit;

import com.ej.hgj.entity.visit.VisitLog;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VisitLogInfoVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6866870999094714438L;
	
    private String custId;

    private String huSeqId;

    private String houseSeqId;
    
    private String beginVisitDate;
    
    private String endVisitDate;

    //新增访客出入日志访问类型  I--进场  O --离场
    private String visitType;
    
    private String stat;
    
    private String buildingId;
    
    private String areaId;
    
    private Integer pageSize;
    
    private Integer pageNum;
    
    private String poSeqId;

    private Integer totalNum;//总记录数
    private Integer pages;//总页数
    private List<VisitLog> list;
    private String houseName;
    private String respCode;

}
