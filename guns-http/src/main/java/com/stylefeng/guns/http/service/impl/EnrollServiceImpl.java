package com.stylefeng.guns.http.service.impl;

import com.stylefeng.guns.core.domain.FilePath;
import com.stylefeng.guns.core.exception.FileUploadException;
import com.stylefeng.guns.core.util.OssUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.http.model.Enroll;
import com.stylefeng.guns.http.model.EnrollImage;
import com.stylefeng.guns.http.persistence.EnrollImageMapper;
import com.stylefeng.guns.http.persistence.EnrollMapper;
import com.stylefeng.guns.http.service.IEnrollService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	@Override
	public void saveEnroll(Enroll enroll) {
		// TODO Auto-generated method stub
		List<FilePath> paths = ossUtil.transferTo(enroll.getFiles());
		try {
			this.insert(enroll);
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
	
	@Transactional
	@Override
	public void modifyEnroll(Enroll enroll) {
		// TODO Auto-generated method stub
		List<FilePath> paths = null;
		if (ToolUtil.isNotEmpty(enroll.getFiles())) {
			paths = ossUtil.transferTo(enroll.getFiles());
		}
		try {
			enroll.setState(0);
			this.baseMapper.updateById(enroll);
			if (ToolUtil.isNotEmpty(paths)) {
				for (FilePath path : paths) {
					EnrollImage image = new EnrollImage();
					image.setEnrollid(enroll.getEnrollid());
					image.setFileKey(path.getFileKey());
					image.setFilePath(path.getFileRealPath());
					enrollImageMapper.insert(image);
				}
			}
		} catch (Exception e) {
			throw new FileUploadException(500, e.getMessage(), paths);
		}
	}

	@Override
	public List<Enroll> getEnrollList(Page<Enroll> page, String state) {
		// TODO Auto-generated method stub
		return this.baseMapper.getEnrollList(page, state);
	}

	@Transactional
	@Override
	public void deleteEnroll(Integer enrollid) {
		// TODO Auto-generated method stub
		EntityWrapper<EnrollImage> wrapper = new EntityWrapper<>();
		wrapper.eq("enrollid", enrollid);
		List<EnrollImage> images = enrollImageMapper.selectList(wrapper);
		this.baseMapper.deleteById(enrollid);
		enrollImageMapper.delete(wrapper);
		images.forEach(image->{
			ossUtil.deleteObject(image.getFileKey());
		});
	}

	@Transactional
	@Override
	public Enroll detailEnroll(Integer enrollid) {
		// TODO Auto-generated method stub
		return this.baseMapper.detailEnroll(enrollid);
	}
}
