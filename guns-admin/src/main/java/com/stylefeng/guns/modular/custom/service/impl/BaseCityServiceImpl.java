package com.stylefeng.guns.modular.custom.service.impl;

import com.stylefeng.guns.modular.custom.model.BaseCity;
import com.stylefeng.guns.modular.custom.dao.BaseCityMapper;
import com.stylefeng.guns.modular.custom.service.IBaseCityService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 城市基础表 服务实现类
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-27
 */
@Service
public class BaseCityServiceImpl extends ServiceImpl<BaseCityMapper, BaseCity> implements IBaseCityService {

	@Override
	public List<BaseCity> getBaseCityListByCondition(Page<BaseCity> page, String condition) {
		// TODO Auto-generated method stub
		return this.baseMapper.getBaseCityListByCondition(page, condition);
	}

}
