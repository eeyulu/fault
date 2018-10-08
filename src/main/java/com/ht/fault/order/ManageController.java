package com.ht.fault.order;

import com.alibaba.fastjson.JSONObject;
import com.ht.fault.base.BaseController;
import com.ht.fault.common.interceptor.AuthInterceptor;
import com.jfinal.aop.Clear;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 */
@Clear
//@Clear(AuthInterceptor.class)
public class ManageController extends BaseController {

	private ManageService service = enhance(ManageService.class);

	public void listHtml() {
		render("list.html");
	}

	/**
	 * 数据列表
	 * 
	 */
	public void list() {
		int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("limit");
		String repairName = getPara("repairName");
		String faultStatus = getPara("faultStatus");
		String responsible = getPara("responsible");
		String orderNo = getPara("orderNo");
		String faultType = getPara("faultType");
		String faultLevel = getPara("faultLevel");
		String reportName = getPara("reportName");
		// 设置查询参数
		Kv cond = Kv.create();
		if (StrKit.notBlank(repairName)) {
			cond.set("repair_name", repairName);
		}
		if (StrKit.notBlank(faultStatus)) {
			cond.set("status", faultStatus);
		}
		if (StrKit.notBlank(orderNo)) {
			cond.set("order_no", orderNo);
		}
		if (StrKit.notBlank(responsible)) {
			cond.set("responsible_dep", responsible);
		}
		if (StrKit.notBlank(faultType)) {
			cond.set("type", faultType);
		}
		if (StrKit.notBlank(faultLevel)) {
			cond.set("level", faultLevel);
		}
		if (StrKit.notBlank(reportName)) {
			cond.set("report_name", reportName);
		}

		Page<Record> page = service.list(pageNumber, pageSize, cond);

		PagerList<Record> list = new PagerList<Record>();
		list.setTotalRow(page.getTotalRow());
		list.setList(page.getList());
		renderJson(list);
	}

	/**
	 * 详情
	 * 
	 */
	public void detail() {
		int id = getParaToInt("id");
		JSONObject json = service.selOrderInfo(id);
		setAttr("x", json);
		render("detail.html");
	}


}
