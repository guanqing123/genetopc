package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * <p>
 * 应用密钥表
 * </p>
 *
 * @author myc123
 * @since 2018-07-23
 */
@Data
@TableName("sys_secret_key")
public class SecretKey extends Model<SecretKey> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotNull
    private Integer userId;
    /**
     * AppKey
     */
    @TableId(value = "appKey", type = IdType.INPUT)
    private String appKey;
    /**
     * AppSecret
     */
    private String appSecret;
    /**
     * 状态
     */
    private Integer status;
    /**
     * AppName
     */
    @NotBlank
    private String appName;

    @Override
    protected Serializable pkVal() {
        return this.appKey;
    }

    @Override
    public String toString() {
        return "SecretKey{" +
        "userId=" + userId +
        ", appKey=" + appKey +
        ", appSecret=" + appSecret +
        ", status=" + status +
        ", appName=" + appName +
        "}";
    }
}
