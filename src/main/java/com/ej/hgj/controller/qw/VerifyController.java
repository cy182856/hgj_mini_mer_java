package com.ej.hgj.controller.qw;

import com.alibaba.fastjson.JSONObject;
import com.ej.hgj.constant.Constant;
import com.ej.hgj.controller.base.BaseController;
import com.ej.hgj.dao.config.ConstantConfDaoMapper;
import com.ej.hgj.dao.corp.CorpDaoMapper;
import com.ej.hgj.entity.config.ConstantConfig;
import com.ej.hgj.entity.corp.Corp;
import com.ej.hgj.utils.MyX509TrustManager;
import com.ej.hgj.utils.TimestampGenerator;
import com.ej.hgj.utils.qw.WxUtil;
import com.ej.hgj.utils.qw.aes.WXBizMsgCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/verify")
public class VerifyController extends BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ConstantConfDaoMapper constantConfDaoMapper;

    @Autowired
    private CorpDaoMapper corpDaoMapper;

    /*
     * 验证通用开发参数及应用回调
     */
    @RequestMapping(value = "callback_verify.do" ,method = RequestMethod.GET)
    public void doGetValid(HttpServletRequest request, HttpServletResponse response){
        logger.info("回调URL请求校验：", request.getServletPath());
        // 微信加密签名
        String msg_signature = request.getParameter("msg_signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        // 如果是刷新，需返回原echostr
        String echostr = request.getParameter("echostr");

        String sEchoStr=""; //需要返回的明文
        PrintWriter out;
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(Constant.TOKEN, Constant.EncodingAESKey, Constant.CorpID);
            sEchoStr = wxcpt.VerifyURL(msg_signature, timestamp,
                    nonce, echostr);
            logger.info("verifyurl echostr: " + sEchoStr);

            // 验证URL成功，将sEchoStr返回
            //2.3若校验成功，则原样返回 echoStr
            out = response.getWriter();
            out.print(sEchoStr);
            out.flush();
            out.close();
            logger.info("回调URL请求校验结束");
        } catch (Exception e) {
            //验证URL失败，错误原因请查看异常
            e.printStackTrace();
        }
    }

    /*
     * 刷新 ticket
     */
    @RequestMapping(value = "callback_verify.do" ,method = RequestMethod.POST)
    public void doPostValid(HttpServletRequest request,HttpServletResponse response) throws IOException {
        logger.info("企业微信指令回调请求开始");
        try {
            // 微信加密签名
            String msg_signature = request.getParameter("msg_signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            logger.info("sVerifyMsgSig=",msg_signature);
            logger.info("sVerifyTimeStamp=",timestamp);
            logger.info("sVerifyNonce=",nonce);
            ConstantConfig miniProgramAppEj = constantConfDaoMapper.getByKey(Constant.MINI_PROGRAM_APP_EJ);
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(Constant.TOKEN, Constant.EncodingAESKey, miniProgramAppEj.getAppId());
            String postData="";   // 密文，对应POST请求的数据
            //1.获取加密的请求消息：使用输入流获得加密请求消息postData
            ServletInputStream in = request.getInputStream();
            BufferedReader reader =new BufferedReader(new InputStreamReader(in));
            String tempStr="";   //作为输出字符串的临时串，用于判断是否读取完毕
            while(null!=(tempStr=reader.readLine())){
                postData+=tempStr;
            }
            String suiteXml = wxcpt.DecryptMsg( msg_signature, timestamp, nonce, postData);
            logger.info("企业微信消息解密后suiteXml: " + suiteXml);
            Map suiteMap = WxUtil.parseXml(suiteXml);
            if(suiteMap != null) {
                Thread thread = new Thread(new DataCheckThread(suiteMap));
                thread.start();
            }else{
                logger.info("企业微信回调接口返回消息内容为空");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.print("success");
        out.close();
    }

    class DataCheckThread implements Runnable {
        private  Map suiteMap;
        public DataCheckThread(Map suiteMap) {
            this.suiteMap = suiteMap;
        }
        @Override
        public void run() {
            logger.info("执行线程开始====================");
            ConstantConfig miniProgramAppEj = constantConfDaoMapper.getByKey(Constant.MINI_PROGRAM_APP_EJ);
            ConstantConfig suiteTicket = constantConfDaoMapper.getByKey(Constant.SUITE_TICKET);
            // 请求事件类型
            String infoType = suiteMap.get("InfoType").toString();
            // 请求事件类型-推送suite_ticket
            if(infoType.equals("suite_ticket")){
                ConstantConfig config = new ConstantConfig();
                config.setConfigKey(Constant.SUITE_TICKET);
                config.setConfigValue(suiteMap.get("SuiteTicket") + "");
                constantConfDaoMapper.update(config);
            }
            // 请求事件类型-授权成功通知
            if(infoType.equals("create_auth")){
                String authCode = suiteMap.get("AuthCode").toString();
                // 获取应用token
                JSONObject jsonObjectToken = getSuiteAccessToken(miniProgramAppEj.getAppId(),miniProgramAppEj.getAppSecret(), suiteTicket.getConfigValue());
                // 根据临时授权码获取企业永久授权码
                JSONObject jsonObjectCode = getPermanentCode(authCode,jsonObjectToken.get("suite_access_token").toString());
                String permanentCode = jsonObjectCode.get("permanent_code").toString();
                JSONObject jsonCorpInfo = JSONObject.parseObject(jsonObjectCode.get("auth_corp_info").toString());
                String corpId  = jsonCorpInfo.get("corpid").toString();
                String corpName  = jsonCorpInfo.get("corp_name").toString();
                Corp corp = corpDaoMapper.getByCorpId(corpId);
                if(corp == null){
                    Corp c = new Corp();
                    c.setId(TimestampGenerator.generateSerialNumber());
                    c.setCorpId(corpId);
                    c.setCorpName(corpName);
                    c.setPermanentCode(permanentCode);
                    c.setCreateTime(new Date());
                    c.setUpdateTime(new Date());
                    c.setDeleteFlag(Constant.DELETE_FLAG_NOT);
                    corpDaoMapper.save(c);
                }
            }
            logger.info("执行线程内结束====================");
        }

    }


    /**
     * 获取企业永久授权码（回调推送或者附加在redirect_uri中跳转回第三方服务商网站）
     * @param authCode
     * @return 授权企业相关信息，需维护注册接口
     */
    public JSONObject getPermanentCode(String authCode, String suiteAccessToken) {
        String jsonStr = "{\"auth_code\": \""+ authCode +"\"}";
        JSONObject jsonObject = httpRequest(Constant.GET_PERMANENT_CODE.replace("SUITE_ACCESS_TOKEN", suiteAccessToken), "POST", jsonStr);
        return jsonObject;
    }

    /**
     * 获取第三方应用凭证
     * @param suiteId 服务商应用ID
     * @return
     */
    public static JSONObject getSuiteAccessToken(String suiteId, String suiteSecret, String suiteTicket) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("suite_id", suiteId);
        params.put("suite_secret", suiteSecret);
        params.put("suite_ticket", suiteTicket);
        String jsonStr = com.alibaba.fastjson.JSONObject.toJSONString(params);
        JSONObject jsonObject = httpRequest(Constant.GET_SUITE_TOKEN, "POST", jsonStr);
        if (jsonObject != null && jsonObject.containsKey("suite_access_token")) {
            return jsonObject;
        }
        return null;
    }

    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            //SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            System.out.println("HTTP请求返回：" + buffer.toString());
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
        } catch (Exception e) {
        }
        return jsonObject;
    }
}
