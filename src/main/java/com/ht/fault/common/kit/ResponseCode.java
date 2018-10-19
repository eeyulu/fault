package com.ht.fault.common.kit;

public class ResponseCode {
	
	public static final int HT_IM_SUCCESS = 200;//HtIm请求成功
	public static final String HT_IM_SUCCESS_MSG = "请求成功";//HtIm请求成功
	
	public static final int HT_IM_ERROR= 1000;//执行失败
	public static final String HT_IM_ERROR_MSG = "请求失败";//HtIm请求
	
	public static final int HT_IM_PARAM_ERROR= 2001;//参数不能为空
	public static final String HT_IM_PARAM_ERROR_MSG= "参数不能为空";//参数不能为空

	public static final int HT_IM_FRIENDSHIP_ERROR = 2002;//好友关系已存在
	
	public static final int HT_IM_SERVICE_ERROR= 1003;//客服全忙
	public static final String HT_IM_SERVICE_ERROR_MSG= "客服全忙";//客服全忙
	
	public static final int USER_SAVE_THIRD_ERROR = 10003;// 第三方已存在
	public static final String USER_SAVE_THIRD_ERROR_MSG = "该用户已被绑定";// 第三方已存在
	
	public static final int USER_LOGIN_USERNAME = 10001;// 用户名不存在
	public static final String USER_LOGIN_USERNAME_MSG = "该用户名存在";// 用户名不存在
	
	public static final int USER_LOGIN_PASSWORD = 10002;// 密码错误
	public static final String USER_LOGIN_PASSWORD_MSG = "密码有误";// 密码错误
	
	public static final int USER_DEL_THIRD_ERROR = 10004;// 第三方不存在
	public static final String USER_DEL_THIRD_ERROR_MSG = "第三方不存在";// 第三方不存在
	

	public static final int USER_FRIEND_ADD_ERROR = 10101;
	public static final String USER_FRIEND_ADD_ERROR_MSG = "添加好友失败";

	public static final int GROUP_CREATE_ERROR = 20001;
	public static final String GROUP_CREATE_ERROR_MSG = "群组已经存在";
	
	public static final int GROUP_UPDATE_ERROR = 20002;
	public static final String GROUP_UPDATE_ERROR_MSG = "更新群组信息失败";
	
	public static final int GROUP_DELETE_ERROR = 20003;
	public static final String GROUP_DELETE_ERROR_MSG = "删除群组信息失败";
	
	public static final int GROUP_PACKET_ERROR = 20004;
	public static final String GROUP_PACKET_ERROR_MSG = "查询科室用户异常";
	
	public static final int GROUP_GROUP_ERROR = 20005; 
	public static final String GROUP_GROUP_ERROR_MSG = "查询群组用户失败";
	
	public static final int GROUP_MEMBER_ERROR = 20006; 
	public static final String GROUP_MEMBER_ERROR_MSG = "添加群组成员已存在";
	
	public static final int FAULT_FORM_TOKEN = 30001; 
	public static final String FAULT_FORM_TOKEN_MSG = "故障工单已被抢走";

	public static final int FAULT_FORM_COMMENT = 30002; 
	public static final String FAULT_FORM_COMMENT_MSG = "故障工单已被评价";
	
	public static final int FAULT_FORM_REPEAL = 30003; 
	public static final String FAULT_FORM_REPEAL_MSG = "工单已被接单,撤销失败";
	
	public static final int FAULT_TOKING_REPEAL = 30004; 
	public static final String FAULT_TOKING_REPEAL_MSG = "工单已被撤销，抢单失败";
	
	public static final int FAULT_STATUS_UPDATE  = 30005; 
	public static final String FAULT_STATUS_UPDATE_MSG = "工单状态已更新,操作失败！";
	
	public static final int FAULT_NOTIN_DEPT  = 30006; 
	public static final String FAULT_NOTIN_DEPT_MSG = "该员工不隶属负责部门！";
	
	public static final int HT_IM_SERVER_ERROR = -1;//服务器异常
	public static final String HT_IM_SERVER_ERROR_MSG = "网络异常，请稍后重试";
	
	public static final int USER_TOKEN_ERROR = 2000;//TOKEN失效
	public static final String USER_TOKEN_ERROR_MSG = "ht_token失效";//HIS请求成功
	public static final String HT_IM_HIS_SUCCESS= "0000";//HIS请求成功
//	public static final int HT_IM_HIS_ACCOUNT_ERROR = 4001;//没有查询到结果
//	public static final int HT_IM_HIS_ACCOUNT_ERROR = 4002;//患者未住院
//	public static final int HT_IM_HIS_ACCOUNT_ERROR = 4003;//微信支付已经存在交易号
//	public static final int HT_IM_HIS_ACCOUNT_ERROR = 4004;//his_token过期
//	public static final int HT_IM_HIS_ACCOUNT_ERROR = 4005;//账号验证失败
//	public static final int HT_IM_HIS_ACCOUNT_ERROR = 4006;//his_token错误
//	public static final int HT_IM_HIS_ACCOUNT_ERROR = 4007;//his账号已停用

}