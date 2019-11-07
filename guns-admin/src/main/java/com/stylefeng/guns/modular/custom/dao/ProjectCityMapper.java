package com.stylefeng.guns.modular.custom.dao;

import com.stylefeng.guns.modular.custom.model.ProjectCity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 项目城市表 Mapper 接口
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-06
 */
public interface ProjectCityMapper extends BaseMapper<ProjectCity> {

	List<ProjectCity> getCityListByProjectid(@Param("projectid") String projectid);

	void cityModify(@Param("pk") Integer pk,@Param("name") String name,@Param("value") String value);

}
