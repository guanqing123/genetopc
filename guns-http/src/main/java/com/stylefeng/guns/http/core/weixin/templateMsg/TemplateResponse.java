package com.stylefeng.guns.http.core.weixin.templateMsg;

import com.stylefeng.guns.http.core.weixin.wxobj.result.BaseResult;

import lombok.Getter;
import lombok.Setter;

/**
* create by guanqing
* 2019年11月27日 上午9:27:31
*/
@Getter
@Setter
public class TemplateResponse extends BaseResult {

	/**
	 * 消息id
	 */
	private String msgid;
	
	@Override
    public String toString() {
        StringBuffer buf = new StringBuffer("TemplateResponse[msgid=");
        buf.append(msgid)
        .append(",errcode=").append(super.getErrCode())
        .append(",errmsg=").append(super.getErrMsg())
        .append("]");
        return buf.toString();
    }
}
