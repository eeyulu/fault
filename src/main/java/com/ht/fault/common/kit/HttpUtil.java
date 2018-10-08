package com.ht.fault.common.kit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;

public class HttpUtil {

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String get(String url, String param) {
		String token = PropKit.get("ht_data_token");
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param+"&ht_data_token="+token;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String post(String url, String param) {
		String token = PropKit.get("ht_data_token");
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param+"&ht_data_token="+token);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * http请求获取用户参数
	 * @param request
	 * @return
	 */
	public static String getParam(HttpServletRequest request) {
		return HttpKit.readData(request);
	}
	
	public static String readJSONString(HttpServletRequest request) {
		StringBuffer json = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}
			if (json.length() < 1) {
				Map<String, String[]> hm = request.getParameterMap();
				if (hm != null && hm.size() > 0) {
					json.append(readjson(hm).toString());
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return json.toString();
	}

	@SuppressWarnings("rawtypes")
	public static JSONObject readjson(Map<String, String[]> hm) {
		JSONObject jobj = new JSONObject();
		// 通过循环遍历的方式获得key和value并set到JSONObject中
		Iterator it = hm.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			String[] values = (String[]) hm.get(key);
			jobj.put(key, values[0]);
		}
		return jobj;
	}

	@SuppressWarnings("rawtypes")
	public static JSONObject readjson(HttpServletRequest request) {
		JSONObject JSONObject = new JSONObject();
		Map pmap = request.getParameterMap();
		// 通过循环遍历的方式获得key和value并set到jsonobject中
		Iterator it = pmap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			String[] values = (String[]) pmap.get(key);
			JSONObject.put(key, values[0]);
		}
		return JSONObject;
	}

}
