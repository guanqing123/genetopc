package com.stylefeng.guns.modular.custom.dao;

import com.stylefeng.guns.modular.custom.model.BaseCity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 城市基础表 Mapper 接口
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-27
 */
public interface BaseCityMapper extends BaseMapper<BaseCity> {

	List<BaseCity> getBaseCityListByCondition(Page<BaseCity> page,@Param("condition") String condition);
}
