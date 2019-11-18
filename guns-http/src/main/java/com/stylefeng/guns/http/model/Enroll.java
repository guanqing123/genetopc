package com.stylefeng.guns.http.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 报名表
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-11
 */
@TableName("gene_enroll")
public class Enroll extends Model<Enroll> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "enrollid", type = IdType.AUTO)
    private Integer enrollid;
    /**
     * 项目id
     */
    private Integer projectid;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String telephone;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 所患疾病
     */
    private String disease;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 详细地址
     */
    @TableField("detail_address")
    private String detailAddress;
    /**
     * 备注
     */
    private String comment;
    /**
     * 状态:(0:等待审核; 1:已通过; 2:已被拒)
     */
    private Integer state;
    /**
     * 创建日期
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 附件
     */
    @TableField(exist = false)
    private MultipartFile[] files;

	public Integer getEnrollid() {
        return enrollid;
    }

    public void setEnrollid(Integer enrollid) {
        this.enrollid = enrollid;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

    @Override
    protected Serializable pkVal() {
        return this.enrollid;
    }

    @Override
    public String toString() {
        return "Enroll{" +
        "enrollid=" + enrollid +
        ", projectid=" + projectid +
        ", name=" + name +
        ", telephone=" + telephone +
        ", sex=" + sex +
        ", age=" + age +
        ", disease=" + disease +
        ", province=" + province +
        ", city=" + city +
        ", district=" + district +
        ", detailAddress=" + detailAddress +
        ", comment=" + comment +
        ", state=" + state +
        ", createDate=" + createDate +
        "}";
    }
}