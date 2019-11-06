package com.stylefeng.guns.core.util;


import java.io.ByteArrayInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.stylefeng.guns.config.properties.AliyunProperties;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.common.exception.FileUploadException;
import com.stylefeng.guns.core.domain.FilePath;
import jodd.datetime.JDateTime;
import lombok.extern.slf4j.Slf4j;



/**
 * create by guanqing
 * 2018年1月23日 上午11:19:22
 * 阿里对象存储工具类
 */
@Service
@Slf4j
public class OssUtil {

    @Autowired
    AliyunProperties aliyunProp;

    // endpoint 是访问OSS的域名.
    private static String endpoint = "https://oss-cn-qingdao.aliyuncs.com";

    /**
     * 字节数组 上传
     * @param bs
     * @param suffix
     * @return
     */
    public FilePath transferTo(byte[] bs, String suffix) {
    	JDateTime jdt = new JDateTime();
        jdt.setCurrentTime();
        
        StringBuffer key = new StringBuffer("upload/image/" + jdt.toString("YYYYMM") + "/")
        		.append(UUID.randomUUID().toString()).append(suffix);
        // 生成OSSClient.
        OSSClient ossClient = new OSSClient(endpoint, aliyunProp.getOss().getAccessKeyId(), aliyunProp.getOss().getAccessKeySecret());
        // 判断Bucket是否存在.
        String bucketName = aliyunProp.getOss().getBucket();
        if (!ossClient.doesBucketExist(bucketName)) {
            ossClient.createBucket(bucketName);
        }
        
        FilePath path = new FilePath();
        // 把file存入OSS,Object的名称为firstKey.
        try {
            ossClient.putObject(bucketName, key.toString(), new ByteArrayInputStream(bs));
            StringBuffer filePath = new StringBuffer(getOssDomain()).append(key);
            path.setFileKey(key.toString());
            path.setFileRealPath(filePath.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException(BizExceptionEnum.FILE_OSS_ERROR, path);
        } finally {
            ossClient.shutdown();
        }
        return path;
    }
    
    /**
     * 单文件 上传
     *
     * @param file
     * @return
     * @throws Exception
     */
    public FilePath transferTo(MultipartFile file) {
        JDateTime jdt = new JDateTime();
        jdt.setCurrentTime();
        
        StringBuffer key = new StringBuffer("upload/image/" + jdt.toString("YYYYMM") + "/").append(UUID.randomUUID().toString()).append(".").append(FilenameUtils.getExtension(file.getOriginalFilename()));
        // 生成OSSClient.
        OSSClient ossClient = new OSSClient(endpoint, aliyunProp.getOss().getAccessKeyId(), aliyunProp.getOss().getAccessKeySecret());
        // 判断Bucket是否存在.
        String bucketName = aliyunProp.getOss().getBucket();
        if (!ossClient.doesBucketExist(bucketName)) {
            ossClient.createBucket(bucketName);
        }

        FilePath path = new FilePath();
        // 把file存入OSS,Object的名称为firstKey.
        try {
            ossClient.putObject(bucketName, key.toString(), file.getInputStream());
            StringBuffer filePath = new StringBuffer(getOssDomain()).append(key);
            path.setFileName(file.getOriginalFilename());
            path.setContentType(file.getContentType());
            path.setFileKey(key.toString());
            path.setFileRealPath(filePath.toString());
            path.setFileSize(file.getSize());
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException(BizExceptionEnum.FILE_OSS_ERROR, path);
        } finally {
            ossClient.shutdown();
        }
        return path;
    }
    

    private String getOssDomain() {
        if (ToolUtil.isEmpty(aliyunProp.getOss().getDomain())) {
            return MessageFormat.format("https://{0}.oss-cn-qingdao.aliyuncs.com/", aliyunProp.getOss().getBucket());
        } else {
            return aliyunProp.getOss().getDomain();
        }
    }
    
    /**
     * 字节数组流数组 上传
     * @param ins
     * @return
     */
    public List<FilePath> transferTo(ByteArrayInputStream[] ins){
        JDateTime jdt = new JDateTime();
        jdt.setCurrentTime();

        // 生成OSSClient
        OSSClient ossClient = new OSSClient(endpoint, aliyunProp.getOss().getAccessKeyId(), aliyunProp.getOss().getAccessKeySecret());
        // 判断Bucket是否存在
        String bucketName = aliyunProp.getOss().getBucket();
        if (!ossClient.doesBucketExist(bucketName)) {
            ossClient.createBucket(bucketName);
        }
        
        StringBuffer basePath = new StringBuffer("upload/image/" + jdt.toString("YYYYMM") + "/");
        StringBuffer key = new StringBuffer();
        List<FilePath> paths = new ArrayList<>();
        try {
        	int i = 0;
        	int available = 0;
            for (ByteArrayInputStream in : ins) {
            		i++;
                    key.setLength(0);
                    key.append(basePath).append(UUID.randomUUID().toString()).append(".").append("png");
                    available = in.available();
                    ossClient.putObject(bucketName, key.toString(), in);
                    FilePath path = new FilePath();
                    path.setFileName(i + ".png");
                    path.setContentType("image/png");
                    path.setFileKey(key.toString());
                    path.setFileRealPath(key.insert(0, getOssDomain()).toString());
                    path.setFileSize(new Integer(available).longValue());
                    paths.add(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException(BizExceptionEnum.FILE_OSS_ERROR, paths);
        } finally {
            ossClient.shutdown();
        }
        return paths;
    }

    /**
     * 多文件上传
     *
     * @param files
     * @return
     * @throws Exception
     */
    public List<FilePath> transferTo(MultipartFile[] files) {
        JDateTime jdt = new JDateTime();
        jdt.setCurrentTime();

        // 生成OSSClient
        OSSClient ossClient = new OSSClient(endpoint, aliyunProp.getOss().getAccessKeyId(), aliyunProp.getOss().getAccessKeySecret());
        // 判断Bucket是否存在
        String bucketName = aliyunProp.getOss().getBucket();
        if (!ossClient.doesBucketExist(bucketName)) {
            ossClient.createBucket(bucketName);
        }
        
        StringBuffer basePath = new StringBuffer("upload/image/" + jdt.toString("YYYYMM") + "/");
        StringBuffer key = new StringBuffer();
        List<FilePath> paths = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                if (file != null && file.getSize() > 0) {
                    key.setLength(0);
                    key.append(basePath).append(UUID.randomUUID().toString()).append(".").append(FilenameUtils.getExtension(file.getOriginalFilename()));
                    ossClient.putObject(bucketName, key.toString(), file.getInputStream());
                    FilePath path = new FilePath();
                    path.setFileName(file.getOriginalFilename());
                    path.setContentType(file.getContentType());
                    path.setFileKey(key.toString());
                    path.setFileRealPath(key.insert(0, getOssDomain()).toString());
                    path.setFileSize(file.getSize());
                    paths.add(path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException(BizExceptionEnum.FILE_OSS_ERROR, paths);
        } finally {
            ossClient.shutdown();
        }
        return paths;
    }

    /**
     * 多文件删除
     * @param paths
     */
    public void deleteObjects(List<FilePath> paths) {
        // 生成OSSClient.
        OSSClient ossClient = new OSSClient(endpoint, aliyunProp.getOss().getAccessKeyId(), aliyunProp.getOss().getAccessKeySecret());
        try {
            String bucketName = aliyunProp.getOss().getBucket();
            for (FilePath filePath : paths) {
                ossClient.deleteObject(bucketName, filePath.getFileKey());
                System.out.println("ossClient.deleteObject >> " + filePath.getFileName() + "\t" + filePath.getFileKey());
            }
        } catch (OSSException e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }
    
    /**
     * 单文件删除
     * @param fileKey
     */
    public void deleteObject(String fileKey) {
    	// 生成OSSClient.
        OSSClient ossClient = new OSSClient(endpoint, aliyunProp.getOss().getAccessKeyId(), aliyunProp.getOss().getAccessKeySecret());
        try {
            String bucketName = aliyunProp.getOss().getBucket();
            ossClient.deleteObject(bucketName, fileKey);
            log.error("fileKey=>{}", fileKey);
        } catch (OSSException e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }
}
