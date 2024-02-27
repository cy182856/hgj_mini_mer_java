package com.ej.hgj.controller.base;

import com.ej.hgj.constant.Constant;
import com.ej.hgj.context.OperationContext;
import com.ej.hgj.context.OperationContextHolder;
import com.ej.hgj.utils.JsonUtils;
import com.ej.hgj.utils.json.MonsterJsonPropertyNamingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tty
 * @version 1.0 2020-08-17 10:46
 */
@Controller
public  class BaseController{

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取httpRequest参数值
     * @param httpRequest
     * @param key
     * @return
     */
    protected String getParam(HttpServletRequest httpRequest, String key) {
        if (StringUtils.isBlank(key)) {
            return StringUtils.EMPTY;
        }
        String value = httpRequest.getParameter(key);
        if (StringUtils.isBlank(value)) {
            return StringUtils.EMPTY;
        }
        return value;
    }

    /**
     *
     * 公用json返回页面
     * @param model
     * @param obj
     * @return
     */
    protected String returnResultAsJson(ModelMap model, Object obj) {
        String jsonStr = convert2JsonAllUppercaseStyle(obj);
        logger.info("返回结果：", jsonStr);
        model.addAttribute(Constant.JSONSTR_KEY, jsonStr);
        return Constant.JSONSTR_RESPONSE_VIEW;
    }
    protected String convert2JsonAllUppercaseStyle(Object obj) {
        try {
            return JsonUtils.writeEntiry2JSON(obj, MonsterJsonPropertyNamingStrategy.ALL_CHARACTER_UPPERCASE_STRATEGY);
        } catch (Exception e) {
            logger.info(e.getMessage(), "转换JSON字符串失败");
            return "";
        }
    }

    protected String getCustId(HttpServletRequest request){
        try {
            OperationContext operationContext = getOperationContext(request);
            return operationContext.getCustId();
        } catch (Exception e) {
            logger.info("获取客户号失败");
            return null;
        }
    }

    protected OperationContext getOperationContext(HttpServletRequest request) {
        OperationContext jiamsvOperationContext = (OperationContext) request.getSession().getAttribute(OperationContextHolder.operationContextSessionKey);
        if (jiamsvOperationContext == null) {
            logger.info("session失效,获取对象失败");
            throw new NullPointerException();
        }
        return jiamsvOperationContext;
    }
    protected String getPoSeqId(HttpServletRequest request){
        try {
            OperationContext operationContext = getOperationContext(request);
            return operationContext.getPoSeqId();
        } catch (Exception e) {
            logger.info("获取物管序列号失败");
            return null;
        }
    }

}
