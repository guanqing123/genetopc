package com.stylefeng.guns.modular.custom.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.modular.custom.model.Enroll;
import com.stylefeng.guns.modular.custom.service.IEnrollService;

/**
 * 报名列表控制器
 *
 * @author guanqing
 * @Date 2019-11-15 21:16:30
 */
@Controller
@RequestMapping("/enroll")
public class EnrollController extends BaseController {

    private String PREFIX = "/custom/enroll/";

    @Autowired
    private IEnrollService enrollService;

    /**
     * 跳转到报名列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "enroll.html";
    }

    /**
     * 跳转到详情审核
     */
    @RequestMapping("/enroll_check/{enrollId}")
    public String enrollCheck(@PathVariable Integer enrollId, Model model) {
        Enroll enroll = enrollService.enrollDetailById(enrollId);
        model.addAttribute("item", enroll);
        return PREFIX + "enroll_check.html";
    }

    /**
     * 获取报名列表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String state) {
    	Page<Enroll> page = new PageFactory<Enroll>().defaultPage();
    	List<Enroll> list = enrollService.listByCondition(page, condition, state);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 报名审核
     */
    @RequestMapping(value = "/check")
    @ResponseBody
    public Object check(@RequestParam Integer enrollid,@RequestParam String checkState,@RequestParam String checkComment) {
    	enrollService.check(enrollid, checkState, checkComment);
        return SUCCESS_TIP;
    }
}
