package com.ej.hgj.constant;

public class Constant {

    // 删除标识 0-未删除
    public static Integer DELETE_FLAG_NOT = 0;
    // 删除标识 1-已删除
    public static Integer DELETE_FLAG_YES = 1;

    // 企微平台
    public static Integer PLAT_FORM_WE_COM = 2;
    // 企业ID
    // public static String CORP_ID = "wwaf0bc97996187867";
    // 企业微信秘钥 宜悦服务商
    public static String WE_COM_APP_YY = "we_com_app_yy";

    // 企业微信 东方渔人码头
    public static String WE_COM_APP = "we_com_app";

    // public static String CORP_SECRET = "SystB7aGmQE6_tdFSgHejiSGhyfeY0u42ATpKcwLgfU";

    public static String FAIL_CODE = "999";

    // 返回值code-成功
    public static String SUCCESS = "000";
    // 返回值message-成功
    public static String SUCCESS_RESULT_MESSAGE = "成功";

    public static final String HTTP_POST = "POST";
    public static final String HTTP_GET = "GET";

    //统一返回
    public static final String JSONSTR_RESPONSE_VIEW = "/jsonResponseView";

    /** http接口中json串key值 */
    public static final String JSONSTR_KEY = "jsonStr";

    public static final String RESP_CODE = "RESPCODE";

    public static final String ERR_CODE = "ERRCODE";

    public static final String ERR_DESC = "ERRDESC";

    // 公共报修单号
    public static String P_REPAIR_NUM = "p_repair_num";
    // 客户报修单号
    public static String S_REPAIR_NUM = "s_repair_num";



    // 服务商相关
    /**
     * 服务商CorpID  宜悦
     */
    public static final String CorpID = "ww4c8c3c6639dd7aac";
    /**
     * 服务商身份的调用凭证 宜悦
     */
    public static final String ProviderSecret = "yiyDsOpKwdWX7c-XbQKZqd9LWM-nUa9SQF-ZXu58ROkXY8rrRu6bjMoGuJ4MTpsc";

    /**
     * 应用的调用身份密钥 凡享物管小程序
     */
    //public static final String SuiteSecret = "eeeedfa5daa65e760069248871806ee4";
    public static final String MINI_PROGRAM_APP_EJ = "mini_program_app_ej";


    // 回调相关
    /**
     * 回调/通用开发参数Token, 两者解密算法一样，所以为方便设为一样
     */
    public static final String TOKEN = "rrMqvxjLlf5K6Ugziic7vCE";

    /**
     * 回调/通用开发参数EncodingAESKey, 两者解密算法一样，所以为方便设为一样
     */
    public static final String EncodingAESKey = "C1hpaqLZhZtjhLBZPttzqm6kDFYFX7nqq5aPE5KIX3p";

    /**
     * 第三方应用的suite_ticket
     */
    public static final String SUITE_TICKET = "suite_ticket";

    /**
     * 企业授权码
     */
    public static final String AUTH_CODE = "auth_code";

    /** 获取企业永久授权码 */
    public static final String GET_PERMANENT_CODE = "https://qyapi.weixin.qq.com/cgi-bin/service/get_permanent_code?suite_access_token=SUITE_ACCESS_TOKEN";

    /** 获取第三方应用凭证 */
    public static final String GET_SUITE_TOKEN = " https://qyapi.weixin.qq.com/cgi-bin/service/get_suite_token";


}
