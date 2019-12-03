package com.stylefeng.guns.http.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.domain.FilePath;
import com.stylefeng.guns.core.domain.Result;
import com.stylefeng.guns.core.exception.FileUploadException;
import com.stylefeng.guns.core.util.OssUtil;
import com.stylefeng.guns.core.util.ResultUtil;
import com.stylefeng.guns.core.util.SmsUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.http.core.schedule.ScheduleManager;
import com.stylefeng.guns.http.core.schedule.ScheduleTaskFactory;
import com.stylefeng.guns.http.model.Enroll;
import com.stylefeng.guns.http.model.EnrollImage;
import com.stylefeng.guns.http.persistence.EnrollImageMapper;
import com.stylefeng.guns.http.persistence.EnrollMapper;
import com.stylefeng.guns.http.service.IEnrollService;

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
	
	@Autowired
	private SmsUtil smsUtil;
	
	@Transactional
	@Override
	public Result<Object> saveEnroll(Enroll enroll) {
		// TODO Auto-generated method stub
		Object icode = smsUtil.getRedisSaveIcode(enroll.getTelephone());
		if (ToolUtil.isNotEmpty(icode) && icode.equals(enroll.getIcode())) {
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
				smsUtil.flushSaveIcode(enroll.getTelephone());
				String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				String remark = "请及时处理(姓名:"+enroll.getName()+" 电话:"+enroll.getTelephone()+")";
				ScheduleManager.me().executeTask(ScheduleTaskFactory.sendNewAppointmentReminder(enroll.getProjectid(), currentDate, remark));
				return ResultUtil.success();
			} catch (Exception e) {
				throw new FileUploadException(500, e.getMessage(), paths);
			}
		} else {
			return ResultUtil.failure(500, "验证码错误");
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
	public List<Enroll> getEnrollList(Page<Enroll> page, String openId, String state) {
		// TODO Auto-generated method stub
		return this.baseMapper.getEnrollList(page, openId, state);
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
