package com.ej.hgj.controller.auth;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.ej.hgj.constant.Constant;
import com.ej.hgj.controller.base.BaseController;
import com.ej.hgj.dao.user.UserDaoMapper;
import com.ej.hgj.entity.menu.Menu;
import com.ej.hgj.entity.user.User;
import com.ej.hgj.entity.user.UserRole;
import com.ej.hgj.enums.JiamsvBasicRespCode;
import com.ej.hgj.enums.MonsterBasicRespCode;
import com.ej.hgj.service.menu.MenuService;
import com.ej.hgj.service.user.UserRoleService;
import com.ej.hgj.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PoAuthInfoController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserDaoMapper userDaoMapper;

//	@RequestMapping("/usr/poauth")
//	@ResponseBody
//	public JSONObject getPoAuthInfo(HttpServletRequest request) {
//		JSONObject jsonObject = new JSONObject();
//		try {
//			String token = request.getHeader("token");
//			String stuffId = JWT.decode(token).getAudience().get(0);
//			User user = userDaoMapper.getByStaffId(stuffId);
//			// 查询企微所有菜单
//			Menu menu = new Menu();
//			menu.setPlatForm(Constant.PLAT_FORM_WE_COM);
//			List<Menu> allMenuList = menuService.getMenuList(menu);
//			// 获取用户角色
//			UserRole userRole = userRoleService.getByStaffId(stuffId);
//			// 查询角色已拥有的企微菜单权限
//			List<Menu> alreadyMenuListAll = menuService.findMenuByRoleId(userRole.getRoleId());
//			// 企微权限树
//			List<Menu> menuList = new ArrayList<>();
//			menuList.addAll(alreadyMenuListAll);
//			// 子菜单不为空的时候才加载父菜单
//			if(!alreadyMenuListAll.isEmpty()){
//				for(Menu subMenu : alreadyMenuListAll){
//					// 查询企微已拥有权限的父级菜单
//					List<Menu> weComMenuParentList = allMenuList.stream().filter(allMenu -> allMenu.getParentId() == 0 && allMenu.getId() == subMenu.getParentId()).collect(Collectors.toList());
//					menuList.addAll(weComMenuParentList);
//				}
//			}
//			jsonObject.put(Constant.RESP_CODE, MonsterBasicRespCode.SUCCESS.getReturnCode());
//			if(!menuList.isEmpty() && user.getProjectNum() != null && !"".equals(user.getProjectNum())) {
//				jsonObject.put("authList", menuList);
//			}
//		}catch (BusinessException be) {
//			logger.info("查询物管人员权限信息业务异常");
//			jsonObject.put(Constant.RESP_CODE, MonsterBasicRespCode.RESULT_FAILED.getReturnCode());
//			jsonObject.put(Constant.ERR_CODE, be.getErrCode());
//			jsonObject.put(Constant.ERR_DESC, be.getErrDesc());
//		}catch (Exception e) {
//			logger.info("查询物管人员权限信息系统异常");
//			jsonObject.put(Constant.RESP_CODE, MonsterBasicRespCode.RESULT_FAILED.getReturnCode());
//			jsonObject.put(Constant.ERR_CODE, JiamsvBasicRespCode.SYSTEM_EXCEPTION.getRespCode());
//			jsonObject.put(Constant.ERR_DESC, JiamsvBasicRespCode.SYSTEM_EXCEPTION.getRespDesc());
//		}
//		return jsonObject;
//	}

	@RequestMapping("/usr/poauth")
	@ResponseBody
	public JSONObject getPoAuthInfo(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			String token = request.getHeader("token");
			String stuffId = JWT.decode(token).getAudience().get(0);
			User user = userDaoMapper.getByUserId(stuffId);
			// 查询企微所有菜单
			Menu menu = new Menu();
			menu.setPlatForm(Constant.PLAT_FORM_WE_COM);
			List<Menu> allMenuList = menuService.getMenuList(menu);
			// 获取用户角色
			UserRole userRole = userRoleService.getByStaffId(stuffId);
			if(userRole == null){
				logger.info("权限未分配");
//				jsonObject.put(Constant.RESP_CODE, MonsterBasicRespCode.RESULT_FAILED.getReturnCode());
//				jsonObject.put(Constant.ERR_CODE, JiamsvBasicRespCode.NO_PERMISSION.getRespCode());
//				jsonObject.put(Constant.ERR_DESC, JiamsvBasicRespCode.NO_PERMISSION.getRespDesc());
				return jsonObject;
			}
			// 查询角色已拥有的企微菜单权限
			List<Menu> alreadyMenuListAll = menuService.findMenuByRoleId(userRole.getRoleId());
			// 企微权限树
			List<Menu> menuList = new ArrayList<>();
			menuList.addAll(alreadyMenuListAll);
			// 子菜单不为空的时候才加载父菜单
			if(!alreadyMenuListAll.isEmpty()){
				for(Menu subMenu : alreadyMenuListAll){
					// 查询企微已拥有权限的父级菜单
					List<Menu> weComMenuParentList = allMenuList.stream().filter(allMenu -> allMenu.getParentId() == 0 && allMenu.getId() == subMenu.getParentId()).collect(Collectors.toList());
					menuList.addAll(weComMenuParentList);
				}
			}
			jsonObject.put(Constant.RESP_CODE, MonsterBasicRespCode.SUCCESS.getReturnCode());
			if(!menuList.isEmpty() && user.getProjectNum() != null && !"".equals(user.getProjectNum())) {
				jsonObject.put("authList", menuList);
			}
		}catch (BusinessException be) {
			logger.info("查询物管人员权限信息业务异常");
			jsonObject.put(Constant.RESP_CODE, MonsterBasicRespCode.RESULT_FAILED.getReturnCode());
			jsonObject.put(Constant.ERR_CODE, be.getErrCode());
			jsonObject.put(Constant.ERR_DESC, be.getErrDesc());
		}catch (Exception e) {
			logger.info("查询物管人员权限信息系统异常");
			jsonObject.put(Constant.RESP_CODE, MonsterBasicRespCode.RESULT_FAILED.getReturnCode());
			jsonObject.put(Constant.ERR_CODE, JiamsvBasicRespCode.SYSTEM_EXCEPTION.getRespCode());
			jsonObject.put(Constant.ERR_DESC, JiamsvBasicRespCode.SYSTEM_EXCEPTION.getRespDesc());
		}
		return jsonObject;
	}
}
