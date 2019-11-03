package com.stylefeng.guns.modular.system.warpper;

import java.util.List;
import java.util.Map;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;

public class SecretKeyWarpper extends BaseControllerWarpper {

	public SecretKeyWarpper(List<Map<String, Object>> list) {
		super(list);
	}

	@Override
	public void warpTheMap(Map<String, Object> map) {
		map.put("userName", ConstantFactory.me().getUserNameById((Integer) map.get("userId")));
		map.put("statusName", ConstantFactory.me().getDictsByName("密钥状态", (Integer) map.get("status")));
	}

}
