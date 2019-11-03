package com.stylefeng.guns.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by GQ
 * 2018年2月2日 上午10:36:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilePath {

    /** 文件名.  */
    public String fileName;

    /** 文件类型. */
    public String contentType;

    /** 文件 key. */
    public String fileKey;

    /** 文件全路径. */
    public String fileRealPath;

    /** 文件大小. */
    public Long fileSize;
}
