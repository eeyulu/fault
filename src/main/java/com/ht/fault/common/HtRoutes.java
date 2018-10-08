package com.ht.fault.common;

import com.ht.fault.index.IndexController;
import com.ht.fault.log.LogController;
import com.ht.fault.order.ManageController;
import com.jfinal.config.Routes;

public class HtRoutes extends Routes {

	@Override
	public void config() {
		// TODO Auto-generated method stub
		add("/",IndexController.class);//验证服务启动
		add("/log",LogController.class);
		add("/manage",ManageController.class);
	}

}
