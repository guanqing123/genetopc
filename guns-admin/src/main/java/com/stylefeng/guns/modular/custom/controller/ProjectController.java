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
import com.stylefeng.guns.core.common.annotion.Permission;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.domain.FilePath;
import com.stylefeng.guns.core.exception.FileUploadException;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.OssUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.custom.model.Project;
import com.stylefeng.guns.modular.custom.model.ProjectCity;
import com.stylefeng.guns.modular.custom.model.ProjectCityHospital;
import com.stylefeng.guns.modular.custom.service.IProjectService;

/**
 * 项目列表控制器
 *
 * @author guanqing
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
        return PREFIX + "project_edit.html";
    }
    
    /**
     * 跳转项目医院
     */
    @RequestMapping("/project_hospital/{projectId}")
    public String projectHospital(@PathVariable Integer projectId, Model model) {
    	model.addAttribute("projectid", projectId);
    	return PREFIX + "project_hospital.html";
    }
    
    /**
     * 跳转添加城市页面
     * @return
     */
    @RequestMapping("/project_cityadd/{projectId}")
    public String projectCityAdd(@PathVariable Integer projectId, Model model) {
    	Project project = projectService.selectById(projectId);
    	model.addAttribute("item", project);
    	return PREFIX + "project_cityadd.html";
    }
    
    /**
     * 跳转添加医院页面
     * @return
     */
    @RequestMapping("/project_hospitaladd/{cityId}")
    public String projectHospitalAdd(@PathVariable Integer cityId, Model model) {
    	ProjectCity city = projectService.selectCityById(cityId);
    	model.addAttribute("item", city);
    	return PREFIX + "project_hospitaladd.html";
    }
    
    /**
     * 跳转到修改焦点图
     */
    @RequestMapping("/project_focus/{projectId}")
    public String projectFocus(@PathVariable Integer projectId, Model model) {
    	Project project = projectService.selectById(projectId);
    	model.addAttribute("item", project);
    	return PREFIX + "project_focus.html";
    }

    /**
     * 获取项目列表列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String jd) {
        Page<Project> page = new PageFactory<Project>().defaultPage();
        List<Project> list = projectService.getProjectListByCondition(page, condition, jd);
        page.setRecords(list);
        return super.packForBT(page);
    }
    
    /**
     * 获取城市列表
     * @param projectid
     * @return
     */
    @RequestMapping(value = "/cityList")
    @ResponseBody
    public Object cityList(String projectid) {
    	List<ProjectCity> result = projectService.getCityListByProjectid(projectid);
    	return result;
    }
    
    /**
     * 获取医院列表
     * @param cityid
     * @return
     */
    @RequestMapping(value = "/hospitalList")
    @ResponseBody
    public Object hospitalList(Integer projectid,Integer cityid) {
    	List<ProjectCityHospital> result = projectService.getHospitalListByCityid(projectid, cityid);
    	return result;
    }
    
    /**
     * 新增医院
     * @param cityHospital
     * @return
     */
    @RequestMapping(value = "/hospitalAdd")
    @ResponseBody
    public Object hospitalAdd(ProjectCityHospital cityHospital) {
    	projectService.hospitalAdd(cityHospital);
    	return SUCCESS_TIP;
    }
    
    /**
     * 修改医院
     */
    @RequestMapping(value = "/hospitalModify")
    @ResponseBody
    public Object hospitalModify(@RequestParam Integer pk, @RequestParam String name, @RequestParam String value) {
    	projectService.hospitalModify(pk, name, value);
    	return SUCCESS_TIP;
    }
    
    /**
     * 删除医院
     * @return
     */
    @RequestMapping(value = "/hospitalDelete")
    @ResponseBody
    public Object hospitalDelete(@RequestParam Integer hospitalid) {
    	projectService.hospitalDelete(hospitalid);
    	return SUCCESS_TIP;
    }
    
    /**
     * 新增城市
     */
    @RequestMapping(value = "/cityAdd")
    @ResponseBody
    public Object cityAdd(ProjectCity projectCity) {
    	projectService.cityAdd(projectCity);
    	return SUCCESS_TIP;
    }
    
    /**
     * 修改城市
     */
    @RequestMapping(value = "/cityModify")
    @ResponseBody
    public Object cityModify(@RequestParam Integer pk, @RequestParam String name, @RequestParam String value) {
    	projectService.cityModify(pk, name, value);
    	return SUCCESS_TIP;
    }
    
    /**
     * 删除城市
     */
    @RequestMapping(value = "/cityDelete")
    @ResponseBody
    public Object cityDelete(@RequestParam Integer cityid) {
    	projectService.cityDelete(cityid);
    	return SUCCESS_TIP;
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
    
    /**
     * 保存图片到本地
     * @param base64Data
     */
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
	 * 修改项目状态
	 * @param projectid
	 * @param state
	 * @return
	 */
	@RequestMapping(value = "/modifyState")
	@ResponseBody
	public Object modifyState(@RequestParam Integer projectid, @RequestParam Integer state) {
		projectService.modifyState(projectid, state);
		return SUCCESS_TIP;
	}

    /**
     * 修改项目列表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Project project) {
    	// 修改图片
    	if (ToolUtil.contains(project.getBase64Data(), "base64")) {
    		// 获取原先项目的缩略图
    		Project oldProject = projectService.selectById(project.getProjectid());
    		String dataPrix = "";
    		String data = "";
    		String[] d = project.getBase64Data().split("base64,");
    		if (d != null && d.length == 2) {
    			dataPrix = d[0];
    			data = d[1];
    		} else {
    			throw new GunsException(BizExceptionEnum.FILE_WRONGFUL_ERROR);
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
    			projectService.updateById(project);
    		} catch (Exception e) {
    			e.printStackTrace();
    			throw new FileUploadException(500, e.getMessage(), path);
    		}
        	ossUtil.deleteObject(oldProject.getSltKey());
        	return SUCCESS_TIP;
    	} else {
    		projectService.updateById(project);
    		return SUCCESS_TIP;
    	}
    }
    
    /**
     * 设置焦点图
     * @return
     */
    @RequestMapping(value = "/focus")
    @ResponseBody
    public Object focus(Project project) {
    	// 替换焦点图
    	if (project.getJd() == 1 && ToolUtil.contains(project.getBase64Data(), "base64")) {
    		// 获取原先项目的缩略图
    		Project oldProject = projectService.selectById(project.getProjectid());
    		String dataPrix = "";
    		String data = "";
    		String[] d = project.getBase64Data().split("base64,");
    		if (d != null && d.length == 2) {
    			dataPrix = d[0];
    			data = d[1];
    		} else {
    			throw new GunsException(BizExceptionEnum.FILE_WRONGFUL_ERROR);
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
    			project.setJdtKey(path.getFileKey());
    			project.setJdtPath(path.getFileRealPath());
    			projectService.updateById(project);
    		} catch (Exception e) {
    			e.printStackTrace();
    			throw new FileUploadException(500, e.getMessage(), path);
    		}
        	if (ToolUtil.isNotEmpty(oldProject.getJdtKey()))
        		ossUtil.deleteObject(oldProject.getJdtKey());
        	return SUCCESS_TIP;
    	} else {
    		projectService.updateById(project);
    		return SUCCESS_TIP;
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
     * 项目列表详情
     */
    @RequestMapping(value = "/detail/{projectId}")
    @ResponseBody
    public Object detail(@PathVariable("projectId") Integer projectId) {
        return projectService.selectById(projectId);
    }
}
