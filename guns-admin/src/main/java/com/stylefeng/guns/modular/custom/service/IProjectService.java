package com.stylefeng.guns.modular.custom.service;

import com.stylefeng.guns.modular.custom.model.Project;

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

	List<Project> getProjectListByCondition(Page<Project> page, String condition);

}
