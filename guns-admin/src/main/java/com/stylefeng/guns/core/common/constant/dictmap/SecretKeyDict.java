package com.stylefeng.guns.core.common.constant.dictmap;

import com.stylefeng.guns.core.common.constant.dictmap.base.AbstractDictMap;

public class SecretKeyDict extends AbstractDictMap {

	@Override
	public void init() {
		put("appKey","密钥");
		put("userId","用户");
		put("appName","应用名");
        put("status","状态");
	}

	@Override
	protected void initBeWrapped() {
		putFieldWrapperMethodName("userId","getUserAccountById");
	}

}
