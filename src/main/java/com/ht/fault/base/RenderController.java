package com.ht.fault.base;

import com.alibaba.fastjson.JSONObject;
import com.ht.fault.common.kit.IpKit;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.LogKit;

public class RenderController extends Controller {

	
	/**
	 * 
	 * @param object  返回json对象
	 * @param code    返回值
	 * @param msg     返回信息(或者错误信息)
	 */
	public void renderJson(Object object,int code,String msg) {
		JSONObject json = new JSONObject();
		json.put("code",code);
		json.put("msg",msg==""?"请求成功":msg);
		json.put("data", object==null ? new Object():object);
//		JSONObject.toJSONString(object, features);
//		System.out.println("("+IpKit.getRealIp(getRequest())+")"+getRequest().getRequestURI()+" -- 返回数据:"+JsonKit.toJson(json));
		LogKit.info("("+IpKit.getRealIp(getRequest())+")"+getRequest().getRequestURI()+" -- 返回数据:"+JsonKit.toJson(json));
		super.renderJson(json);
	}
	
	/**
	 * 
	 * @param object  返回json对象
	 * @param code    返回值
	 * @param msg     返回信息(或者错误信息)
	 */
	public void renderJson200() {
		this.renderJson(null, 200, "");
	}
	
//	/**
//	 * 
//	 * @param object  返回json对象
//	 * @param code    返回值
//	 * @param msg     返回信息(或者错误信息)
//	 */
//	public void renderJson(Object object) {
//		this.renderJson(object, 200, "");
//	}
	
	
	
	/**
	 * 
	 * @param object  返回json对象
	 * @param code    返回值
	 * @param msg     返回信息(或者错误信息)
	 */
	public void renderJson(Object object,int code) {
		this.renderJson(object, code, "");
	}
	
	/**
	 * 返回成功数据
	 * @param object  返回json对象
	 * @param msg     返回信息(或者错误信息)
	 */
	public void renderJsonSuccess200(String msg) {
		this.renderJson(null, 200, msg);
	}
	/**
	 * 返回成功数据
	 * @param object  返回json对象
	 * @param msg     返回信息(或者错误信息)
	 */
	public void renderJsonSuccess(Object object,String msg) {
		this.renderJson(object, 200, msg);
	}
	/**
	 * 返回成功数据
	 * @param object  返回json对象
	 * @param msg     返回信息(或者错误信息)
	 */
	public void renderJsonSuccess(String key,Object value,String msg) {
		this.renderJson(new JSONObject().fluentPut(key, value), 200, msg);
	}
	
	/**
	 * 返回成功数据
	 * @param object  返回json对象
	 * @param msg     返回信息(或者错误信息)
	 */
	public void renderJsonSuccess(Object object) {
		this.renderJson(object, 200, "");
	}
	/**
	 * 返回成功错误
	 * @param object  返回json对象
	 * @param msg     返回信息(或者错误信息)
	 */
	public void renderJsonError(int code,String msg) {
		this.renderJson(null, code,msg);
	}
	/**
	 * 返回成功错误
	 * @param object  返回json对象
	 * @param msg     返回信息(或者错误信息)
	 */
	public void renderJsonError(int code) {
		this.renderJson(null, code,"");
	}
	
	public void renderInterfaceError(int code){
		this.renderJson(null, code,"调用接口失败");
	}
	
	
}
