package com.stylefeng.guns.modular.custom.dao;

import com.stylefeng.guns.modular.custom.model.BaseCityHospital;
import com.stylefeng.guns.modular.custom.model.CityHospital;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 医院基础表 Mapper 接口
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-28
 */
public interface BaseCityHospitalMapper extends BaseMapper<BaseCityHospital> {

	List<BaseCityHospital> getBaseCityHospitalListByCondition(Page<BaseCityHospital> page,@Param("baseCityId") Integer baseCityId,
			@Param("condition") String condition);

	List<CityHospital> cityTree();
}
