package com.stylefeng.guns.http.service.impl;

import com.stylefeng.guns.http.model.Project;
import com.stylefeng.guns.http.persistence.ProjectMapper;
import com.stylefeng.guns.http.service.IProjectService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-09
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

}
