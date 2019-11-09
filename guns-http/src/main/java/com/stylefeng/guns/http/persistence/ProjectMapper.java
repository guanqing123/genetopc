package com.stylefeng.guns.http.persistence;

import com.stylefeng.guns.http.model.Project;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

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

}
