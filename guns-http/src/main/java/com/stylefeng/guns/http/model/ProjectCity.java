package com.stylefeng.guns.http.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 项目城市表
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-11
 */
@TableName("gene_project_city")
public class ProjectCity extends Model<ProjectCity> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "cityid", type = IdType.AUTO)
    private Integer cityid;
    /**
     * 项目id
     */
    private Integer projectid;
    /**
     * 城市
     */
    @TableField("city_name")
    private String cityName;
    /**
     * 省
     */
    @TableField("province_name")
    private String provinceName;
	/**
     * 联系人
     */
    private String person;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 创建日期
     */
    @TableField("create_date")
    private Date createDate;

    /**
     * 医院
     */
    @TableField(exist = false)
    private List<ProjectCityHospital> hospitals;

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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public List<ProjectCityHospital> getHospitals() {
		return hospitals;
	}

	public void setHospitals(List<ProjectCityHospital> hospitals) {
		this.hospitals = hospitals;
	}

    @Override
    protected Serializable pkVal() {
        return this.cityid;
    }

    @Override
    public String toString() {
        return "ProjectCity{" +
        "cityid=" + cityid +
        ", projectid=" + projectid +
        ", cityName=" + cityName +
        ", provinceName=" + provinceName +
        ", person=" + person +
        ", phone=" + phone +
        ", createDate=" + createDate +
        "}";
    }
}
