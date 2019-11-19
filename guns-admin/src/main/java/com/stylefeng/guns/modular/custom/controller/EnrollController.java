package com.stylefeng.guns.modular.custom.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.custom.model.DownloadEnroll;
import com.stylefeng.guns.modular.custom.model.Enroll;
import com.stylefeng.guns.modular.custom.model.ImageData;
import com.stylefeng.guns.modular.custom.service.IEnrollService;

/**
 * 报名列表控制器
 *
 * @author guanqing
 * @Date 2019-11-15 21:16:30
 */
@Controller
@RequestMapping("/enroll")
public class EnrollController extends BaseController {

    private String PREFIX = "/custom/enroll/";

    @Autowired
    private IEnrollService enrollService;

    /**
     * 跳转到报名列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "enroll.html";
    }

    /**
     * 跳转到详情审核
     */
    @RequestMapping("/enroll_check/{enrollId}")
    public String enrollCheck(@PathVariable Integer enrollId, Model model) {
        Enroll enroll = enrollService.enrollDetailById(enrollId);
        model.addAttribute("item", enroll);
        return PREFIX + "enroll_check.html";
    }

    /**
     * 获取报名列表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String state) {
    	Page<Enroll> page = new PageFactory<Enroll>().defaultPage();
    	List<Enroll> list = enrollService.listByCondition(page, condition, state);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 报名审核
     */
    @RequestMapping(value = "/check")
    @ResponseBody
    public Object check(@RequestParam Integer enrollid,@RequestParam String checkState,@RequestParam String checkComment) {
    	enrollService.check(enrollid, checkState, checkComment);
        return SUCCESS_TIP;
    }
    
    /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DownloadData}
     * <p>
     * 2. 设置返回的 参数
     * <p>
     * 3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @RequestMapping(value = "/export")
    public void export(@RequestParam String condition,@RequestParam String state,HttpServletResponse response) {
    	List<DownloadEnroll> list = enrollService.downloadEnroll(condition, state);
    	// 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用 postman
    	response.setContentType("application/vnd.ms-excel;charset=utf-8");
    	response.setCharacterEncoding("UTF-8");
    	// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
    	String fileName = null;
		try {
			fileName = URLEncoder.encode("报名表", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	response.setHeader("Content-Disposition", "Attachment;Filename="+ fileName+".xls");
    	try {
			EasyExcel.write(response.getOutputStream(), DownloadEnroll.class).sheet(1).doWrite(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 重复多次写入
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ComplexHeadData}
     * <p>
     * 2. 使用{@link ExcelProperty}注解指定复杂的头
     * <p>
     * 3. 直接调用二次写入即可
     */
    @RequestMapping(value = "/exportOne")
    public void exportOne(@RequestParam Integer enrollid,HttpServletResponse response) {
    	// 生成报名对象
    	DownloadEnroll downloadEnroll = enrollService.downloadEnroll(enrollid);
    	List<DownloadEnroll> list = new ArrayList<>();
    	list.add(downloadEnroll);
    	// 生成附件
    	List<ImageData> images = enrollService.downloadEnrollImage(enrollid);
    	this.writeOneSheet(response, list, images);
    }
    
    private void writeOneSheet(HttpServletResponse response, List<DownloadEnroll> list, List<ImageData> images) {
    	// 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用 postman
    	response.setContentType("application/vnd.ms-excel;charset=utf-8");
    	response.setCharacterEncoding("UTF-8");
    	
    	// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
    	String fileName = null;
		try {
			fileName = URLEncoder.encode("报名表", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setHeader("Content-Disposition", "Attachment;Filename="+ fileName+".xls");
		// 这里 需要指定写用哪个class去写
		ExcelWriter excelWriter = null;
		try {
			excelWriter = EasyExcel.write(response.getOutputStream(), DownloadEnroll.class).build();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// 把sheet设置为不需要头 不然会输出sheet的头 这样看起来第一个table 就有2个头了
	     WriteSheet writeSheet = EasyExcel.writerSheet("报名表").needHead(Boolean.FALSE).build();
	     
	     // 这里必须指定需要头，table 会继承sheet的配置，sheet配置了不需要，table 默认也是不需要
	     WriteTable writeTable0 = EasyExcel.writerTable(0).needHead(Boolean.TRUE).build();
	     writeTable0.setClazz(DownloadEnroll.class);
	     // 第一次写入会创建头
	     excelWriter.write(list, writeSheet, writeTable0);
	     
	     WriteTable writeTable1 = EasyExcel.writerTable(1).needHead(Boolean.TRUE).build();
	     writeTable1.setClazz(ImageData.class);
	     // 第二次写如也会创建头，然后在第一次的后面写入数据
	     excelWriter.write(images, writeSheet, writeTable1);
	     
	     /// 千万别忘记finish 会帮忙关闭流
	     excelWriter.finish();
    }
    
    /**
     * 生成 两个sheet
     * @param response
     */
    @SuppressWarnings("unused")
	private void writeTwoSheets(HttpServletResponse response, List<DownloadEnroll> list, List<ImageData> images) {
    	// 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用 postman
    	response.setContentType("application/vnd.ms-excel;charset=utf-8");
    	response.setCharacterEncoding("UTF-8");
    	
    	// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
    	String fileName = null;
		try {
			fileName = URLEncoder.encode("报名表", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setHeader("Content-Disposition", "Attachment;Filename="+ fileName+".xls");
		// 这里 需要指定写用哪个class去写
		ExcelWriter excelWriter = null;
		try {
			excelWriter = EasyExcel.write(response.getOutputStream(), DownloadEnroll.class).build();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// 这里注意 如果同一个sheet只要创建一次
		WriteSheet writeSheet = EasyExcel.writerSheet(0, "报名信息").build();
		writeSheet.setClazz(DownloadEnroll.class);
		excelWriter.write(list, writeSheet);
		
		// 生成图片
		WriteSheet imageSheet = EasyExcel.writerSheet(1, "附件信息").build();
		imageSheet.setClazz(ImageData.class);
		excelWriter.write(images, imageSheet);
		
		excelWriter.finish();
    }
}
