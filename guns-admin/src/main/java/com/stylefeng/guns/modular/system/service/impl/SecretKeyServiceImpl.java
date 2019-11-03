package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.modular.system.model.SecretKey;
import com.stylefeng.guns.modular.system.service.ISecretKeyService;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.SecretKeyMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 应用密钥表 服务实现类
 * </p>
 *
 * @author myc123
 * @since 2018-07-23
 */
@Service
public class SecretKeyServiceImpl extends ServiceImpl<SecretKeyMapper, SecretKey> implements ISecretKeyService {

	@Override
	public List<Map<String, Object>> selectSecretKeys(String appName) {
		return this.baseMapper.selectSecretKeys(appName);
	}

	@Override
	public void add(SecretKey secretKey) {
		Integer count = this.baseMapper.selectCount(new EntityWrapper<SecretKey>().eq("appName", secretKey.getAppName()));
		if (count > 0) {
			throw new GunsException(BizExceptionEnum.EXISTED_THE_APPNAME);
		}
		secretKey.setAppKey(MD5Util.encrypt16(secretKey.getAppName()));
		secretKey.setAppSecret(ToolUtil.getRandomString(32));
		secretKey.setStatus(1);
		secretKey.insert();
	}

	@Override
	public void setStatus(String appKey, int status) {
		this.baseMapper.setStatus(appKey, status);
	}

}
