package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.SecretKey;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 应用密钥表 Mapper 接口
 * </p>
 *
 * @author myc123
 * @since 2018-07-23
 */
public interface SecretKeyMapper extends BaseMapper<SecretKey> {

	List<Map<String, Object>> selectSecretKeys(@Param("appName")String appName);

	void setStatus(@Param("appKey")String appKey, @Param("status")int status);

}
