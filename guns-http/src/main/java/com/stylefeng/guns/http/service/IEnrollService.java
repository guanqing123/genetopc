package com.stylefeng.guns.http.service;

import com.stylefeng.guns.http.model.Enroll;
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

	void saveEnroll(Enroll enroll);
}
