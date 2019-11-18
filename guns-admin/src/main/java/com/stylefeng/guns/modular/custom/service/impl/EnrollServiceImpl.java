package com.stylefeng.guns.modular.custom.service.impl;

import com.stylefeng.guns.modular.custom.model.Enroll;
import com.stylefeng.guns.modular.custom.dao.EnrollMapper;
import com.stylefeng.guns.modular.custom.service.IEnrollService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 报名表 服务实现类
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-15
 */
@Service
public class EnrollServiceImpl extends ServiceImpl<EnrollMapper, Enroll> implements IEnrollService {

	@Override
	public List<Enroll> listByCondition(Page<Enroll> page, String condition) {
		// TODO Auto-generated method stub
		return this.baseMapper.listByCondition(page, condition);
	}

	@Override
	public Enroll enrollDetailById(Integer enrollId) {
		// TODO Auto-generated method stub
		return this.baseMapper.enrollDetailById(enrollId);
	}

	@Override
	public void check(Integer enrollId, String checkState, String checkComment) {
		// TODO Auto-generated method stub
		this.baseMapper.check(enrollId, checkState, checkComment);
	}
	
}