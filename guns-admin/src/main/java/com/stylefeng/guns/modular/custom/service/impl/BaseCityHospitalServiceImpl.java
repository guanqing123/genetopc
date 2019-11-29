package com.stylefeng.guns.modular.custom.service.impl;

import com.stylefeng.guns.modular.custom.model.BaseCityHospital;
import com.stylefeng.guns.modular.custom.model.CityHospital;
import com.stylefeng.guns.modular.custom.model.CityHospital.City;
import com.stylefeng.guns.modular.custom.dao.BaseCityHospitalMapper;
import com.stylefeng.guns.modular.custom.service.IBaseCityHospitalService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public List<Map<String, Object>> cityTree() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		List<CityHospital> cityHospitals = this.baseMapper.cityTree();
		int i = -1;
		for (CityHospital cityHospital : cityHospitals) {
			Map<String, Object> premap = new HashMap<String, Object>();
			String id = String.valueOf(i);
			premap.put("checked", false);
			premap.put("id", id);
			premap.put("isOpen", false);
			premap.put("name", cityHospital.getProvinceName());
			premap.put("open", false);
			premap.put("pId", "0");
			results.add(premap);
			for (City city : cityHospital.getChildren()) {
				Map<String, Object> sonmap = new HashMap<>();
				sonmap.put("checked", false);
				sonmap.put("id", city.getCityid());
				sonmap.put("isOpen", false);
				sonmap.put("name", city.getCityName());
				sonmap.put("open", false);
				sonmap.put("pId", id);
				results.add(sonmap);
			}
			i--;
		}
		return results;
	}
}
