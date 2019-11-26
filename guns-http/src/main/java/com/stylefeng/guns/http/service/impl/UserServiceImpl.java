package com.stylefeng.guns.http.service.impl;

import com.stylefeng.guns.http.model.User;
import com.stylefeng.guns.http.persistence.UserMapper;
import com.stylefeng.guns.http.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
