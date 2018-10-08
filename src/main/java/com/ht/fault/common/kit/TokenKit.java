package com.ht.fault.common.kit;


import java.util.List;

import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;

public class TokenKit {

	public static final String TOKEN_TOKEN_USERID = "token_userId";
	public static final String TOKEN_TOKEN_TIME = "token_time";
	public static final String TOKEN_USERID_TOKEN = "userId_token";
	public static final String CODE_CACHE = "code-cache";
	public static final int TOKEN_TIME = 60 * 60 * 24 * 15;// 单位为:秒


	private static Long currentTimeMillis() {
		return System.currentTimeMillis();
	}
	/**
	 * 验证Token check whether token string is valid. if session contains the
	 * token string, return true. otherwise, return false.
	 * 
	 * @param String
	 *            tokenStr
	 * @param HttpSession
	 *            session
	 * @return true: session contains tokenStr; false: session is null or
	 *         tokenStr is id not in session
	 */

	public static boolean isTokenValid(String token) {
return false;
//		String userId = CacheKit.get(TOKEN_TOKEN_USERID, token);
//      LogKit.info("token:" + token + "    userId:" + userId);
//		if (StrKit.notBlank(userId)) {
//			// 如过包含该token，然后验证是否时间过期
//			Long time = CacheKit.get(TOKEN_TOKEN_TIME, token);
//			if (StrKit.notNull(time)) {
//				Long d = currentTimeMillis() - time;
//				if ((d / 1000) > TOKEN_TIME) {// 失效时间暂时设置为20秒
//					System.out.println("token间隔" + (d / 1000) + "失效了:" + token);
//					return false;
//				}
//			} else {
//				return false;
//			}
//			// 如过小与失效时间，然后重新设置失效时间，延长时间
//			CacheKit.put(TOKEN_TOKEN_TIME, token, currentTimeMillis());
//			return true;
//		}
//
//		return false;
		/*
		 * if(getTokenMap().containsKey(token)) { //如过包含该token，然后验证是否时间过期 Long
		 * time = getTimeMap().get(token); Long d =currentTimeMillis()-time;
		 * if((d/1000)>TOKEN_TIME) {//失效时间暂时设置为20秒 return false; }
		 * //如过小与失效时间，然后重新设置失效时间，延长时间 getTimeMap().put(token,
		 * currentTimeMillis()); return true; }else {
		 * 
		 * return false; }
		 */
	}

	/**
	 * 根据token 获取用户UserId
	 * 
	 * @param token
	 * @return
	 */
	public static String getUserIdByToken(String token) {
return "";
//		if (isTokenValid(token)) {
//			return CacheKit.get(TOKEN_TOKEN_USERID, token);
//		} else {
//			return ErrorCode.USER_TOKEN_ERROR+"";
//		}

	}
	/**
	 * 
	 * @param token
	 * @return
	 */
	public static String getTokenByUserId(String userId) {
		return "";
//		return CacheKit.get(TOKEN_USERID_TOKEN, userId);
	}

	public static boolean removeToken(String token) {
//		String userId = CacheKit.get(TOKEN_TOKEN_USERID, token);
//		if (StrKit.notBlank(userId)) {
//			CacheKit.remove(TOKEN_TOKEN_USERID, token);
//			CacheKit.remove(TOKEN_TOKEN_TIME, token);
//			CacheKit.remove(TOKEN_USERID_TOKEN, userId);
//		}
		return true;
	}
	
	
	public static void saveCode(String code, String userId) {
		CacheKit.put(CODE_CACHE, code, userId);
	}
	
	public static void removeCode(String code) {
		CacheKit.remove(CODE_CACHE, code);
	}
	
	public static String getCode(String code) {
		return CacheKit.get(CODE_CACHE, code);
	}
	
//	public static void testToken() {
//		String userId = "1";
//		String token = "a";
//		saveToken(userId,token);
//		String token1 = CacheKit.get(TOKEN_USERID_TOKEN, userId);
//		System.out.println(token1);
//		userId = "2";
//		saveToken(userId,token);
//		String token2 = CacheKit.get(TOKEN_USERID_TOKEN, "1");
//		System.out.println(token2);
//		List<String> list = CacheKit.getKeys(TOKEN_USERID_TOKEN);
//		
//	}

}
