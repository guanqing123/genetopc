package com.stylefeng.guns.modular.custom.service;

import com.stylefeng.guns.modular.custom.model.BaseCityHospital;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 医院基础表 服务类
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-28
 */
public interface IBaseCityHospitalService extends IService<BaseCityHospital> {

	List<BaseCityHospital> getBaseCityHospitalListByCondition(Page<BaseCityHospital> page, Integer baseCityId,
			String condition);

	List<Map<String, Object>> cityTree();
}
