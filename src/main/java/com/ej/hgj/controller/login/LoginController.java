package com.ej.hgj.controller.login;

import com.ej.hgj.controller.base.BaseController;
import com.ej.hgj.dao.user.UserDaoMapper;
import com.ej.hgj.entity.user.User;
import com.ej.hgj.enums.JiamsvBasicRespCode;
import com.ej.hgj.enums.MonsterBasicRespCode;
import com.ej.hgj.result.login.LoginResult;
import com.ej.hgj.service.login.LoginService;
import com.ej.hgj.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 *
 * @author  xia
 * @version $Id: LoginController.java, v 0.1 2020年8月11日 下午2:18:45 xia Exp $
 */
@Controller
public class LoginController extends BaseController implements Serializable {

    /**  */
    private static final long serialVersionUID = -1008424102185198682L;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserDaoMapper userDaoMapper;

    /**
     * 企业微信登录
     * @param httpRequest
     * @return
     */
//    @ResponseBody
//    @RequestMapping("/login/wxlogin")
//    public LoginResult wxLogin(HttpServletRequest httpRequest) {
//        String code = getParam(httpRequest, "code");
//        String suiteId = getParam(httpRequest, "suite_id");
//        String staffId = loginService.login(code, suiteId);
//        User user = userDaoMapper.getByStaffId(staffId);
//        LoginResult loginResult = new LoginResult();
//        loginResult.setRespCode(MonsterBasicRespCode.SUCCESS.getReturnCode());
//        loginResult.setErrCode(JiamsvBasicRespCode.SUCCESS.getRespCode());
//        loginResult.setErrDesc(JiamsvBasicRespCode.SUCCESS.getRespDesc());
//        String token = TokenUtils.getToken(staffId, staffId);
//        loginResult.setToken(token);
//        loginResult.setUserName(user.getUserName());
//        return loginResult;
//    }


    /**
     * 企业微信服务商登录
     * @param httpRequest
     * @return
     */
    @ResponseBody
    @RequestMapping("/login/wxlogin")
    public LoginResult wxLogin(HttpServletRequest httpRequest) {
        String code = getParam(httpRequest, "code");
        String suiteId = getParam(httpRequest, "suite_id");
        String staffId = loginService.serviceLogin(code, suiteId);
        User user = userDaoMapper.getByStaffId(staffId);
        LoginResult loginResult = new LoginResult();
        loginResult.setRespCode(MonsterBasicRespCode.SUCCESS.getReturnCode());
        loginResult.setErrCode(JiamsvBasicRespCode.SUCCESS.getRespCode());
        loginResult.setErrDesc(JiamsvBasicRespCode.SUCCESS.getRespDesc());
        String token = TokenUtils.getToken(staffId, staffId);
        loginResult.setToken(token);
        loginResult.setUserName(user.getUserName());
        return loginResult;
    }

}
