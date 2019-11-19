package com.stylefeng.guns.modular.custom.model;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;

import lombok.Data;

@Data
@ContentRowHeight(15)
@HeadRowHeight(20)
@ColumnWidth(20)
public class DownloadEnroll {
	 /**
     * 报名序号
     */
	@ExcelProperty("报名序号")
    private Integer enrollid;
	 /**
     * 项目序号
     */
	@ExcelProperty("项目序号")
    private Integer projectid;
    /**
     * 项目名称
     */
    @ExcelProperty("项目名称")
    private String  xmmc;
	/**
     * 姓名
     */
    @ExcelProperty("用户名")
    private String name;
    /**
     * 手机号
     */
    @ExcelProperty("手机号")
    private String telephone;
    /**
     * 性别
     */
    @ExcelProperty("性别")
    private String sex;
    /**
     * 年龄
     */
    @ExcelProperty("年龄")
    private Integer age;
    /**
     * 所患疾病
     */
    @ExcelProperty("所患疾病")
    private String disease;
    /**
     * 所在地区
     */
    @ExcelProperty("所在地区")
    private String address;
    /**
     * 详细地址
     */
    @ExcelProperty("详细地址")
    private String detailAddress;
    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String comment;
    /**
     * 状态:(0:等待审核; 1:已通过; 2:已被拒)
     */
    @ExcelProperty("状态")
    private String state;
    /**
     * 创建日期
     */
    @ExcelProperty("创建日期")
    private Date createDate;
    /**
     * 审核意见
     */
    @ExcelProperty("审核意见")
    private String checkComment;
    /**
     * 审核日期
     */
    @ExcelProperty("审核日期")
    private Date checkDate;
}
