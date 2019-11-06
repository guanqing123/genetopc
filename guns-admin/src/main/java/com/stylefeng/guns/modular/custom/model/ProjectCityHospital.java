package com.stylefeng.guns.modular.custom.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 城市医院表
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-06
 */
@TableName("gene_project_city_hospital")
public class ProjectCityHospital extends Model<ProjectCityHospital> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "hospitalid", type = IdType.AUTO)
    private Integer hospitalid;
    /**
     * 城市id
     */
    private Integer cityid;
    /**
     * 项目id
     */
    private Integer projectid;
    /**
     * 医院名称
     */
    @TableField("hospital_name")
    private String hospitalName;
    /**
     * 科室名称
     */
    @TableField("department_name")
    private String departmentName;


    public Integer getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(Integer hospitalid) {
        this.hospitalid = hospitalid;
    }

    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    protected Serializable pkVal() {
        return this.hospitalid;
    }

    @Override
    public String toString() {
        return "ProjectCityHospital{" +
        "hospitalid=" + hospitalid +
        ", cityid=" + cityid +
        ", projectid=" + projectid +
        ", hospitalName=" + hospitalName +
        ", departmentName=" + departmentName +
        "}";
    }
}
