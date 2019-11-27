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
import com.stylefeng.guns.modular.custom.model.BaseCity;
import com.stylefeng.guns.modular.custom.service.IBaseCityService;

/**
 * 城市列表控制器
 *
 * @author fengshuonan
 * @Date 2019-11-27 21:22:49
 */
@Controller
@RequestMapping("/baseCity")
public class BaseCityController extends BaseController {

    private String PREFIX = "/custom/baseCity/";

    @Autowired
    private IBaseCityService baseCityService;

    /**
     * 跳转到城市列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "baseCity.html";
    }

    /**
     * 跳转到添加城市列表
     */
    @RequestMapping("/baseCity_add")
    public String baseCityAdd() {
        return PREFIX + "baseCity_add.html";
    }

    /**
     * 跳转到修改城市列表
     */
    @RequestMapping("/baseCity_update/{baseCityId}")
    public String baseCityUpdate(@PathVariable Integer baseCityId, Model model) {
        BaseCity baseCity = baseCityService.selectById(baseCityId);
        model.addAttribute("item",baseCity);
        return PREFIX + "baseCity_edit.html";
    }

    /**
     * 获取城市列表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	Page<BaseCity> page = new PageFactory<BaseCity>().defaultPage();
    	List<BaseCity> result = baseCityService.getBaseCityListByCondition(page, condition);
    	page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 新增城市列表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BaseCity baseCity) {
        baseCityService.insert(baseCity);
        return SUCCESS_TIP;
    }

    /**
     * 删除城市列表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer baseCityId) {
        baseCityService.deleteById(baseCityId);
        return SUCCESS_TIP;
    }

    /**
     * 修改城市列表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BaseCity baseCity) {
        baseCityService.updateById(baseCity);
        return SUCCESS_TIP;
    }

    /**
     * 城市列表详情
     */
    @RequestMapping(value = "/detail/{baseCityId}")
    @ResponseBody
    public Object detail(@PathVariable("baseCityId") Integer baseCityId) {
        return baseCityService.selectById(baseCityId);
    }
}
