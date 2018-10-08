package com.ht.fault.error;

public class ErrorCode {

	public static final int SUCCESS = 200;
	
	public static final int PARAMETER_ERROR = 1000;//参数异常
	
	
	public static final int OAUTH_CODE_ERROR = 2001;//oauth
	public static final String OAUTH_CODE_ERROR_MSG = "code无效";
	
	
	public static final int USER_TOKEN_ERROR = 600;// TOKEN失效

}
