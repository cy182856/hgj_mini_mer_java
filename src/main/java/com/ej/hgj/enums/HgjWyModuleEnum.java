package com.ej.hgj.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

public enum HgjWyModuleEnum {
    APPT_ORD_DETAIL("01", "011", "预约订单详情", "/subpages/appt/appt_ordDtl_query"),
    REPAIR_DETAIL("08", "081", "报修详情", "/subpages/repair/pages/repairDetail/repairDetail"),
    REPAIR_DETAIL_NM("08", "082", "匿名报修详情", "/subpages/repair/pages/niming/repairDetail"),
    HEO_DETAIL("03", "031", "邻里圈详情", "/subpages/heo/heodetail/heodetail"),
    ADVICE_DETAIL("13", "131", "反馈详情", "/subpages/advice/adviceDetail/myDetail");

    private String busiId;
    private String modelId;
    private String modelDesc;
    private String pagePath;

    private HgjWyModuleEnum(String busiId, String modelId, String modelDesc, String pagePath) {
        this.busiId = busiId;
        this.modelId = modelId;
        this.modelDesc = modelDesc;
        this.pagePath = pagePath;
    }

    public String getModelId() {
        return this.modelId;
    }

    public String getBusiId() {
        return this.busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelDesc() {
        return this.modelDesc;
    }

    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
    }

    public String getPagePath() {
        return this.pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public static HgjWyModuleEnum getMatched(String modelId) {
        HgjWyModuleEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            HgjWyModuleEnum hgjWyModuleEnum = var1[var3];
            if (StringUtils.equals(modelId, hgjWyModuleEnum.getModelId())) {
                return hgjWyModuleEnum;
            }
        }

        return null;
    }

    public static String transferJsonStr() {
        JSONArray jsonArray = new JSONArray();
        HgjWyModuleEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            HgjWyModuleEnum hgjWyModuleEnum = var1[var3];
            JSONObject jsonData = new JSONObject();
            jsonData.put("modelId", hgjWyModuleEnum.getModelId());
            jsonData.put("pagePath", hgjWyModuleEnum.getPagePath());
            jsonArray.add(jsonData);
        }

        return jsonArray.toJSONString();
    }

}
