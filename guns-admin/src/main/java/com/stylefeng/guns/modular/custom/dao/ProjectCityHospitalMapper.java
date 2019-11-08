package com.stylefeng.guns.modular.custom.dao;

import com.stylefeng.guns.modular.custom.model.ProjectCityHospital;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 城市医院表 Mapper 接口
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-06
 */
public interface ProjectCityHospitalMapper extends BaseMapper<ProjectCityHospital> {

	List<ProjectCityHospital> getHospitalListByCityid(@Param("projectid") Integer projectid,@Param("cityid") Integer cityid);

	void hospitalModify(@Param("pk") Integer pk,@Param("name") String name,@Param("value") String value);

	void deleteByCityid(@Param("cityid") Integer cityid);

}
