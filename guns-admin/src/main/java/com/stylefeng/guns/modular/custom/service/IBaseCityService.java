package com.stylefeng.guns.modular.custom.service;

import com.stylefeng.guns.modular.custom.model.BaseCity;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 城市基础表 服务类
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-27
 */
public interface IBaseCityService extends IService<BaseCity> {

	List<BaseCity> getBaseCityListByCondition(Page<BaseCity> page, String condition);

}
