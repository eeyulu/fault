package com.ht.fault.log;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;

public class LogService {	
	public JSONObject findLogs(String date) throws Exception{
		String path = this.getClass().getResource("/").getPath();
		path = URLDecoder.decode(path,"utf-8");
//		path = path.substring(0,path.indexOf("target"))+"src/main/webapp/upload/log";
		path = path.substring(0,path.indexOf("ht_data_service"))+"/ht_data_service/upload/log";
		JSONObject result = new JSONObject();	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String format = sdf.format(new Date());
		String fileName = "ht_data_service.log_" + date + ".log";
		if(date.equals(format)){
			fileName = "ht_data_service.log";
		}
		String filePath = PropKit.get("uploadUrl")+ fileName;		
		File file = new File(path + "/" + fileName);
		if(file.isFile() && file.exists()){
			result.put("filePath", filePath);
			result.put("fileName", fileName);
			return result;
		}else{
			return null;	
		}		
	}
}
