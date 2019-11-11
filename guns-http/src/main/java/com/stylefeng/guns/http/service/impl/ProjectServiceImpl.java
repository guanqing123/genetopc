package com.stylefeng.guns.http.service.impl;

import com.stylefeng.guns.http.model.Project;
import com.stylefeng.guns.http.persistence.ProjectMapper;
import com.stylefeng.guns.http.service.IProjectService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-09
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

	@Override
	public List<Project> getProjectListByCondition(Page<Project> page, String condition) {
		// TODO Auto-generated method stub
		return this.baseMapper.getProjectListByCondition(page, condition);
	}

	@Override
	public List<Map<String, Object>> getTopFiveSwiperList() {
		// TODO Auto-generated method stub
		return this.baseMapper.getTopFiveSwiperList();
	}

	@Override
	public Project getProjectDetail(Integer projectid) {
		// TODO Auto-generated method stub
		return this.baseMapper.getProjectDetail(projectid);
	}
}
