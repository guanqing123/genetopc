package com.stylefeng.guns.modular.custom.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 医院基础表
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-28
 */
@TableName("gene_base_city_hospital")
public class BaseCityHospital extends Model<BaseCityHospital> {

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
     * 医院名称
     */
    @TableField("hospital_name")
    private String hospitalName;
    /**
     * 科室名称
     */
    @TableField("department_name")
    private String departmentName;
    /**
     * 创建日期
     */
    @TableField("create_date")
    private Date createDate;


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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.hospitalid;
    }

    @Override
    public String toString() {
        return "BaseCityHospital{" +
        "hospitalid=" + hospitalid +
        ", cityid=" + cityid +
        ", hospitalName=" + hospitalName +
        ", departmentName=" + departmentName +
        ", createDate=" + createDate +
        "}";
    }
}
