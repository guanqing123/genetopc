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
    
    //新预约提醒
    public static final String TEMPLATE_NEW_APPOINTMENT_REMINDER = "M_XJHgZ6S_mNiBoshlVZYi1eh5d3zCi0vpKML3SvU_w";
    //提醒谁
    public static final String REMAIND_USER = "oAxr51ckmTKZn3-pOLQn8vRfp9uM";
    //报名成功
    public static final String TEMPLATE_ENROLL_PASS = "FlMZuUAXj9_ImnSf9Ls20eWQKXA75JErUEx429oZ4eE";
    //报名失败
    public static final String TEMPLATE_ENROLL_REFUSE = "-aGo7XlCzrdsrcqrcSAiD8CKUae0hmogfZnQuvyzrO8";
}
