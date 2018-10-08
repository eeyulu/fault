package com.ht.fault.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * 
 * 请求参数处理
 */
public class BaseInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		controller.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		inv.invoke();
//		
//		if (controller.getRequest().getMethod().equals("OPTIONS")) {
//			inv.getController().renderJson("");
//		} else {
//
//			Enumeration rnames = controller.getRequest().getParameterNames();
//			JSONObject parameters = new JSONObject();
//			for (Enumeration e = rnames; e.hasMoreElements();) {
//				String thisName = e.nextElement().toString();
//				String thisValue = controller.getRequest().getParameter(thisName);
//				parameters.put(thisName, thisValue);
//			}
//		
//			JSONObject json = new JSONObject();
//			if (StrKit.notNull(parameters)) {
//		
//				if (!parameters.containsKey("appId")) {
////					json.put("code", ErrorCode.PARAMETER_ERROR);
//					json.put("msg", "appId不能为空");
//					System.out.println(IpKit.getRealIp(controller.getRequest())+"：appId不能为空");
//					inv.getController().renderJson(json);
//				} else if (!parameters.containsKey("clientType")) {
//
////					json.put("code", ErrorCode.PARAMETER_ERROR);
//					json.put("msg", "clientType不能为空");
//					System.out.println(IpKit.getRealIp(controller.getRequest())+"：clientType不能为空");
//					inv.getController().renderJson(json);
//				} else {
//					inv.getController().setAttr("data", parameters);
//					inv.invoke();
//				}
//
//			} else {
////				json.put("code", ErrorCode.PARAMETER_ERROR);
//				json.put("msg", "appId和clientType不能为空");
//				System.out.println(IpKit.getRealIp(controller.getRequest())+"：appId和clientType不能为空");
//				inv.getController().renderJson(json);
//			}
//
//		}

	}

}
