package com.stylefeng.guns.http.persistence;

import com.stylefeng.guns.http.model.Enroll;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 报名表 Mapper 接口
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-11
 */
@Mapper
public interface EnrollMapper extends BaseMapper<Enroll> {

	List<Enroll> getEnrollList(Page<Enroll> page,@Param("state") String state);
}
