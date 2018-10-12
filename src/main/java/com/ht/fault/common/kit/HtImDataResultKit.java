package com.ht.fault.common.kit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;

/**
 * 
 * <p>Title:DataServerResultKit</p>
 * <p>Description:  本地数据来源 </p>
 * <p>Company: 汉唐软件科技</p>
 * @author HBF
 * @date 2017年11月28日 上午10:20:40
 * @version 1.0
 */
public class HtImDataResultKit {

	private static String ht_data_url = PropKit.get("im_data_url");// 获取数据库域名
	

	
	/**
	 * 查询某部门所有员工userid
	 * 
	 * @param param
	 * @return
	 */
	public static JSONObject findUserId(String param) {
		JSONObject result = new JSONObject();
		
		String jsonObjectData =HttpUtil.post(ht_data_url + "/organi/findUserId",param);
		if (StrKit.notBlank(jsonObjectData) && jsonObjectData.length() > 0) {
			JSONObject parseObject = JSON.parseObject(jsonObjectData);
			if (parseObject.getInteger("code") == ResponseCode.HT_IM_SUCCESS) {
				String userObject = parseObject.getString("data");
				result = JSON.parseObject(userObject);
			}else{
				result = parseObject;
			}
		}
		return result;
	}
	
	/**
	 * 查询某部门所有父部门
	 * 
	 * @param param
	 * @return
	 */
	public static JSONObject findFatherDept(String param) {
		JSONObject result = new JSONObject();
		
		String jsonObjectData =HttpUtil.post(ht_data_url + "/organi/findFatherDept",param);
		if (StrKit.notBlank(jsonObjectData) && jsonObjectData.length() > 0) {
			JSONObject parseObject = JSON.parseObject(jsonObjectData);
			if (parseObject.getInteger("code") == ResponseCode.HT_IM_SUCCESS) {
				String userObject = parseObject.getString("data");
				result = JSON.parseObject(userObject);
			}else{
				result = parseObject;
			}
		}
		return result;
	}
	
	/**
	 * 群组中已存在成员
	 * 
	 * @param groupId
	 * @param string
	 * @return
	 */
	public static Integer checkExistUser(String groupId, String userId) {
		return Db.queryInt("select count(*) from HT_IM_GROUP_USER WHERE group_id=? and user_id=?",groupId,userId);
	}
	
	
	/**
	 * 通过patientId获取openid
	 * 
	 * @param param
	 * @return
	 */
	public static JSONObject getOpenIdByPid(String param) {
		JSONObject result = new JSONObject();
		String resultStr =HttpUtil.post(ht_data_url + "/user/base/getWxOpenId",param);
		JSONObject jsonObject = JSON.parseObject(resultStr);
		if (jsonObject.getInteger("code") == ResponseCode.HT_IM_SUCCESS) {
			result.put("result", jsonObject.getString("data"));
		}
		result.put("code", jsonObject.getInteger("code"));
		return result;
	}
	
	/**
	 * 通过openId获取userid
	 * 
	 * @param param
	 * @return
	 */
	public static JSONObject getUserIdByOpenId(String param) {
		JSONObject result = new JSONObject();
		String resultStr =HttpUtil.post(ht_data_url + "/user/base/getUserId",param);
		JSONObject jsonObject = JSON.parseObject(resultStr);
		if (jsonObject.getInteger("code") == ResponseCode.HT_IM_SUCCESS && ResponseCode.HT_IM_SUCCESS_MSG.equals(jsonObject.getString("msg"))) {
			result.put("userId", jsonObject.getJSONObject("data").getString("userId"));
			result.put("code", ResponseCode.HT_IM_SUCCESS);
		}else {
			result.put("code", ResponseCode.HT_IM_ERROR);
		}
		return result;
	}
	
}
