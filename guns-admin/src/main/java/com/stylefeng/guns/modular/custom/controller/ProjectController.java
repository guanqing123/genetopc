package com.stylefeng.guns.modular.custom.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.custom.model.Project;
import com.stylefeng.guns.modular.custom.service.IProjectService;

/**
 * 项目列表控制器
 *
 * @author fengshuonan
 * @Date 2019-11-04 13:26:39
 */
@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {

    private String PREFIX = "/custom/project/";

    @Autowired
    private IProjectService projectService;

    /**
     * 跳转到项目列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "project.html";
    }

    /**
     * 跳转到添加项目列表
     */
    @RequestMapping("/project_add")
    public String projectAdd() {
        return PREFIX + "project_add.html";
    }

    /**
     * 跳转到修改项目列表
     */
    @RequestMapping("/project_update/{projectId}")
    public String projectUpdate(@PathVariable Integer projectId, Model model) {
        Project project = projectService.selectById(projectId);
        model.addAttribute("item",project);
        LogObjectHolder.me().set(project);
        return PREFIX + "project_edit.html";
    }

    /**
     * 获取项目列表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return projectService.selectList(null);
    }

    /**
     * 新增项目列表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Project project) {
        projectService.insert(project);
        return SUCCESS_TIP;
    }

    /**
     * 删除项目列表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer projectId) {
        projectService.deleteById(projectId);
        return SUCCESS_TIP;
    }

    /**
     * 修改项目列表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Project project) {
        projectService.updateById(project);
        return SUCCESS_TIP;
    }

    /**
     * 项目列表详情
     */
    @RequestMapping(value = "/detail/{projectId}")
    @ResponseBody
    public Object detail(@PathVariable("projectId") Integer projectId) {
        return projectService.selectById(projectId);
    }
}
