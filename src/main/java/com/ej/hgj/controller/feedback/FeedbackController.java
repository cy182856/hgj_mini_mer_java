package com.ej.hgj.controller.feedback;

import com.ej.hgj.controller.base.BaseController;
import com.ej.hgj.dao.config.ProConfDaoMapper;
import com.ej.hgj.dao.cst.HgjCstDaoMapper;
import com.ej.hgj.dao.feedback.FeedbackDaoMapper;
import com.ej.hgj.entity.feedback.FeedBack;
import com.ej.hgj.enums.JiasvBasicRespCode;
import com.ej.hgj.enums.MonsterBasicRespCode;
import com.ej.hgj.vo.ResponseVo;
import com.ej.hgj.vo.feedback.FeedbackRequestVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * 问题反馈
 */
@Controller
public class FeedbackController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FeedbackDaoMapper feedbackDaoMapper;

	@ResponseBody
	@RequestMapping("/feedbackQuery.do")
	public ResponseVo feedbackQuery(@RequestBody FeedbackRequestVo feedbackRequestVo) {
		ResponseVo responseVo = new ResponseVo();
		FeedBack feedBack = new FeedBack();
		String id = feedbackRequestVo.getId();
		feedBack.setId(id);
		PageHelper.offsetPage((feedbackRequestVo.getPageNum()-1) * feedbackRequestVo.getPageSize(),feedbackRequestVo.getPageSize());
		List<FeedBack> list = feedbackDaoMapper.getList(feedBack);
		PageInfo<FeedBack> pageInfo = new PageInfo<>(list);
		int pageNumTotal = (int) Math.ceil((double)pageInfo.getTotal()/(double)feedbackRequestVo.getPageSize());
		list = pageInfo.getList();
		logger.info("list返回记录数");
		logger.info(list != null ? list.size() + "":0 + "");
		responseVo.setPages(pageNumTotal);
		responseVo.setTotalNum((int) pageInfo.getTotal());
		responseVo.setPageSize(feedbackRequestVo.getPageSize());
		if(list != null){
			if(StringUtils.isNotBlank(id)) {
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
				//logger.info("报修图片:" + base64Img);
				responseVo.setFileList(fileList);
			}
		}
		responseVo.setFeedbackList(list);
		responseVo.setRespCode(MonsterBasicRespCode.SUCCESS.getReturnCode());
		responseVo.setErrCode(JiasvBasicRespCode.SUCCESS.getRespCode());
		responseVo.setErrDesc(JiasvBasicRespCode.SUCCESS.getRespDesc());
		return responseVo;
	}

}
