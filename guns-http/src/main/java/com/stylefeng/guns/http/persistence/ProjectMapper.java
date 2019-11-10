package com.stylefeng.guns.http.persistence;

import com.stylefeng.guns.http.model.Project;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 项目表 Mapper 接口
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-09
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

	List<Project> getProjectListByCondition(Page<Project> page,@Param("condition") String condition);

	List<Map<String, Object>> getTopFiveSwiperList();
}
