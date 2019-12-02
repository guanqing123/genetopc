package com.stylefeng.guns.http.core.schedule;
/**
* create by guanqing
* 2019年11月27日 下午3:10:04
*/

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stylefeng.guns.core.db.Db;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.http.core.weixin.templateMsg.TemplateUtil;
import com.stylefeng.guns.http.model.Project;
import com.stylefeng.guns.http.persistence.ProjectMapper;

public class ScheduleTaskFactory {

	// 模板消息工具类
	private static TemplateUtil templateUtil = SpringContextHolder.getBean(TemplateUtil.class);
	// 日志记录类
	private static Logger logger = LoggerFactory.getLogger(ScheduleManager.class);
	private static ProjectMapper projectMapper = Db.getMapper(ProjectMapper.class);
	/**
	 * 发送新预约提醒
	 * @return
	 */
	public static TimerTask sendNewAppointmentReminder(final String openId, final Integer projectid, final String commitDate, final String remark) {
		return new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Project project = projectMapper.selectById(projectid);
					templateUtil.sendNewAppointmentReminder(openId, project.getXmmc(), commitDate, remark);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("发送新预约提醒失败!", e);
				}
			}
		};
	}
}