package com.ht.fault.log;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.ht.fault.base.BaseController;
import com.jfinal.aop.Clear;

public class LogController extends BaseController {
	private Logger loger = Logger.getLogger(LogController.class);
	public static final LogService me = new LogService();
	@Clear
	public void getHtDataLog(){
		String time=getPara("date");
		try {
			JSONObject json=me.findLogs(time);	
			if(json!=null){							
				renderJsonSuccess(json, "获取log日志url成功");
			}else{
				renderJsonError(1001, time+"日志不存在");
			}	
		} catch (Exception e) {
			loger.error(e.getMessage());
			e.printStackTrace();
			renderJsonError(1001, time+"日志不存在");
		}
		
	}
}
