package com.stylefeng.guns.http.persistence;

import com.stylefeng.guns.http.model.Enroll;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

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

}
