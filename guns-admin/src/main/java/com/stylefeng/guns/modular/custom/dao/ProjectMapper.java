package com.stylefeng.guns.modular.custom.dao;

import com.stylefeng.guns.modular.custom.model.Project;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 项目表 Mapper 接口
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-04
 */
public interface ProjectMapper extends BaseMapper<Project> {

	List<Project> getProjectListByCondition(Page<Project> page,@Param("condition") String condition);

}
