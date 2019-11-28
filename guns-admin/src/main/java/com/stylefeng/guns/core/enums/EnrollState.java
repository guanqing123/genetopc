package com.stylefeng.guns.core.enums;

import lombok.Getter;

/**
* create by guanqing
* 2019年11月28日 下午4:48:44
*/
@Getter
public enum EnrollState {
	
	pass("1"),
	refuse("2");
	
	private String state;
	
	private EnrollState(String state) {
		this.state = state;
	}
	
	public static EnrollState getEnrollState(String state) {
		for (EnrollState enrollState : EnrollState.values()) {
			if (enrollState.getState().equals(state)) {
				return enrollState;
			}
		}
		return null;
	}
}
