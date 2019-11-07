package com.stylefeng.guns.modular.custom.service.impl;

import com.stylefeng.guns.modular.custom.model.Project;
import com.stylefeng.guns.modular.custom.model.ProjectCity;
import com.stylefeng.guns.modular.custom.model.ProjectCityHospital;
import com.stylefeng.guns.modular.custom.dao.ProjectCityHospitalMapper;
import com.stylefeng.guns.modular.custom.dao.ProjectCityMapper;
import com.stylefeng.guns.modular.custom.dao.ProjectMapper;
import com.stylefeng.guns.modular.custom.service.IProjectService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-04
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {
	
	@Autowired
	private ProjectCityMapper projectCityMapper;
	
	@Autowired
	private ProjectCityHospitalMapper projectCityHospitalMapper;
	
	@Override
	public List<Project> getProjectListByCondition(Page<Project> page, String condition, String jd) {
		// TODO Auto-generated method stub
		return this.baseMapper.getProjectListByCondition(page, condition, jd);
	}

	@Override
	public void modifyState(Integer projectid, Integer state) {
		// TODO Auto-generated method stub
		this.baseMapper.modifyState(projectid, state);
	}

	@Override
	public List<ProjectCity> getCityListByProjectid(String projectid) {
		// TODO Auto-generated method stub
		return this.projectCityMapper.getCityListByProjectid(projectid);
	}

	@Override
	public void cityAdd(ProjectCity projectCity) {
		// TODO Auto-generated method stub
		this.projectCityMapper.insert(projectCity);
	}

	@Override
	public void cityModify(Integer pk, String name, String value) {
		// TODO Auto-generated method stub
		this.projectCityMapper.cityModify(pk, name, value);
	}

	@Override
	public List<ProjectCityHospital> getHospitalListByCityid(Integer projectid, Integer cityid) {
		// TODO Auto-generated method stub
		return this.projectCityHospitalMapper.getHospitalListByCityid(projectid, cityid);
	}

	@Override
	public void hospitalAdd(ProjectCityHospital cityHospital) {
		// TODO Auto-generated method stub
		this.projectCityHospitalMapper.insert(cityHospital);
	}

	@Override
	public void hospitalModify(Integer pk, String name, String value) {
		// TODO Auto-generated method stub
		this.projectCityHospitalMapper.hospitalModify(pk, name, value);
	}

	@Override
	public ProjectCity selectCityById(Integer cityId) {
		// TODO Auto-generated method stub
		return this.projectCityMapper.selectById(cityId);
	}
}
