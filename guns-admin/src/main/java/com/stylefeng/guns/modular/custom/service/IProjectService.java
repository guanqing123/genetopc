package com.stylefeng.guns.modular.custom.service;

import com.stylefeng.guns.modular.custom.model.Project;
import com.stylefeng.guns.modular.custom.model.ProjectCity;
import com.stylefeng.guns.modular.custom.model.ProjectCityHospital;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-04
 */
public interface IProjectService extends IService<Project> {

	List<Project> getProjectListByCondition(Page<Project> page, String condition, String jd);

	void modifyState(Integer projectid, Integer state);

	List<ProjectCity> getCityListByProjectid(String projectid);

	void cityAdd(ProjectCity projectCity);

	void cityModify(Integer pk, String name, String value);

	List<ProjectCityHospital> getHospitalListByCityid(Integer projectid, Integer cityid);

	void hospitalAdd(ProjectCityHospital cityHospital);

	void hospitalModify(Integer pk, String name, String value);

	ProjectCity selectCityById(Integer cityId);

	void hospitalDelete(Integer hospitalid);

	void cityDelete(Integer cityid);
}
