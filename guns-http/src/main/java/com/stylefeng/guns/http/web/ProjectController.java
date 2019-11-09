package com.stylefeng.guns.http.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.core.domain.Result;
import com.stylefeng.guns.core.util.ResultUtil;
import com.stylefeng.guns.http.model.Project;
import com.stylefeng.guns.http.service.IProjectService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 项目表 前端控制器
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-09
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private IProjectService projectServiceImpl;
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public Result<Object> list(){
		List<Project> list = projectServiceImpl.selectList(null);
		return ResultUtil.success(list);
	}
}

