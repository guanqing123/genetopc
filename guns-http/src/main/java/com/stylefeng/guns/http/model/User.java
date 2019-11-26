package com.stylefeng.guns.http.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-26
 */
@TableName("gene_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * openid
     */
    @TableId(type = IdType.INPUT)
    private String openId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private String sex;
    /**
     * 城市
     */
    private String city;
    /**
     * 国家
     */
    private String country;
    /**
     * 省
     */
    private String province;
    /**
     * 语言
     */
    private String language;
    /**
     * 头像
     */
    private String headImgUrl;
    /**
     * 关注时间
     */
    private Long subscribeTime;
    /**
     * 联合id
     */
    private String unionId;
    /**
     * 标记
     */
    private String remark;
    /**
     * 组id
     */
    private String groupId;


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Long getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    protected Serializable pkVal() {
        return this.openId;
    }

    @Override
    public String toString() {
        return "User{" +
        "openId=" + openId +
        ", nickName=" + nickName +
        ", sex=" + sex +
        ", city=" + city +
        ", country=" + country +
        ", province=" + province +
        ", language=" + language +
        ", headImgUrl=" + headImgUrl +
        ", subscribeTime=" + subscribeTime +
        ", unionId=" + unionId +
        ", remark=" + remark +
        ", groupId=" + groupId +
        "}";
    }
}
