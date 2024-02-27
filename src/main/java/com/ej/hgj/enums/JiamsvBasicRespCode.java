package com.ej.hgj.enums;

public enum JiamsvBasicRespCode {

    SUCCESS("0000", "成功"),
    RESULT_FAILED("0101", "请求失败"),
    DATA_NULL("0001", "请求参数含有空数据"),
    DATA_LENGTH_ERROR("0002", "请求参数长度不正确"),
    FINAL_DATA_NOT_EXIST("0003", "固定数据没有匹配项"),
    DATA_FOMART_MATCH_ERROR("0004", "数据格式不正确"),
    DATA_IS_NULL("0005", "暂无数据"),
    RESULT_UNCERTAIN("0006", "交易结果不确定"),
    CONVERT_DATA_ERROR("0007", "数据转换异常"),
    UPD_DATA_IS_NULL("0008","更新数据不存在"),
    INFO_IS_NOT_EXIST("0009","信息未找到"),
    NO_PERMISSION("0010","无权限操作"),
    NO_SUPPORTED_BUSI("0011", "不支持的业务"),

    SIGN_FAILED("0996", "签名失败"),
    DB_ERROR("0997","数据库操作异常"),
    GET_TOKEN_FAILED("0998", "获取TOKEN失败"),
    SYSTEM_EXCEPTION("0999", "系统异常"),
    
    EW_INFO_NOT_EXIST("1001","企业微信信息不存在"),
    EW_BIND_INFO_NOT_EXIST("1002","职员企业微信绑定信息不存在"),
    USR_OPER_DATA_ERROR("1003","职员数据异常"),
    GET_DEPT_INFO_ERROR("1004","获取部门信息失败"),
    GET_DEPT_INFO_NULL("1005","获取部门信息为空"),
    
    /**日报错误码 2001 ~2099*/
    DRPT_LOG_IS_EXIST("2001","日报记录项已存在"), 
    
    /** 门禁 3001 - 3099 */
    AC_DEV_LIST_NOT_EXIST("3001","门禁设备信息不存在");

    private String respCode;
    private String respDesc;
    
    JiamsvBasicRespCode(String respCode, String respDesc) {
    	this.respCode = respCode;
    	this.respDesc = respDesc;
    }

	public String getRespCode() {
		return JiaSubsystem.JIAMSV.getWholeSystemNo().concat(respCode);
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
    
}
