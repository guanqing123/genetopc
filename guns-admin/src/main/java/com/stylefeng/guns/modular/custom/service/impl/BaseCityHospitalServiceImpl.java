package com.stylefeng.guns.modular.custom.service.impl;

import com.stylefeng.guns.modular.custom.model.BaseCityHospital;
import com.stylefeng.guns.modular.custom.dao.BaseCityHospitalMapper;
import com.stylefeng.guns.modular.custom.service.IBaseCityHospitalService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 医院基础表 服务实现类
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-28
 */
@Service
public class BaseCityHospitalServiceImpl extends ServiceImpl<BaseCityHospitalMapper, BaseCityHospital> implements IBaseCityHospitalService {

	@Override
	public List<BaseCityHospital> getBaseCityHospitalListByCondition(Page<BaseCityHospital> page, Integer baseCityId,
			String condition) {
		// TODO Auto-generated method stub
		return this.baseMapper.getBaseCityHospitalListByCondition(page, baseCityId, condition);
	}

}
