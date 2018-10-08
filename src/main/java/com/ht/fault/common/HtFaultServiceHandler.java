package com.ht.fault.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class HtFaultServiceHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		System.out.println(request.getRequestURL());
		// 静态请求直接跳出
		if (target.indexOf('.') != -1) {
			StringBuffer url = request.getRequestURL();
			if(url.toString().indexOf("resource")!=-1){  
				System.out.println("进入");
				target="/download";
			}else{
				return ;
			}
		}
		next.handle(target, request, response, isHandled);
	}

}
