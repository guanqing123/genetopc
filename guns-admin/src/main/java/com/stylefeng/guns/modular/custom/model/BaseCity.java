package com.stylefeng.guns.modular.custom.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 城市基础表
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-27
 */
@TableName("gene_base_city")
public class BaseCity extends Model<BaseCity> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer cityid;
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


    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
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

    @Override
    protected Serializable pkVal() {
        return this.cityid;
    }

    @Override
    public String toString() {
        return "BaseCity{" +
        "cityid=" + cityid +
        ", cityName=" + cityName +
        ", provinceName=" + provinceName +
        ", person=" + person +
        ", phone=" + phone +
        ", createDate=" + createDate +
        "}";
    }
}
