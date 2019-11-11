package com.stylefeng.guns.http.service.impl;

import com.stylefeng.guns.core.domain.FilePath;
import com.stylefeng.guns.core.exception.FileUploadException;
import com.stylefeng.guns.core.util.OssUtil;
import com.stylefeng.guns.http.model.Enroll;
import com.stylefeng.guns.http.model.EnrollImage;
import com.stylefeng.guns.http.persistence.EnrollImageMapper;
import com.stylefeng.guns.http.persistence.EnrollMapper;
import com.stylefeng.guns.http.service.IEnrollService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 报名表 服务实现类
 * </p>
 *
 * @author guanqing123
 * @since 2019-11-11
 */
@Service
public class EnrollServiceImpl extends ServiceImpl<EnrollMapper, Enroll> implements IEnrollService {

	@Autowired
	private OssUtil ossUtil;
	
	@Autowired
	private EnrollImageMapper enrollImageMapper;
	
	@Override
	public void saveEnroll(Enroll enroll) {
		// TODO Auto-generated method stub
		List<FilePath> paths = ossUtil.transferTo(enroll.getFiles());
		try {
			this.saveEnroll(enroll);
			for (FilePath path : paths) {
				EnrollImage image = new EnrollImage();
				image.setEnrollid(enroll.getEnrollid());
				image.setFileKey(path.getFileKey());
				image.setFilePath(path.getFileRealPath());
				enrollImageMapper.insert(image);
			}
		} catch (Exception e) {
			throw new FileUploadException(500, e.getMessage(), paths);
		}
	}
}
