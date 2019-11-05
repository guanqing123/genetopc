package com.stylefeng.guns.modular.custom.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 项目表
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-04
 */
@TableName("gene_project")
public class Project extends Model<Project> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer projectid;
    /**
     * 项目名称
     */
    private String xmmc;
    /**
     * 适应症
     */
    private String syz;
    private String xmyy;
    /**
     * 项目状态
     */
    private Integer state;
    /**
     * 截止时间
     */
    private String jzsj;
    /**
     * 焦点
     */
    private Integer jd;
    /**
     * 缩略图key
     */
    @TableField("slt_key")
    private String sltKey;
    /**
     * 缩略图路径
     */
    @TableField("slt_path")
    private String sltPath;
    /**
     * 焦点图key
     */
    @TableField("jdt_key")
    private String jdtKey;
    /**
     * 焦点图路径
     */
    @TableField("jdt_path")
    private String jdtPath;
    /**
     * 项目介绍
     */
    private String xmjs;
    /**
     * 参加标准
     */
    private String cjbz;
    /**
     * base64图片
     */
    @TableField(exist = false)
    private String base64Data;

	public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public String getSyz() {
        return syz;
    }

    public void setSyz(String syz) {
        this.syz = syz;
    }

    public String getXmyy() {
        return xmyy;
    }

    public void setXmyy(String xmyy) {
        this.xmyy = xmyy;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getJzsj() {
        return jzsj;
    }

    public void setJzsj(String jzsj) {
        this.jzsj = jzsj;
    }

    public Integer getJd() {
        return jd;
    }

    public void setJd(Integer jd) {
        this.jd = jd;
    }

    public String getSltKey() {
        return sltKey;
    }

    public void setSltKey(String sltKey) {
        this.sltKey = sltKey;
    }

    public String getSltPath() {
        return sltPath;
    }

    public void setSltPath(String sltPath) {
        this.sltPath = sltPath;
    }

    public String getJdtKey() {
        return jdtKey;
    }

    public void setJdtKey(String jdtKey) {
        this.jdtKey = jdtKey;
    }

    public String getJdtPath() {
        return jdtPath;
    }

    public void setJdtPath(String jdtPath) {
        this.jdtPath = jdtPath;
    }

    public String getXmjs() {
        return xmjs;
    }

    public void setXmjs(String xmjs) {
        this.xmjs = xmjs;
    }

    public String getCjbz() {
        return cjbz;
    }

    public void setCjbz(String cjbz) {
        this.cjbz = cjbz;
    }
    
    public String getBase64Data() {
		return base64Data;
	}

	public void setBase64Data(String base64Data) {
		this.base64Data = base64Data;
	}

    @Override
    protected Serializable pkVal() {
        return this.projectid;
    }

    @Override
    public String toString() {
        return "Project{" +
        "projectid=" + projectid +
        ", xmmc=" + xmmc +
        ", syz=" + syz +
        ", xmyy=" + xmyy +
        ", state=" + state +
        ", jzsj=" + jzsj +
        ", jd=" + jd +
        ", sltKey=" + sltKey +
        ", sltPath=" + sltPath +
        ", jdtKey=" + jdtKey +
        ", jdtPath=" + jdtPath +
        ", xmjs=" + xmjs +
        ", cjbz=" + cjbz +
        "}";
    }
}
