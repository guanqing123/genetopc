package com.stylefeng.guns.modular.custom.service;

import com.stylefeng.guns.modular.custom.model.Enroll;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 报名表 服务类
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-15
 */
public interface IEnrollService extends IService<Enroll> {

	List<Enroll> listByCondition(Page<Enroll> page, String condition);

	Enroll enrollDetailById(Integer enrollId);
}
