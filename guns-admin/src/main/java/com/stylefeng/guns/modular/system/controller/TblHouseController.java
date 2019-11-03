package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.TblHouse;
import com.stylefeng.guns.modular.system.service.ITblHouseService;

/**
 * housemanager控制器
 *
 * @author fengshuonan
 * @Date 2018-06-27 17:38:16
 */
@Controller
@RequestMapping("/tblHouse")
public class TblHouseController extends BaseController {

    private String PREFIX = "/system/tblHouse/";

    @Autowired
    private ITblHouseService tblHouseService;

    /**
     * 跳转到housemanager首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tblHouse.html";
    }

    /**
     * 跳转到添加housemanager
     */
    @RequestMapping("/tblHouse_add")
    public String tblHouseAdd() {
        return PREFIX + "tblHouse_add.html";
    }

    /**
     * 跳转到修改housemanager
     */
    @RequestMapping("/tblHouse_update/{tblHouseId}")
    public String tblHouseUpdate(@PathVariable Integer tblHouseId, Model model) {
        TblHouse tblHouse = tblHouseService.selectById(tblHouseId);
        model.addAttribute("item",tblHouse);
        LogObjectHolder.me().set(tblHouse);
        return PREFIX + "tblHouse_edit.html";
    }

    /**
     * 获取housemanager列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	// 判断condition是否有值
    	if (ToolUtil.isEmpty(condition)) {
    		// 如果没有值,则表示查询全部
    		return tblHouseService.selectList(null);
    	}else{
    		// 如果有值,则认为是按业务名称进行模糊查询
    		EntityWrapper<TblHouse> entityWrapper = new EntityWrapper<>();
    		Wrapper<TblHouse> wrapper = entityWrapper.like("house_user", condition);
    		return tblHouseService.selectList(wrapper);
    	}
    }

    /**
     * 表单提交日期绑定
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
    	sdf.setLenient(false);
    	binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
    
    /**
     * 新增housemanager
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TblHouse tblHouse) {
        tblHouseService.insert(tblHouse);
        return SUCCESS_TIP;
    }

    /**
     * 删除housemanager
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer tblHouseId) {
        tblHouseService.deleteById(tblHouseId);
        return SUCCESS_TIP;
    }

    /**
     * 修改housemanager
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TblHouse tblHouse) {
        tblHouseService.updateById(tblHouse);
        return SUCCESS_TIP;
    }

    /**
     * housemanager详情
     */
    @RequestMapping(value = "/detail/{tblHouseId}")
    @ResponseBody
    public Object detail(@PathVariable("tblHouseId") Integer tblHouseId) {
        return tblHouseService.selectById(tblHouseId);
    }
}
