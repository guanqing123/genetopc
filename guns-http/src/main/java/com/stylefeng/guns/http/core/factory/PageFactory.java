package com.stylefeng.guns.http.core.factory;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.support.HttpKit;

/**
 * 分页参数构建
 * @author information
 *
 * @param <T>
 */
public class PageFactory<T> {

	/**
	 * 创建 page
	 * @return
	 */
	public Page<T> defaultPage(){
		HttpServletRequest request = HttpKit.getRequest();
		int pageNum = Integer.valueOf(request.getParameter("pageNum")); //当前页码
		int pageSize = Integer.valueOf(request.getParameter("pageSize")); //每页多少条数据
		Page<T> page = new Page<>(pageNum, pageSize);
		return page;
	}
}
