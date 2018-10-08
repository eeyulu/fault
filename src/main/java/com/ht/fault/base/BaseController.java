package com.ht.fault.base;

import com.ht.fault.common.kit.TokenKit;

public class BaseController extends RenderController {
	
	
	public String getCurrentUserID(){
		return TokenKit.getUserIdByToken(getPara("ht_token"));
	}

}
