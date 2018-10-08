package com.ht.fault.common.kit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.Ret;

public class JsonKit {
	
	public static JSONObject ret2JsonObject(Ret ret) {
		String jsonStr =com.jfinal.kit.JsonKit.toJson(ret);
		JSONObject json =JSON.parseObject(jsonStr);
		json.remove("state");
		json.remove("msg");
		return json;
	}

}
