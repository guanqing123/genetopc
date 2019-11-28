package com.stylefeng.guns.modular.custom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.custom.dao.BaseCityHospitalMapper;
import com.stylefeng.guns.modular.custom.dao.BaseCityMapper;
import com.stylefeng.guns.modular.custom.model.BaseCity;
import com.stylefeng.guns.modular.custom.model.BaseCityHospital;
import com.stylefeng.guns.modular.custom.service.IBaseCityService;

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

	@Autowired
	private BaseCityHospitalMapper baseCityHospitalMapper;
	
	@Override
	public List<BaseCity> getBaseCityListByCondition(Page<BaseCity> page, String condition) {
		// TODO Auto-generated method stub
		return this.baseMapper.getBaseCityListByCondition(page, condition);
	}

	@Transactional
	@Override
	public void deleteCityAndHospitals(Integer baseCityId) {
		// TODO Auto-generated method stub
		EntityWrapper<BaseCityHospital> wrapper = new EntityWrapper<>();
		wrapper.eq("cityid", baseCityId);
		baseCityHospitalMapper.delete(wrapper);
		this.baseMapper.deleteById(baseCityId);
	}
}
