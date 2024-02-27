package com.ej.hgj.entity.workord;

import com.ej.hgj.entity.repair.RepairLog;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class WorkOrd {
    private String id;
    // 报修单号
    private String repairNum;
    // 项目号
    private String orgId;
    // 项目名称
    private String orgName;
    // 工作位置ID
    private String woId;
    // 工作位置
    private String workPos;
    // 客户id
    private String cstId;
    // 报事方式
    private String rsWay;
    // 接单人
    private String orders;
    // 前端的报修状态
    private String repairStatus;
    // 客户名称
    private String cstName;
    // 工作位置类型名称,报修类型
    private String quesType;
    // 人工费
    private BigDecimal labourCost;
    // 材料费
    private BigDecimal materialCost;
    // 开工时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ordersTime;
    // 完工时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date completionTime;
    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private List<String> budIdList;

}
