package com.stylefeng.guns.http.service;

import com.stylefeng.guns.http.model.Project;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-09
 */
public interface IProjectService extends IService<Project> {

	List<Project> getProjectListByCondition(Page<Project> page, String condition);

	List<Map<String, Object>> getTopFiveSwiperList();

}
