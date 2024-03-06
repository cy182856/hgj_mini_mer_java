package com.ej.hgj.controller.repair;

import com.alibaba.fastjson.JSONObject;
import com.ej.hgj.constant.AjaxResult;
import com.ej.hgj.constant.Constant;
import com.ej.hgj.controller.base.BaseController;
import com.ej.hgj.dao.config.ConstantConfDaoMapper;
import com.ej.hgj.dao.config.ProConfDaoMapper;
import com.ej.hgj.dao.config.RepairConfDaoMapper;
import com.ej.hgj.dao.cst.HgjCstDaoMapper;
import com.ej.hgj.dao.hu.HgjHouseDaoMapper;
import com.ej.hgj.dao.repair.RepairLogDaoMapper;
import com.ej.hgj.dao.user.UserBuildDaoMapper;
import com.ej.hgj.dao.user.UserDaoMapper;
import com.ej.hgj.entity.config.ConstantConfig;
import com.ej.hgj.entity.config.ProConfig;
import com.ej.hgj.entity.config.RepairConfig;
import com.ej.hgj.entity.cst.HgjCst;
import com.ej.hgj.entity.hu.HgjHouse;
import com.ej.hgj.entity.repair.RepairLog;
import com.ej.hgj.entity.user.User;
import com.ej.hgj.entity.user.UserBuild;
import com.ej.hgj.entity.workord.Material;
import com.ej.hgj.entity.workord.ReturnVisit;
import com.ej.hgj.entity.workord.WorkOrd;
import com.ej.hgj.entity.workord.WorkPos;
import com.ej.hgj.enums.JiasvBasicRespCode;
import com.ej.hgj.enums.MonsterBasicRespCode;
import com.ej.hgj.sy.dao.workord.MaterialDaoMapper;
import com.ej.hgj.sy.dao.workord.ReturnVisitDaoMapper;
import com.ej.hgj.sy.dao.workord.WorkOrdDaoMapper;
import com.ej.hgj.sy.dao.workord.WorkPosDaoMapper;
import com.ej.hgj.utils.DateUtils;
import com.ej.hgj.utils.GenerateUniqueIdUtil;
import com.ej.hgj.utils.SyPostClient;
import com.ej.hgj.utils.TokenUtils;
import com.ej.hgj.vo.repair.RepairRequestVo;
import com.ej.hgj.vo.repair.RepairResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 新增报修接口
 * @author lx
 * @version $Id: RepairController.java, v 0.1 2020-8-14 下午2:10:48 lx Exp $
 */
@Controller
public class RepairController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${upload.path}")
	private String uploadPath;

	@Autowired
	private WorkOrdDaoMapper workOrdDaoMapper;

	@Autowired
	private HgjHouseDaoMapper hgjHouseDaoMapper;

	@Autowired
	private ProConfDaoMapper proConfDaoMapper;

	@Autowired
	private WorkPosDaoMapper workPosDaoMapper;

	@Autowired
	private RepairLogDaoMapper repairLogDaoMapper;

	@Autowired
	private MaterialDaoMapper materialDaoMapper;

	@Autowired
	private ReturnVisitDaoMapper returnVisitDaoMapper;

	@Autowired
	private RepairConfDaoMapper repairConfDaoMapper;

	@Autowired
	private ConstantConfDaoMapper constantConfDaoMapper;

	@Autowired
	private UserDaoMapper userDaoMapper;

	@Autowired
	private UserBuildDaoMapper userBuildDaoMapper;

	@ResponseBody
	@RequestMapping("/repair.do")
	public RepairResponseVo repair(HttpServletRequest request, @RequestBody RepairRequestVo repairRequestVo) {
		RepairResponseVo repairResponseVo = new RepairResponseVo();
		String userId = TokenUtils.getUserId(request);
		//User user = userDaoMapper.getByStaffId(userId);
		User user = userDaoMapper.getByUserId(userId);
		// 报修类型
		String repairType = repairRequestVo.getRepairType();
		// 客户编号
		//String cstCode = repairRequestVo.getCstCode();
		// 客户其他信息
		//HgjCst hgjCst = hgjCstDaoMapper.getByCstCode(cstCode);
		// 客户手机号
		//String phone = hgjCst.getMobile();
		String phone = user.getMobile();
		// 客户ID
		String cstId = "";
		// 客户名称
		//String cstName = hgjCst.getCstName();
		String cstName = user.getUserName();
		// 客户微信openId
		String wxOpenId = repairRequestVo.getWxOpenId();
		// 项目号
		ProConfig proConfig = proConfDaoMapper.getByProjectNum(user.getProjectNum());
		String orgId = proConfig.getProjectNum();
		// 获取思源接口地址
		ConstantConfig constantConfig = constantConfDaoMapper.getByKey("sy_url");
		// 获取token
		String token = SyPostClient.getToken(constantConfig.getConfigValue());
		// 项目名
		String orgName = proConfig.getProjectName();
		// 房屋id
		String houseId = repairRequestVo.getHouseId();
		// 报修描述
		String desc = "";
		// 工作位置
		String workPos = "";
		// 报修规则对应表-SRules
		String woNoBasicId = "";
		String woNoBasicName = "";
		String quesTypeId = "";
		String quesTypeName = "";
		// 单号 GGBX20230830112-公共  KHBX20230801003-客户
		List<RepairConfig> repairConfigList = repairConfDaoMapper.getByProjectNum(orgId);
		String no = "";
		if("S".equals(repairType)){
			RepairConfig s_repairConfig = repairConfigList.stream().filter(repairConfig -> "S".equals(repairConfig.getRepairType())).collect(Collectors.toList()).get(0);
			woNoBasicId = s_repairConfig.getWoNoBasicId();
			woNoBasicName = s_repairConfig.getWoNoBasicName();
			quesTypeId = s_repairConfig.getQuesTypeId();
			quesTypeName = s_repairConfig.getQuesTypeName();

			// 查询思源单号,第六位以后加1
			// WorkOrd woNo = workOrdDaoMapper.getKhBxWoNo(orgId);
//			if(woNo == null){
//				no = "KHBX" + DateUtils.strYmd()+ "001";
//			}else {
//				String sub_5 = woNo.getWoNo().substring(0,6);
//				String subWoNo = woNo.getWoNo().substring(6);
//				String strWoNo = (Integer.parseInt(subWoNo) + 1) + "";
//				no = sub_5 + strWoNo;
//			}
			String repairNum = constantConfDaoMapper.getByKey(Constant.S_REPAIR_NUM).getConfigValue();
			String sysDate = DateUtils.strYmd();
			String sRepairNumDate = repairNum.substring(0,8);
			if(sysDate.equals(sRepairNumDate)){
				Long repairNumInt = Long.valueOf(repairNum);
				no = "KHBX" + (repairNumInt + 1);
				ConstantConfig config = new ConstantConfig();
				config.setConfigKey(Constant.S_REPAIR_NUM);
				config.setConfigValue((repairNumInt + 1) + "");
				constantConfDaoMapper.update(config);
			}else {
				ConstantConfig config = new ConstantConfig();
				config.setConfigKey(Constant.S_REPAIR_NUM);
				config.setConfigValue(sysDate + "500");
				constantConfDaoMapper.update(config);
				no = "KHBX" + config.getConfigValue();
			}
			houseId = repairRequestVo.getHouseId();
			HgjHouse hgjHouse = hgjHouseDaoMapper.getById(houseId);
			// 客户报修工作位置
			WorkPos s_workPos = workPosDaoMapper.getWorkPos(orgId, hgjHouse.getResCode());
			workPos = s_workPos.getWorkPos();
			desc = repairRequestVo.getRepairDesc();
			// 客户报修客户名称是客户名字而不是报修人的名字
			RepairLog repairLog = repairLogDaoMapper.getCstNameByResId(houseId);
			cstId = repairLog.getCstId();
			cstName = repairLog.getCstName();
		} else if("P".equals(repairType)){
			RepairConfig p_repairConfig = repairConfigList.stream().filter(repairConfig -> "P".equals(repairConfig.getRepairType())).collect(Collectors.toList()).get(0);
			woNoBasicId = p_repairConfig.getWoNoBasicId();
			woNoBasicName = p_repairConfig.getWoNoBasicName();
			quesTypeId = p_repairConfig.getQuesTypeId();
			quesTypeName = p_repairConfig.getQuesTypeName();
//			List<HgjHouse> list = hgjHouseDaoMapper.getListByCstCode(cstCode);
//			if(!list.isEmpty()){
//				HgjHouse house = list.get(0);
//				houseId = house.getId();
//			}
			HgjHouse hgjHouse = hgjHouseDaoMapper.getById(houseId);
			// 客户报修工作位置
			WorkPos s_workPos = workPosDaoMapper.getWorkPos(orgId, hgjHouse.getResCode());
			// 查询思源单号,第六位以后加1
//			WorkOrd woNo = workOrdDaoMapper.getGgBxWoNo(orgId);
//			if(woNo == null){
//				no = "GGBX" + DateUtils.strYmd()+ "001";
//			}else {
//				String sub_5 = woNo.getWoNo().substring(0,6);
//				String subWoNo = woNo.getWoNo().substring(6);
//				String strWoNo = (Integer.parseInt(subWoNo) + 1) + "";
//				no = sub_5 + strWoNo;
//			}
			String repairNum = constantConfDaoMapper.getByKey(Constant.P_REPAIR_NUM).getConfigValue();
			String sysDate = DateUtils.strYmd();
			String sRepairNumDate = repairNum.substring(0,8);
			if(sysDate.equals(sRepairNumDate)){
				Long repairNumInt = Long.valueOf(repairNum);
				no = "GGBX" + (repairNumInt + 1);
				ConstantConfig config = new ConstantConfig();
				config.setConfigKey(Constant.P_REPAIR_NUM);
				config.setConfigValue((repairNumInt + 1) + "");
				constantConfDaoMapper.update(config);
			}else {
				ConstantConfig config = new ConstantConfig();
				config.setConfigKey(Constant.P_REPAIR_NUM);
				config.setConfigValue(sysDate + "500");
				constantConfDaoMapper.update(config);
				no = "GGBX" + config.getConfigValue();
			}
			// 公共区域报修工作位置 001-办公楼  002-商场
			if("001".equals(hgjHouse.getGrpCode())){
				ConstantConfig config = constantConfDaoMapper.getByKey("001");
				//workPos = "东方渔人码头_办公楼_办公楼公共区域_办公楼公共区_办公楼公共区";
				workPos = config.getConfigValue();
			}else if("002".equals(hgjHouse.getGrpCode())){
				ConstantConfig config = constantConfDaoMapper.getByKey("002");
				//workPos = "东方渔人码头_商场_商场公共区域_商场公共区_商场公共区";
				workPos = config.getConfigValue();
			}
			//desc = s_workPos.getWorkPos() + ";" +repairRequestVo.getRepairDesc();
			desc = repairRequestVo.getRepairDesc();
		}

		// 图片
		String[] fileList = repairRequestVo.getFileList();
		// 发送请求
		String p7 = initTicket(orgId, orgName, no, cstId, cstName,
				woNoBasicId, woNoBasicName, quesTypeId, quesTypeName, houseId, workPos,
				desc, desc, phone, fileList);
		// 获取请求结果
		JSONObject jsonObject = SyPostClient.callSy("User_Service_SaveWorkOrdInfoAndroid", p7, token, constantConfig.getConfigValue());
		String status = jsonObject.getString("status");
		String msg = jsonObject.getString("msg");
		//String status = "1";
		//String msg = "提交成功";
		if("1".equals(status)){
			repairResponseVo.setRespCode(MonsterBasicRespCode.SUCCESS.getReturnCode());
			repairResponseVo.setErrCode(JiasvBasicRespCode.SUCCESS.getRespCode());
			repairResponseVo.setErrDesc(JiasvBasicRespCode.SUCCESS.getRespDesc());
			// 保存报修记录
			RepairLog repairLog = new RepairLog();
			// 将图片数组转换为逗号分割的字符串
			repairLog.setImage(saveImg(fileList,no));
			repairLog.setId(GenerateUniqueIdUtil.getGuid());
			repairLog.setProjectNum(orgId);
			repairLog.setProjectName(orgName);
			repairLog.setRepairNum(no);
			repairLog.setWxOpenId(wxOpenId);
			repairLog.setCstName(cstName);
			repairLog.setCstMobile(phone);
			repairLog.setHouseId(houseId);
			repairLog.setServiceType(woNoBasicName);
			repairLog.setQuesType(quesTypeName);
			repairLog.setRepairDesc(repairRequestVo.getRepairDesc());
			repairLog.setWorkPos(workPos);
			repairLog.setRepairType(repairRequestVo.getRepairType());
			repairLog.setRepairStatus("WOSta_Sub");
			repairLog.setCreateTime(new Date());
			repairLog.setUpdateTime(new Date());
			repairLog.setDeleteFlag(0);
			repairLogDaoMapper.save(repairLog);
		}else {
			repairResponseVo.setRespCode(Constant.FAIL_CODE);
			repairResponseVo.setErrDesc(msg);
		}

		return repairResponseVo;
	}

	public String saveImg(String[] fileList, String no) {
		String path = "";
		// 将图片数组转换为逗号分割的字符串
		if (fileList.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (String str : fileList) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(str);
			}
			//目录不存在则直接创建
			File filePath = new File(uploadPath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			//创建年月日文件夹
			File ymdFile = new File(uploadPath + File.separator + new SimpleDateFormat("yyyyMMdd").format(new Date()));
			//目录不存在则直接创建
			if (!ymdFile.exists()) {
				ymdFile.mkdirs();
			}
			//在年月日文件夹下面创建txt文本存储图片base64码
			File txtFile = new File(ymdFile.getPath() + "/" + no + ".txt");
			if (!txtFile.exists()) {
				try {
					txtFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			path = txtFile.getPath();
			FileWriter writer = null;
			try {
				writer = new FileWriter(txtFile);
				writer.write(sb.toString());
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path;
	}

	// 组装参数
	public String initTicket(String orgId, String orgName, String no, String cstId, String cstName,
							 String woNoBasicId, String woNoBasicName, String quesTypeId, String quesTypeName, String houseId,
							 String workPos, String desc, String descDi, String phone, String[] pics){
		String OrgID = "{ \"OrgID\":\"" + orgId + "\",";
		String OrgName = "\"OrgName\":\"" + orgName + "\",";
		String WONo = "\"WONo\":\"" + no + "\",";
		String RSDate = "\"RSDate\":\"" + DateUtils.strYmdHms() + "\",";
		// WONoBasicID,WONoBasicName  报事主规则（一般报事、投诉等）
		String WONoBasicID = "\"WONoBasicID\": \"" + woNoBasicId + "\",";
		String WONoBasicName = "\"WONoBasicName\":\"" + woNoBasicName + "\",";
		// String CstID = "\"CstID\":\"\",";
		String CstID = "\"CstID\":\"" + cstId + "\",";
		String CstName = "\"CstName\":\"" + cstName + "\",";
		String WorkPosFrom = "\"WorkPosFrom\":\"Resource\",";
		String WOID = "\"WOID\":\"" + houseId + "\",";
		String WorkPos = "\"WorkPos\":\"" + workPos + "\",";
		String RSWay = "\"RSWay\": \"wx\",";
		String CallPhone = "\"CallPhone\":\"" + phone + "\",";
		String RStartTime = "\"RStartTime\":\"" + DateUtils.strYmdHms() + "\",";
		String Importance = "\"Importance\":\"1\",";
		String Urgency = "\"Urgency\":\"1\",";
		String Intricacy = "\"Intricacy\":\"1\",";
		String PaidServices = "\"PaidServices\":\"1\",";
		String Orders = "\"Orders\":\"\",";
		String OrdersID = "\"OrdersID\":\"\",";
		String OrdersDepart = "\"OrdersDepart\":\"\","; ;
		String OrdersPositionID = "\"OrdersPositionID\":\"\","; ;
		String WorkQuestion = "\"WorkQuestion\":[{";
		String QuesTypeID = "\"QuesTypeID\":\"" + quesTypeId + "\",";
		String QuesTypeName = "\"QuesTypeName\":\"" + quesTypeName + "\",";
		String QuesDesc = "\"QuesDesc\":\"" + desc + "\",";
		String QuesDeti = "\"QuesDeti\":\"" + descDi + "\",";
		String CreateTime = "\"CreateTime\":\"\",";
		String CreateUser = "\"CreateUser\":\"\",";
		String OpTime = "\"OpTime\":\"\",";
		String OpUser = "\"OpUser\":\"\",";

		for(int i = 0; i<pics.length; i++ ){
			if (i == 0) {
				CreateTime = "\"CreateTime\":\"" + pics[i] + "\",";
			}
			if (i == 1) {
				CreateUser = "\"CreateUser\":\"" + pics[i] + "\",";
			}
			if (i == 2) {
				OpTime = "\"OpTime\":\"" + pics[i] + "\",";
			}
			if (i == 3) {
				OpUser = "\"OpUser\":\"" + pics[i] + "\",";
			}
		}

		OpUser += "}]}";

		return OrgID + OrgName + WONo + RSDate + WONoBasicID + WONoBasicName +
				CstID + CstName + WorkPosFrom + WOID + WorkPos + RSWay + CallPhone + RStartTime +
				Importance + Urgency + Intricacy + PaidServices + Orders + OrdersID + OrdersDepart +
				OrdersPositionID + WorkQuestion + QuesTypeID + QuesTypeName + QuesDesc + QuesDeti +
				CreateTime + CreateUser + OpTime + OpUser;
	}

//	@RequestMapping("/repairHouseList")
//	@ResponseBody
//    public AjaxResult queryRepairList(HttpServletRequest request,@RequestBody RepairRequestVo repairRequestVo){
//		String userId = TokenUtils.getUserId(request);
//		User user = userDaoMapper.getByStaffId(userId);
//		ProConfig proConfig = proConfDaoMapper.getByProjectNum(user.getProjectNum());
//		AjaxResult ajaxResult = new AjaxResult();
//		HashMap map = new HashMap();
//		String cstCode = repairRequestVo.getCstCode();
//		List<HgjHouse> list = hgjHouseDaoMapper.getListByCstCode(cstCode);
//		map.put("list",list);
//		map.put("orgName",proConfig.getProjectName());
//		ajaxResult.setRespCode(Constant.SUCCESS);
//		ajaxResult.setMessage(Constant.SUCCESS_RESULT_MESSAGE);
//		ajaxResult.setData(map);
//		return ajaxResult;
//    }

	/**
	 * 获取分期,项目名
	 * */
	@RequestMapping("/queryStages")
	@ResponseBody
	public AjaxResult queryStages(HttpServletRequest request,@RequestBody RepairRequestVo repairRequestVo){
		String userId = TokenUtils.getUserId(request);
		//User user = userDaoMapper.getByStaffId(userId);
		User user = userDaoMapper.getByUserId(userId);
		ProConfig proConfig = proConfDaoMapper.getByProjectNum(user.getProjectNum());
		AjaxResult ajaxResult = new AjaxResult();
		HashMap map = new HashMap();
		List<HgjHouse> list = hgjHouseDaoMapper.queryStages(user.getProjectNum());
		map.put("list",list);
		map.put("orgName",proConfig.getProjectName());
		ajaxResult.setRespCode(Constant.SUCCESS);
		ajaxResult.setMessage(Constant.SUCCESS_RESULT_MESSAGE);
		ajaxResult.setData(map);
		return ajaxResult;
	}

    /**
	 * 获取楼栋
	 * */
	@RequestMapping("/queryBuilding")
	@ResponseBody
	public AjaxResult queryBuilding(HttpServletRequest request,@RequestBody RepairRequestVo repairRequestVo){
		String userId = TokenUtils.getUserId(request);
		//User user = userDaoMapper.getByStaffId(userId);
		User user = userDaoMapper.getByUserId(userId);
		String grpCode = repairRequestVo.getGrpCode();
		AjaxResult ajaxResult = new AjaxResult();
		HashMap map = new HashMap();
		List<HgjHouse> list = hgjHouseDaoMapper.queryBuilding(grpCode,user.getProjectNum());
		map.put("list",list);
		ajaxResult.setRespCode(Constant.SUCCESS);
		ajaxResult.setMessage(Constant.SUCCESS_RESULT_MESSAGE);
		ajaxResult.setData(map);
		return ajaxResult;
	}

	/**
	 * 获取房号
	 * */
	@RequestMapping("/queryRoomNum")
	@ResponseBody
	public AjaxResult queryRoomNum(@RequestBody RepairRequestVo repairRequestVo){
		String budId = repairRequestVo.getBuildingId();
		AjaxResult ajaxResult = new AjaxResult();
		HashMap map = new HashMap();
		List<HgjHouse> list = hgjHouseDaoMapper.queryRoomNum(budId);
		map.put("list",list);
		ajaxResult.setRespCode(Constant.SUCCESS);
		ajaxResult.setMessage(Constant.SUCCESS_RESULT_MESSAGE);
		ajaxResult.setData(map);
		return ajaxResult;
	}

	@ResponseBody
	@RequestMapping("/queryRepairLogSy.do")
	public RepairResponseVo queryRepairLogSy(HttpServletRequest request, @RequestBody RepairRequestVo repairRequestVo) {
		String userId = TokenUtils.getUserId(request);
		//User user = userDaoMapper.getByStaffId(userId);
		User user = userDaoMapper.getByUserId(userId);
		// 查询管家绑定的楼栋
		List<String> budIdList = new ArrayList<>();
		UserBuild userBuild = new UserBuild();
		userBuild.setMobile(user.getMobile());
		List<UserBuild> userBuildList = userBuildDaoMapper.getList(userBuild);
		if(!userBuildList.isEmpty()){
			for(UserBuild build : userBuildList){
				budIdList.add(build.getBudId());
			}
		}
		RepairResponseVo repairResponseVo = new RepairResponseVo();
//		RepairLog repairLog = new RepairLog();
//		repairLog.setRepairStatus(repairRequestVo.getRepairStatus());
//		repairLog.setProjectNum(user.getProjectNum());
//		repairLog.setCstMobile(user.getMobile());
		PageHelper.offsetPage((repairRequestVo.getPageNum()-1) * repairRequestVo.getPageSize(),repairRequestVo.getPageSize());
		//List<RepairLog> list = repairLogDaoMapper.getList(repairLog);
		WorkOrd workOrd = new WorkOrd();
		workOrd.setRepairStatus(repairRequestVo.getRepairStatus());
		workOrd.setBudIdList(budIdList);
		List<WorkOrd> list = workOrdDaoMapper.getListSy(workOrd);
		PageInfo<WorkOrd> pageInfo = new PageInfo<>(list);
		int pageNumTotal = (int) Math.ceil((double)pageInfo.getTotal()/(double)repairRequestVo.getPageSize());
		list = pageInfo.getList();
		logger.info("list返回记录数");
		logger.info(list != null ? list.size() + "":0 + "");
		repairResponseVo.setPages(pageNumTotal);
		repairResponseVo.setTotalNum((int) pageInfo.getTotal());
		repairResponseVo.setPageSize(repairRequestVo.getPageSize());
		repairResponseVo.setRepairLogList(list);
		repairResponseVo.setRespCode(MonsterBasicRespCode.SUCCESS.getReturnCode());
		repairResponseVo.setErrCode(JiasvBasicRespCode.SUCCESS.getRespCode());
		repairResponseVo.setErrDesc(JiasvBasicRespCode.SUCCESS.getRespDesc());
		return repairResponseVo;
	}

	@ResponseBody
	@RequestMapping("/queryRepairLogSyById.do")
	public RepairResponseVo queryRepairLogSyById(HttpServletRequest request, @RequestBody RepairRequestVo repairRequestVo) {
		RepairResponseVo repairResponseVo = new RepairResponseVo();
		WorkOrd workOrd = new WorkOrd();
		workOrd.setRepairNum(repairRequestVo.getRepairNum());
		WorkOrd workOrds = workOrdDaoMapper.getRepairInfo(workOrd);
		repairResponseVo.setRepairInfo(workOrds);
		repairResponseVo.setRespCode(MonsterBasicRespCode.SUCCESS.getReturnCode());
		repairResponseVo.setErrCode(JiasvBasicRespCode.SUCCESS.getRespCode());
		repairResponseVo.setErrDesc(JiasvBasicRespCode.SUCCESS.getRespDesc());
		return repairResponseVo;
	}

	@ResponseBody
	@RequestMapping("/queryRepairLog.do")
	public RepairResponseVo queryRepairLog(HttpServletRequest request, @RequestBody RepairRequestVo repairRequestVo) {
		String userId = TokenUtils.getUserId(request);
		//User user = userDaoMapper.getByStaffId(userId);
		User user = userDaoMapper.getByUserId(userId);
		RepairResponseVo repairResponseVo = new RepairResponseVo();
		RepairLog repairLog = new RepairLog();
		String repairNum = repairRequestVo.getRepairNum();
		repairLog.setRepairStatus(repairRequestVo.getRepairStatus());
		repairLog.setProjectNum(user.getProjectNum());
		//repairLog.setCstCode(repairRequestVo.getCstCode());
		//repairLog.setWxOpenId(repairRequestVo.getWxOpenId());

		repairLog.setCstMobile(user.getMobile());
		repairLog.setRepairNum(repairNum);
		PageHelper.offsetPage((repairRequestVo.getPageNum()-1) * repairRequestVo.getPageSize(),repairRequestVo.getPageSize());
		List<RepairLog> list = repairLogDaoMapper.getList(repairLog);
		PageInfo<RepairLog> pageInfo = new PageInfo<>(list);
		int pageNumTotal = (int) Math.ceil((double)pageInfo.getTotal()/(double)repairRequestVo.getPageSize());
		list = pageInfo.getList();
		logger.info("list返回记录数");
		logger.info(list != null ? list.size() + "":0 + "");
		repairResponseVo.setPages(pageNumTotal);
		repairResponseVo.setTotalNum((int) pageInfo.getTotal());
		repairResponseVo.setPageSize(repairRequestVo.getPageSize());
		if(list != null){
			if(repairNum != null) {
				// 获取图片
				String imgPath = list.get(0).getImage();
				String base64Img = "";
				try {
					// 创建BufferedReader对象，从本地文件中读取
					BufferedReader reader = new BufferedReader(new FileReader(imgPath));
					// 逐行读取文件内容
					String line = "";
					while ((line = reader.readLine()) != null) {
						base64Img += line;
					}
					// 关闭文件
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				String[] fileList = base64Img.split(",");
				repairResponseVo.setFileList(fileList);
			}
			// 获取思源报修单状态
			List<String> woNoList = new ArrayList<>();
			for(RepairLog r : list){
				woNoList.add(r.getRepairNum());
			}

//			if(!woNoList.isEmpty()) {
//				List<WorkOrd> syWorkOrdList = workOrdDaoMapper.getList(woNoList);
//				logger.info("syWorkOrdList返回记录数");
//				logger.info(syWorkOrdList != null ? syWorkOrdList.size() + "":0 + "");
//				for (RepairLog rl : list) {
//					List<WorkOrd> syWorkOrdListFilter = syWorkOrdList.stream().filter(syOl -> rl.getRepairNum().equals(syOl.getWoNo())).collect(Collectors.toList());
//					if (syWorkOrdListFilter != null) {
//						rl.setRepairStatus(syWorkOrdListFilter.get(0).getWorkOrdState());
//					}
//				}
//			}
		}

		// 根据状态查询的时候过滤list
//		List<RepairLog> listFilter = new ArrayList<>();
//		if("0".equals(repairStatus) || repairStatus == null){
//			// 全部
//			listFilter = list;
//		}
//		if("1".equals(repairStatus)){
//			// 已提交
//			listFilter = list.stream().filter(l -> "WOSta_Sub".equals(l.getRepairStatus())).collect(Collectors.toList());
//		}
//		if("2".equals(repairStatus)){
//			// 处理中
//			listFilter = list.stream().filter(l -> "WOSta_Proc".equals(l.getRepairStatus())).collect(Collectors.toList());
//		}
//		if("3".equals(repairStatus)){
//			// 已完工 包括 WOSta_Visit-已回访,WOSta_Close-已关闭
//			listFilter = list.stream().filter(l -> "WOSta_Finish".equals(l.getRepairStatus())
//					|| "WOSta_Visit".equals(l.getRepairStatus()) || "WOSta_Close".equals(l.getRepairStatus())
//			).collect(Collectors.toList());
//		}
//		repairResponseVo.setRepairLogList(listFilter);
		//repairResponseVo.setRepairLogList(list);
		repairResponseVo.setRespCode(MonsterBasicRespCode.SUCCESS.getReturnCode());
		repairResponseVo.setErrCode(JiasvBasicRespCode.SUCCESS.getRespCode());
		repairResponseVo.setErrDesc(JiasvBasicRespCode.SUCCESS.getRespDesc());
		return repairResponseVo;
	}


	/**\
	 *管家服务楼栋查询
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/serviceBuild.do")
	public RepairResponseVo serviceBuild(HttpServletRequest request) {
		String userId = TokenUtils.getUserId(request);
		//User user = userDaoMapper.getByStaffId(userId);
		User user = userDaoMapper.getByUserId(userId);
		RepairResponseVo repairResponseVo = new RepairResponseVo();
		RepairLog repairLog = new RepairLog();
		repairLog.setProjectNum(user.getProjectNum());
		repairLog.setCstMobile(user.getMobile());
		List<HgjHouse> list = hgjHouseDaoMapper.queryByMobile(user.getMobile());
		logger.info("list返回记录数:" + list != null ? list.size() + "":0 + "");
		logger.info(list != null ? list.size() + "":0 + "");
		repairResponseVo.setHouseList(list);
		repairResponseVo.setRespCode(MonsterBasicRespCode.SUCCESS.getReturnCode());
		repairResponseVo.setErrCode(JiasvBasicRespCode.SUCCESS.getRespCode());
		repairResponseVo.setErrDesc(JiasvBasicRespCode.SUCCESS.getRespDesc());
		return repairResponseVo;
	}


	@ResponseBody
	@RequestMapping("/repair/addRepairMsg.do")
	public RepairResponseVo addRepairMsg(HttpServletRequest request, @RequestBody RepairRequestVo repairRequestVo) {
		String userId = TokenUtils.getUserId(request);
		//User user = userDaoMapper.getByStaffId(userId);
		RepairResponseVo repairResponseVo = new RepairResponseVo();
		// 根据单号查询保修单ID
		WorkOrd workOrd = workOrdDaoMapper.getCsWorkOrd(repairRequestVo.getRepairNum(), "WOSta_Finish");
		// 根据报修单id查询思源已回访记录
		List<ReturnVisit> list = returnVisitDaoMapper.getList(workOrd.getId());
		if(!list.isEmpty()){
			repairResponseVo.setRespCode(MonsterBasicRespCode.RESULT_FAILED.getReturnCode());
			repairResponseVo.setErrCode(JiasvBasicRespCode.RESULT_FAILED.getRespCode());
			repairResponseVo.setErrDesc("客服已回访!");
		}else {
			String repairScore = repairRequestVo.getRepairScore();
			String totalScore = "";
			if ("1".equals(repairScore)) totalScore = "20";
			if ("2".equals(repairScore)) totalScore = "40";
			if ("3".equals(repairScore)) totalScore = "60";
			if ("4".equals(repairScore)) totalScore = "80";
			if ("5".equals(repairScore)) totalScore = "100";
			// 满意度
			String hgjSatisFaction = repairRequestVo.getSatisFaction();
			String sySatisFaction = "";
			if ("0".equals(hgjSatisFaction)) {
				sySatisFaction = "100";
			} else {
				sySatisFaction = "50";
			}
			// 获取思源接口地址
			ConstantConfig constantConfig = constantConfDaoMapper.getByKey( "sy_url");
			// 获取token
			String token = SyPostClient.getToken(constantConfig.getConfigValue());

			//workOrd.setId("2307201424330001006P");
			String p7 = initReturnVisit(workOrd.getId(), sySatisFaction, totalScore, repairRequestVo.getRepairMsg());

			// 获取请求结果, 调用思源接口 94-客服回访接口，修改工单状态为已回访
			JSONObject jsonObject = SyPostClient.returnVisitCallSy("UserRev2_Service_CustomerServiceReturn", p7, token, constantConfig.getConfigValue());
			String status = jsonObject.getString("Status");
			String msg = jsonObject.getString("Msg");
			if ("1".equals(status)) {
				RepairLog repairLog = new RepairLog();
				repairLog.setRepairNum(repairRequestVo.getRepairNum());
				repairLog.setRepairScore(repairScore);
				repairLog.setRepairMsg(repairRequestVo.getRepairMsg());
				repairLog.setSatisFaction(hgjSatisFaction);
				repairLog.setRepairStatus("WOSta_Visit");
				repairLogDaoMapper.update(repairLog);
				repairResponseVo.setRespCode(MonsterBasicRespCode.SUCCESS.getReturnCode());
				repairResponseVo.setErrCode(JiasvBasicRespCode.SUCCESS.getRespCode());
				repairResponseVo.setErrDesc(JiasvBasicRespCode.SUCCESS.getRespDesc());
			} else {
				repairResponseVo.setRespCode(MonsterBasicRespCode.RESULT_FAILED.getReturnCode());
				repairResponseVo.setErrCode(JiasvBasicRespCode.RESULT_FAILED.getRespCode());
				repairResponseVo.setErrDesc(JiasvBasicRespCode.RESULT_FAILED.getRespDesc());
			}
		}
		return repairResponseVo;
	}


	@ResponseBody
	@RequestMapping("/queryRepairCostDetail.do")
	public RepairResponseVo queryRepairCostDetail(@RequestBody RepairRequestVo repairRequestVo) {
		RepairResponseVo repairResponseVo = new RepairResponseVo();
		String repairNum = repairRequestVo.getRepairNum();
		// 根据单号查询保修单ID
		WorkOrd workOrd = workOrdDaoMapper.getCsWorkOrd(repairNum,"WOSta_Visit");
		// 获取材料明细
		List<Material> list = new ArrayList<>();
		if(workOrd != null){
			list = materialDaoMapper.getList(workOrd.getId());
		}
		// 获取人工费、材料费
		RepairLog repairLog = repairLogDaoMapper.getByRepNum(repairNum);
		if(repairLog != null){
			repairResponseVo.setLabourCost(repairLog.getLabourCost());
			repairResponseVo.setMaterialCost(repairLog.getMaterialCost());
		}
		repairResponseVo.setMaterialList(list);
		repairResponseVo.setRespCode(MonsterBasicRespCode.SUCCESS.getReturnCode());
		repairResponseVo.setErrCode(JiasvBasicRespCode.SUCCESS.getRespCode());
		repairResponseVo.setErrDesc(JiasvBasicRespCode.SUCCESS.getRespDesc());
		return repairResponseVo;
	}


	// 组装参数-客服回访
	public String initReturnVisit(String repairId, String satisFaction, String totalScore, String desc){
		String WOID = "{ \"WOID\":\"" + repairId + "\",";
		String ReturnVisitWay = "\"ReturnVisitWay\":\"Tel\",";
		String ReturnVisitDate = "\"ReturnVisitDate\":\"" + DateUtils.strYmdHms() + "\",";
		String Object = "\"Object\":\"17082215304300020066\",";
		String ObjectName = "\"ObjectName\":\"KF01\",";
		String SatisfiedVisit = "\"SatisfiedVisit\":\"" + satisFaction + "\",";
		//String FailureCause = "\"FailureCause\":\"很满意\",";
		String Remark = "\"Remark\":\"" + desc + "\",";
		String TotalScore = "\"TotalScore\":\"" + totalScore + "\",";
		String VisitState = "\"VisitState\":\"1\",";
		String UserId = "\"UserId\":\"Sam\",";
		UserId += "}";
		return WOID + ReturnVisitWay + ReturnVisitDate + Object + ObjectName + SatisfiedVisit + Remark + TotalScore + VisitState + UserId;
	}


	public static void main(String[] args) {
		// 思源接口-正式环境
		// String url = "http://192.168.5.201:4321/NetApp/CstService.asmx/GetService";
		// 思源接口-测试环境
		String url = "http://192.168.99.1:4321/NetApp/CstService.asmx/GetService";
		// 获取token
		String token = SyPostClient.getToken(url);
		RepairController repairController = new RepairController();
		// KHBX20230801005 - 100    2308011415310001006P   	Satisfaction2 -满意
		// KHBX20230801004 - 90 	2308011207060001006P   	Satisfaction2 -满意
		// KHBX20230801003 - 80 	2308011058100001006P	Satisfaction2 -满意
		// GGBX20230731028 - 70		2307311604210001009U	Satisfaction2 -满意
		// KHBX20230728007 - 60		2307281713330001006P	Satisfaction2 -满意
		// GGBX20230728008 - 50		2307281336160001006P	NotSatisfaction1 - 不满意
		// KHBX20230728002 - 40		2307281135250001006P	NotSatisfaction1 - 不满意
		// GGBX20230726012 - 30		2307261340180001006P	NotSatisfaction1 - 不满意
		// GGBX20230724178b - 150	2023072412122028d418
		// KHBX20230724002
		// CS_ReturnVisit
		String p7 = repairController.initReturnVisit("2307241147450001006P","100" , "100", "111");
		// 获取请求结果, 调用思源接口 94-客服回访接口，修改工单状态为已回访
		JSONObject jsonObject = SyPostClient.returnVisitCallSy("UserRev2_Service_CustomerServiceReturn", p7, token, url);
		String status = jsonObject.getString("Status");
		String msg = jsonObject.getString("Msg");
		System.out.println("status:"+status+"----msg:"+msg);
	}
}
