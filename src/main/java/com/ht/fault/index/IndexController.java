package com.ht.fault.index;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ht.fault.base.BaseController;
import com.ht.fault.common.kit.HttpUtil;
import com.ht.fault.common.kit.TokenKit;
import com.ht.fault.error.ErrorCode;
import com.jfinal.aop.Clear;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法 详见 JFinal 俱乐部:
 * http://jfinal.com/club
 * 
 * IndexController
 */
// @Clear({AuthInterceptor.class,BaseInterceptor.class})
@Clear
public class IndexController extends BaseController {
	public static final String CORP_ID = "ding1ee87f569129376135c2f4657eb6378f";
	public static final String CORP_SECRET = "-4-8LHyOg7Jc0goq6ywxAR-lldzs9EeXn9GXiwZEzOePaAdpRzVhW_xbNre_16hV";
	public static final String SSO_Secret = "FoBZaarTtL8QWN24lkCD3SlUalZ3e7ZyX6n_8wXrnVGZCDl1QOJENyXwj4WKiKGj";

	public void index() {
//		render("index/index.html");
		render("index/test.html");
	}

	public void getUserId() {
		String access_token = getAccessToken();
		System.out.println("access_token:" + access_token);
		String code = getPara("code");
		System.out.println("code:" + code);
		String url = "https://oapi.dingtalk.com/user/getuserinfo";
		String param = "access_token=" + access_token + "&code=" + code;
		String result = HttpUtil.get(url, param);
		System.out.println(result);
		JSONObject jsonObject = JSON.parseObject(result);
		String userId = jsonObject.getString("userid");
		System.out.println("userId:" + userId);
		setCookie("userId", userId, 7200);
		renderJson(jsonObject);
	}

	public String getAccessToken() {
		String url = "https://oapi.dingtalk.com/gettoken";
		String param = "corpid=" + CORP_ID + "&corpsecret=" + CORP_SECRET;
		String result = HttpUtil.get(url, param);
		JSONObject jsonObject = JSON.parseObject(result);
		String access_token = jsonObject.getString("access_token");
		return access_token;
	}

	public void getUserInfo() {
		String access_token = getAccessToken();
		System.out.println("access_token:" + access_token);
		String userId = getCookie("userId");

		String url = "https://oapi.dingtalk.com/user/get";
		String param = "access_token=" + access_token + "&userid=" + userId;
		
		String result = HttpUtil.get(url, param);
		System.out.println(result);
		JSONObject jsonObject = JSON.parseObject(result);
		renderJson(jsonObject);
	}
	
	/**
	 * 
	 */
	public void oauth() {
		String clientId = getPara("clientId");
		System.out.println("clientId:" + clientId);
		String ht_token = getPara("ht_token")+"xx";
		System.out.println("ht_token:" + ht_token);
		String url = "http://192.168.1.194:8081/ht_im_service/oauth";
		
		Map<String, String> paras = new HashMap<String, String>();
		paras.put("clientId", clientId);
		paras.put("ht_token", ht_token);
		String reply = HttpKit.get(url, paras);
		System.out.println(reply);
		JSONObject json = JSONObject.parseObject(reply);
/*		if(json.getInteger("code")==ErrorCode.SUCCESS) {
			String code = UUID.randomUUID().toString();
			TokenKit.saveCode(code, json.getString("userId"));
		}*/
		renderJson(json);
	}
	
	/**
	 * 验证code
	 */
	public void verifyCode() {
		JSONObject json = new JSONObject();
		String code = getPara("code");
		String userId = TokenKit.getCode(code);
		if(StrKit.isBlank(userId)) {
			json.put("code", ErrorCode.OAUTH_CODE_ERROR);
			json.put("msg",ErrorCode.OAUTH_CODE_ERROR_MSG);
		}else {
			json.put("code", ErrorCode.SUCCESS);
			json.put("userId", userId);
		}
		renderJson(json);
	}
	
//	public void cacheTe() {
//		TokenKit.testToken();
//		renderJson();
//	}
}
