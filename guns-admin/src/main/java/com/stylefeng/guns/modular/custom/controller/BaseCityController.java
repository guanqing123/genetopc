package com.stylefeng.guns.modular.custom.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.custom.model.BaseCity;
import com.stylefeng.guns.modular.custom.model.BaseCityHospital;
import com.stylefeng.guns.modular.custom.model.CityHospital;
import com.stylefeng.guns.modular.custom.service.IBaseCityHospitalService;
import com.stylefeng.guns.modular.custom.service.IBaseCityService;

/**
 * 城市列表控制器
 *
 * @author guanqing
 * @Date 2019-11-27 21:22:49
 */
@Controller
@RequestMapping("/baseCity")
public class BaseCityController extends BaseController {

    private String PREFIX = "/custom/baseCity/";

    @Autowired
    private IBaseCityService baseCityService;
    
    @Autowired
    private IBaseCityHospitalService baseCityHospitalService;

    /**
     * 跳转到城市列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "baseCity.html";
    }

    /**
     * 跳转到添加城市
     */
    @RequestMapping("/baseCity_add")
    public String baseCityAdd() {
        return PREFIX + "baseCity_add.html";
    }

    /**
     * 跳转到修改城市
     */
    @RequestMapping("/baseCity_update/{baseCityId}")
    public String baseCityUpdate(@PathVariable Integer baseCityId, Model model) {
        BaseCity baseCity = baseCityService.selectById(baseCityId);
        model.addAttribute("item",baseCity);
        return PREFIX + "baseCity_edit.html";
    }
    
    /**
     * 跳转到医院列表
     * @return
     */
    @RequestMapping("/baseCity_hospital/{baseCityId}")
    public String baseCityHospital(@PathVariable Integer baseCityId, Model model) {
    	model.addAttribute("baseCityId", baseCityId);
    	return PREFIX + "baseCity_hospital.html";
    }
    
    /**
     * 跳转到添加医院页面
     * @return
     */
    @RequestMapping("/baseCity_hospitalAdd/{baseCityId}")
    public String baseCityHospitalAdd(@PathVariable Integer baseCityId, Model model) {
    	model.addAttribute("baseCityId", baseCityId);
    	return PREFIX + "baseCity_hospitalAdd.html";
    }
    
    /**
     * 跳转到编辑医院页面
     * @return
     */
    @RequestMapping("/baseCity_hospitalUpdate/{hospitalid}")
    public String baseCityHospitalUpdate(@PathVariable Integer hospitalid, Model model) {
    	BaseCityHospital hospital = baseCityHospitalService.selectById(hospitalid);
    	model.addAttribute("hospital", hospital);
    	return PREFIX + "baseCity_hospitalEdit.html";
    }
    
    /**
     * 添加医院
     * @param baseCityHospital
     * @return
     */
    @RequestMapping(value = "/hospital/add")
    @ResponseBody
    public Object hospitalAdd(BaseCityHospital baseCityHospital) {
    	baseCityHospitalService.insert(baseCityHospital);
    	return SUCCESS_TIP;
    }
    
    /**
     * 编辑医院
     * @param baseCityHospital
     * @return
     */
    @RequestMapping(value = "/hospital/update")
    @ResponseBody
    public Object hospitalUpdate(BaseCityHospital baseCityHospital) {
    	baseCityHospitalService.updateById(baseCityHospital);
    	return SUCCESS_TIP;
    }
    
    /**
     * 删除医院
     * @param hospitalId
     * @return
     */
    @RequestMapping(value = "/hospital/delete")
    @ResponseBody
    public Object hospitalDelete(@RequestParam Integer hospitalId) {
    	baseCityHospitalService.deleteById(hospitalId);
    	return SUCCESS_TIP;
    }
    
    /**
     * 获取医院列表
     * @return
     */
    @RequestMapping(value = "/hospital/list")
    @ResponseBody
    public Object hospitalList(Integer baseCityId, String condition) {
    	Page<BaseCityHospital> page = new PageFactory<BaseCityHospital>().defaultPage();
    	List<BaseCityHospital> result = baseCityHospitalService.getBaseCityHospitalListByCondition(page, baseCityId, condition);
    	page.setRecords(result);
    	return super.packForBT(page);
    }

    /**
     * 获取城市列表
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
        baseCityService.deleteCityAndHospitals(baseCityId);
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
    
    /**
     * 地区树
     * @return
     */
    @RequestMapping(value = "/cityTree")
    @ResponseBody
    public Object cityTree() {
    	List<Map<String, Object>> results = baseCityHospitalService.cityTree();
    	return results;
    }
}
