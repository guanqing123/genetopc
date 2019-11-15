package com.stylefeng.guns.modular.custom.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;

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
     * 跳转到添加报名列表
     */
    @RequestMapping("/enroll_add")
    public String enrollAdd() {
        return PREFIX + "enroll_add.html";
    }

    /**
     * 跳转到修改报名列表
     */
    @RequestMapping("/enroll_update/{enrollId}")
    public String enrollUpdate(@PathVariable Integer enrollId, Model model) {
        Enroll enroll = enrollService.selectById(enrollId);
        model.addAttribute("item",enroll);
        LogObjectHolder.me().set(enroll);
        return PREFIX + "enroll_edit.html";
    }

    /**
     * 获取报名列表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	Page<Enroll> page = new PageFactory<Enroll>().defaultPage();
    	List<Enroll> list = enrollService.listByCondition(page, condition);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 新增报名列表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Enroll enroll) {
        enrollService.insert(enroll);
        return SUCCESS_TIP;
    }

    /**
     * 删除报名列表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer enrollId) {
        enrollService.deleteById(enrollId);
        return SUCCESS_TIP;
    }

    /**
     * 修改报名列表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Enroll enroll) {
        enrollService.updateById(enroll);
        return SUCCESS_TIP;
    }

    /**
     * 报名列表详情
     */
    @RequestMapping(value = "/detail/{enrollId}")
    @ResponseBody
    public Object detail(@PathVariable("enrollId") Integer enrollId) {
        return enrollService.selectById(enrollId);
    }
}
