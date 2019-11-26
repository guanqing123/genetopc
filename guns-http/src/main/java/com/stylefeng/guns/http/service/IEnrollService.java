package com.stylefeng.guns.http.service;

import com.stylefeng.guns.core.domain.Result;
import com.stylefeng.guns.http.model.Enroll;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 报名表 服务类
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-11
 */
public interface IEnrollService extends IService<Enroll> {

	Result<Object> saveEnroll(Enroll enroll);

	List<Enroll> getEnrollList(Page<Enroll> page, String openId, String state);

	void deleteEnroll(Integer enrollid);

	Enroll detailEnroll(Integer enrollid);

	void modifyEnroll(Enroll enroll);
}
