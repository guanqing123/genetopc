package com.stylefeng.guns.modular.custom.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.OssUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.custom.dao.BaseCityHospitalMapper;
import com.stylefeng.guns.modular.custom.dao.BaseCityMapper;
import com.stylefeng.guns.modular.custom.dao.EnrollMapper;
import com.stylefeng.guns.modular.custom.dao.ProjectCityHospitalMapper;
import com.stylefeng.guns.modular.custom.dao.ProjectCityMapper;
import com.stylefeng.guns.modular.custom.dao.ProjectMapper;
import com.stylefeng.guns.modular.custom.model.BaseCity;
import com.stylefeng.guns.modular.custom.model.BaseCityHospital;
import com.stylefeng.guns.modular.custom.model.Enroll;
import com.stylefeng.guns.modular.custom.model.Project;
import com.stylefeng.guns.modular.custom.model.ProjectCity;
import com.stylefeng.guns.modular.custom.model.ProjectCityHospital;
import com.stylefeng.guns.modular.custom.service.IProjectService;

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
	
	@Autowired
	private EnrollMapper enrollMapper;
	
	@Autowired
	private BaseCityMapper baseCityMapper;
	
	@Autowired
	private BaseCityHospitalMapper baseCityHospitalMapper;
	
	@Autowired
	private OssUtil ossUtil;
	
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

	@Override
	public void hospitalDelete(Integer hospitalid) {
		// TODO Auto-generated method stub
		this.projectCityHospitalMapper.deleteById(hospitalid);
	}

	@Transactional
	@Override
	public void cityDelete(Integer cityid) {
		// TODO Auto-generated method stub
		this.projectCityMapper.deleteById(cityid);
		this.projectCityHospitalMapper.deleteByCityid(cityid);
	}

	@Transactional
	@Override
	public Tip projectDelete(Integer projectId) {
		// TODO Auto-generated method stub
		EntityWrapper<Enroll> wrapper = new EntityWrapper<>();
		wrapper.eq("projectid", projectId);
		List<Enroll> list = this.enrollMapper.selectList(wrapper);
		if (ToolUtil.isNotEmpty(list))
			throw new GunsException(500, "该项目已有"+ list.size() + "条报名信息,无法删除");
		Project project = selectById(projectId);
		// 删除项目
		deleteById(project);
		// 删除城市
		EntityWrapper<ProjectCity> cityWrapper = new EntityWrapper<>();
		cityWrapper.eq("projectid", projectId);
		this.projectCityMapper.delete(cityWrapper);
		// 删除医院
		EntityWrapper<ProjectCityHospital> hospitalWrapper = new EntityWrapper<>();
		hospitalWrapper.eq("projectid", projectId);
		this.projectCityHospitalMapper.delete(hospitalWrapper);
		if (ToolUtil.isNotEmpty(project.getSltKey()))
			ossUtil.deleteObject(project.getSltKey());
		if (ToolUtil.isNotEmpty(project.getJdtKey()))
			ossUtil.deleteObject(project.getJdtKey());
		return new SuccessTip();
	}

	@Transactional
	@Override
	public void batchAddCityAndHospital(Integer projectId, String ids) {
		// TODO Auto-generated method stub
		String[] strs = Convert.toStrArray(",", ids);
		for (String str : strs) {
			Integer cityId = Integer.valueOf(str);
			if (cityId < 0)
				continue;
			// 查询基础城市
			BaseCity baseCity = this.baseCityMapper.selectById(cityId);
			
			// 构建项目城市对象
			ProjectCity city = new ProjectCity();
			BeanUtils.copyProperties(baseCity, city);
			city.setProjectid(projectId);
			
			// 保存项目城市
			this.projectCityMapper.insert(city);
			
			// 查询基础医院
			EntityWrapper<BaseCityHospital> wrapper = new EntityWrapper<>();
			wrapper.eq("cityid", cityId);
			List<BaseCityHospital> hospitals = this.baseCityHospitalMapper.selectList(wrapper);
			
			for (BaseCityHospital baseCityHospital : hospitals) {
				// 构建项目医院对象
				ProjectCityHospital cityHospital = new ProjectCityHospital();
				BeanUtils.copyProperties(baseCityHospital, cityHospital);
				cityHospital.setCityid(city.getCityid());
				cityHospital.setProjectid(projectId);
				
				// 保存项目医院
				this.projectCityHospitalMapper.insert(cityHospital);
			}
		}
	}
}
