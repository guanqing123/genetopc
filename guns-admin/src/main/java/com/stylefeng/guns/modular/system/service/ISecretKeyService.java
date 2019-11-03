package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.modular.system.model.SecretKey;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 应用密钥表 服务类
 * </p>
 *
 * @author myc123
 * @since 2018-07-23
 */
public interface ISecretKeyService extends IService<SecretKey> {

	List<Map<String, Object>> selectSecretKeys(String appName);

	void add(SecretKey secretKey);

	void setStatus(String appKey, int i);

}
