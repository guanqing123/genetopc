package com.stylefeng.guns.http.web;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.core.domain.Result;
import com.stylefeng.guns.core.util.ResultUtil;
import com.stylefeng.guns.http.model.Enroll;
import com.stylefeng.guns.http.service.IEnrollService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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
}

