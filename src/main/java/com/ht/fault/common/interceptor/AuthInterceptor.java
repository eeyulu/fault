package com.ht.fault.common.interceptor;

import java.util.Enumeration;

import com.alibaba.fastjson.JSONObject;
import com.ht.fault.common.kit.IpKit;
import com.ht.fault.common.kit.TokenKit;
import com.ht.fault.error.ErrorCode;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法 详见 JFinal 俱乐部:
 * http://jfinal.com/club
 * 
 * BlogInterceptor 此拦截器仅做为示例展示，在本 demo 中并不需要
 */

public class AuthInterceptor implements Interceptor {
	//// 1.web 2.android 3.ios 4.微信公众号
	public void intercept(Invocation inv) {

		Controller controller = inv.getController();
		if (controller.getRequest().getMethod().equals("OPTIONS")) {
			inv.getController().renderJson("");
		} else {
			String ht_token = "";
			@SuppressWarnings("rawtypes")
			Enumeration enu = controller.getRequest().getParameterNames();
			while (enu.hasMoreElements()) {
				String paraName = (String) enu.nextElement();
				LogKit.info("请求参数:" + paraName + ": " + controller.getPara(paraName));
			}
			ht_token = inv.getController().getPara("accessToken");
			String clientType = inv.getController().getPara("clientType");
			inv.getController().setAttr("accessToken", ht_token);
			inv.getController().setAttr("clientType", clientType);//// 1.web
																	//// 2.android
																	//// 3.ios
																	//// 4.微信公众号

			if (TokenKit.isTokenValid(ht_token)) {
				inv.invoke();
				// "token验证通过";
			} else {
				// "token验证失败,请重新授权";
				JSONObject json = new JSONObject();
				json.put("code", ErrorCode.USER_TOKEN_ERROR);
				json.put("msg", "accessToken已经失效，请重新获取");
				System.out.println(IpKit.getRealIp(controller.getRequest()) + "：accessToken已经失效，请重新获取");
				inv.getController().renderJson(json);
			}
		}

	}
}
