package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.common.annotion.Permission;
import com.stylefeng.guns.core.common.constant.Const;
import com.stylefeng.guns.core.common.constant.dictmap.SecretKeyDict;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.SecretKey;
import com.stylefeng.guns.modular.system.service.ISecretKeyService;
import com.stylefeng.guns.modular.system.warpper.SecretKeyWarpper;

/**
 * 密钥管理控制器
 *
 * @author fengshuonan
 * @Date 2018-07-23 09:40:09
 */
@Controller
@RequestMapping("/secretKey")
public class SecretKeyController extends BaseController {

    private String PREFIX = "/system/secretKey/";

    @Autowired
    private ISecretKeyService secretKeyService;

    /**
     * 跳转到密钥管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "secretKey.html";
    }

    /**
     * 跳转到添加密钥管理
     */
    @RequestMapping("/secretKey_add")
    public String secretKeyAdd() {
        return PREFIX + "secretKey_add.html";
    }

    /**
     * 跳转到修改密钥管理
     */
    @RequestMapping("/secretKey_update/{appKey}")
    public String secretKeyUpdate(@PathVariable String appKey, Model model) {
        SecretKey secretKey = secretKeyService.selectById(appKey);
        model.addAttribute("item",secretKey);
        LogObjectHolder.me().set(secretKey);
        return PREFIX + "secretKey_edit.html";
    }

    /**
     * 获取密钥管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String appName) {
    	List<Map<String, Object>> keys = secretKeyService.selectSecretKeys(appName);
        return new SecretKeyWarpper(keys).warp();
    }

    /**
     * 新增密钥管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid SecretKey secretKey, BindingResult result) {
    	if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        secretKeyService.add(secretKey);
        return SUCCESS_TIP;
    }

    /**
     * 删除密钥管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String appKey) {
        secretKeyService.deleteById(appKey);
        return SUCCESS_TIP;
    }

    /**
     * 修改密钥管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SecretKey secretKey) {
        secretKeyService.updateById(secretKey);
        return SUCCESS_TIP;
    }

    /**
     * 密钥管理详情
     */
    @RequestMapping(value = "/detail/{appKey}")
    @ResponseBody
    public Object detail(@PathVariable("appKey") String appKey) {
        return secretKeyService.selectById(appKey);
    }
    
    /**
     * 冻结密钥
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "冻结密钥", key = "appKey", dict = SecretKeyDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip freeze(@RequestParam String appKey) {
    	if (ToolUtil.isEmpty(appKey)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
    	secretKeyService.setStatus(appKey, 2);
    	return SUCCESS_TIP;
    }
    
    /**
     * 解除冻结密钥
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "冻结密钥", key = "appKey", dict = SecretKeyDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip unfreeze(@RequestParam String appKey) {
    	if (ToolUtil.isEmpty(appKey)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
    	secretKeyService.setStatus(appKey, 1);
    	return SUCCESS_TIP;
    }
}
