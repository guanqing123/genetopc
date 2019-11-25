package com.stylefeng.guns.http.core.weixin.constant;
/**
* create by guanqing
* 2019年11月25日 下午6:20:12
*/
public class WxConstant {
	
	//cookie中openId的命名
    public static final String OPEN_ID = "open_id";
    //openId30天后过期
    public static final int OPEN_ID_LIVE_TIME = 60*60*24*30;
    
	//cookie中nickName的命名
    public static final String NICK_NAME = "nick_name";
    //nickName30天后过期
    public static final int NICK_NAME_LIVE_TIME = 60*60*24*30;
    
	//cookie中headUrl的命名
    public static final String HEAD_URL = "head_url";
    //headUrl30天后过期
    public static final int HEAD_URL_LIVE_TIME = 60*60*24*30;
}
