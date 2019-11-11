package com.stylefeng.guns.http.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.domain.Result;
import com.stylefeng.guns.core.util.ResultUtil;
import com.stylefeng.guns.http.core.factory.PageFactory;
import com.stylefeng.guns.http.model.Project;
import com.stylefeng.guns.http.service.IProjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

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
@Api(value = "项目控制器", tags= {"项目相关接口"})
@Controller
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private IProjectService projectServiceImpl;
	
	@ApiOperation(value = "获取轮播列表")
	@GetMapping(value = "/swiperList")
	@ResponseBody
	public Result<Object> swiperList(){
		List<Map<String, Object>> list = projectServiceImpl.getTopFiveSwiperList();
		return ResultUtil.success(list);
	}
	
	@ApiOperation(value = "获取项目列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="pageNum", value="当前页码", required=true, paramType="query", dataType="int", defaultValue="0"),
		@ApiImplicitParam(name="pageSize", value="每页条数", required=true, paramType="query", dataType="int", defaultValue="5"),
		@ApiImplicitParam(name="condition", value="检索条件", required=true, paramType="query", dataType="String", defaultValue="")
	})
	@GetMapping(value = "/list")
	@ResponseBody
	public Result<Object> list(String condition){
		Page<Project> page = new PageFactory<Project>().defaultPage();
		List<Project> list = projectServiceImpl.getProjectListByCondition(page, condition);
		page.setRecords(list);
		return ResultUtil.success(page);
	}

	@ApiOperation(value = "获取项目详情")
	@ApiImplicitParams({
		@ApiImplicitParam(name="projectid", value="项目id", required=true, paramType="query", dataType="int", defaultValue="8")
	})
	@GetMapping(value = "/projectDetail")
	@ResponseBody
	public Result<Object> projectDetail(Integer projectid){
		Project project = projectServiceImpl.getProjectDetail(projectid);
		return ResultUtil.success(project);
	}
}

