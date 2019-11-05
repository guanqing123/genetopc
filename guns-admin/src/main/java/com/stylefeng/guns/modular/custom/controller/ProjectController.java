package com.stylefeng.guns.modular.custom.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.common.exception.FileUploadException;
import com.stylefeng.guns.core.domain.FilePath;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.OssUtil;
import com.stylefeng.guns.core.util.ToolUtil;
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
    
    @Autowired
    private OssUtil ossUtil;

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
        Page<Project> page = new PageFactory<Project>().defaultPage();
        List<Project> list = projectService.getProjectListByCondition(page, condition);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 新增项目列表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Project project) {
    	String dataPrix = "";
    	String data = "";
    	if (ToolUtil.isEmpty(project.getBase64Data())) {
    		throw new GunsException(BizExceptionEnum.FILE_EMPTY_ERROR);
    	} else {
    		String[] d = project.getBase64Data().split("base64,");
    		if (d != null && d.length == 2) {
    			dataPrix = d[0];
    			data = d[1];
    		} else {
    			throw new GunsException(BizExceptionEnum.FILE_WRONGFUL_ERROR);
    		}
    	}
    	String suffix = "";
    	if ("data:image/jpeg;".equalsIgnoreCase(dataPrix)) {
    		suffix = ".jpg";
    	} else if ("data:image/x-icon;".equalsIgnoreCase(dataPrix)) {
    		suffix = ".ico";
    	} else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) {
    		suffix = ".gif";
    	} else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {
    		suffix = ".png";
    	} else {
    		throw new GunsException(BizExceptionEnum.FILE_FORMAT_ERROR);
    	}    	
    	// 因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
    	byte[] bs = Base64Utils.decodeFromString(data);
    	
    	FilePath path = ossUtil.transferTo(bs, suffix);
    	try {
			project.setSltKey(path.getFileKey());
			project.setSltPath(path.getFileRealPath());
			project.insert();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileUploadException(500, e.getMessage(), path);
		}
    	return SUCCESS_TIP;
    }
    
	@SuppressWarnings("unused")
	private void saveFileToLocal(String base64Data) {
    	String dataPrix = "";
    	String data = "";
    	if (ToolUtil.isEmpty(base64Data)) {
    		throw new GunsException(BizExceptionEnum.FILE_EMPTY_ERROR);
    	} else {
    		String[] d = base64Data.split("base64,");
    		if (d != null && d.length == 2) {
    			dataPrix = d[0];
    			data = d[1];
    		} else {
    			throw new GunsException(BizExceptionEnum.FILE_WRONGFUL_ERROR);
    		}
    	}
    	String suffix = "";
    	if ("data:image/jpeg;".equalsIgnoreCase(dataPrix)) {
    		suffix = ".jpg";
    	} else if ("data:image/x-icon;".equalsIgnoreCase(dataPrix)) {
    		suffix = ".ico";
    	} else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) {
    		suffix = ".gif";
    	} else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {
    		suffix = ".png";
    	} else {
    		throw new GunsException(BizExceptionEnum.FILE_FORMAT_ERROR);
    	}
    	
    	String tempFileName = UUID.randomUUID().toString() + suffix;
    	
    	// 因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
    	byte[] bs = Base64Utils.decodeFromString(data);
    	
    	try {
    		//使用apache提供的工具类操作流
    		System.out.println(HttpKit.getRequest().getServletContext().getRealPath("/upload"));
			FileUtils.writeByteArrayToFile(new File(HttpKit.getRequest().getServletContext().getRealPath("/upload"), tempFileName), bs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new GunsException(BizExceptionEnum.FILE_WRITE_ERROR);
		}
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
