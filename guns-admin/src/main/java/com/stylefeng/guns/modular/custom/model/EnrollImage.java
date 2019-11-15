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
 * 报名图片表
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-15
 */
@TableName("gene_enroll_image")
public class EnrollImage extends Model<EnrollImage> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "imageid", type = IdType.AUTO)
    private Integer imageid;
    /**
     * 报名id
     */
    private Integer enrollid;
    /**
     * 文件key
     */
    @TableField("file_key")
    private String fileKey;
    /**
     * 图片地址
     */
    @TableField("file_path")
    private String filePath;
    /**
     * 创建日期
     */
    @TableField("create_date")
    private Date createDate;


    public Integer getImageid() {
        return imageid;
    }

    public void setImageid(Integer imageid) {
        this.imageid = imageid;
    }

    public Integer getEnrollid() {
        return enrollid;
    }

    public void setEnrollid(Integer enrollid) {
        this.enrollid = enrollid;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.imageid;
    }

    @Override
    public String toString() {
        return "EnrollImage{" +
        "imageid=" + imageid +
        ", enrollid=" + enrollid +
        ", fileKey=" + fileKey +
        ", filePath=" + filePath +
        ", createDate=" + createDate +
        "}";
    }
}
