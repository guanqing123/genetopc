package com.stylefeng.guns.modular.custom.dao;

import com.stylefeng.guns.modular.custom.model.Enroll;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 报名表 Mapper 接口
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-15
 */
public interface EnrollMapper extends BaseMapper<Enroll> {
	
	List<Enroll> listByCondition(Page<Enroll> page, String condition);

	Enroll enrollDetailById(@Param("enrollId") Integer enrollId);

	void check(@Param("enrollId") Integer enrollId,@Param("checkState") String checkState,@Param("checkComment") String checkComment);
}
