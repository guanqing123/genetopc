package com.stylefeng.guns.http.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.domain.Result;
import com.stylefeng.guns.core.util.ResultUtil;
import com.stylefeng.guns.http.core.factory.PageFactory;
import com.stylefeng.guns.http.model.Enroll;
import com.stylefeng.guns.http.service.IEnrollService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 报名表 前端控制器
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-11
 */
@Api(value = "报名控制器", tags= {"报名相关接口"})
@Controller
@RequestMapping("/enroll")
public class EnrollController {

	@Autowired
	private IEnrollService enrollServiceImpl;
	
	@ApiOperation(value = "保存报名信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name="enroll", value="报名对象", required=true, paramType="query", dataType="int", defaultValue="0")
	})
	@PostMapping(value = "/saveEnroll")
	@ResponseBody
	public Result<Object> saveEnroll(Enroll enroll){
		enrollServiceImpl.saveEnroll(enroll);
		return ResultUtil.success();
	}
	
	@ApiOperation(value = "查看报名详情")
	@ApiImplicitParams({
		@ApiImplicitParam(name="enrollid", value="报名ID", required=true, paramType="query", dataType="int", defaultValue="0")
	})
	@PostMapping(value = "/detailEnroll")
	@ResponseBody
	public Result<Object> detailEnroll(@RequestParam Integer enrollid) {
		Enroll enroll = enrollServiceImpl.detailEnroll(enrollid);
		return ResultUtil.success(enroll);
	}
	
	@ApiOperation(value = "删除报名信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name="enrollid", value="报名ID", required=true, paramType="query", dataType="int", defaultValue="0")
	})
	@PostMapping(value = "/deleteEnroll")
	@ResponseBody
	public Result<Object> deleteEnroll(@RequestParam Integer enrollid) {
		enrollServiceImpl.deleteEnroll(enrollid);
		return ResultUtil.success();
	}
	
	@ApiOperation(value = "保存报名信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name="pageNum", value="当前页码", required=true, paramType="query", dataType="int", defaultValue="0"),
		@ApiImplicitParam(name="pageSize", value="每页条数", required=true, paramType="query", dataType="int", defaultValue="5"),
		@ApiImplicitParam(name="state", value="状态", required=true, paramType="query", dataType="int", defaultValue="0")
	})
	@GetMapping(value = "/getEnrollList")
	@ResponseBody
	public Result<Object> getEnrollList(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String state) {
		Page<Enroll> page = new PageFactory<Enroll>().defaultPage();
		List<Enroll> list = enrollServiceImpl.getEnrollList(page, state);
		page.setRecords(list);
		return ResultUtil.success(page);
	}
}

