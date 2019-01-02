package com.ht.fault.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ht.fault.base.BaseController;
import com.ht.fault.common.interceptor.AuthInterceptor;
import com.ht.fault.common.kit.ResponseCode;
import com.ht.fault.order.code.OrderStatus;
import com.ht.fault.order.model.FaultMessage;
import com.jfinal.aop.Clear;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.xxl.sso.core.conf.Conf;
import com.xxl.sso.core.user.XxlUser;

/**
 * 
 */
 @Clear
//@Clear(AuthInterceptor.class)
public class ManageController extends BaseController {

//	private ManageService service = enhance(ManageService.class);
	private ManageService service =  enhance(ManageService.class);


	public void listHtml() {
		String status = PropKit.get("fault_status");
		JSONArray statuArr = JSON.parseArray(status);
		String[] typeArr = PropKit.get("fault_type").split(",");
		String[] levelArr = PropKit.get("fault_level").split(",");
		setAttr("statuArr", statuArr);
		setAttr("typeArr", typeArr);
		setAttr("levelArr", levelArr);

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
	
	/**
	 * 派单页面
	 * 
	 */
	public void sendOrder() {
		int id = getParaToInt("id");
		JSONObject json = service.selOrderInfo(id);
		setAttr("x", json);
		render("sendOrder.html");
	}

	/**
	 * 跳转编辑页面
	 * 
	 */
	public void goEdit() {
//		String status = PropKit.get("fault_status");
//		JSONArray statuArr = JSON.parseArray(status);
		String resp = PropKit.get("fault_responsible_dep");
		JSONArray reJson = JSON.parseArray(resp);
		String[] typeArr = PropKit.get("fault_type").split(",");
		String[] levelArr = PropKit.get("fault_level").split(",");
//		setAttr("statuArr", statuArr);
		setAttr("resp", reJson);
		setAttr("typeArr", typeArr);
		setAttr("levelArr", levelArr);

		int id = getParaToInt("id");
		JSONObject json = service.selOrderInfo(id);
		setAttr("x", json);
		Integer faultStatus = json.getJSONObject("fault").getJSONObject("columns").getInteger("status");
		if(faultStatus == OrderStatus.RELEASE){
			render("edit.html");
		}else if(faultStatus == OrderStatus.TAKE){
			render("editTake.html");
		}else if(faultStatus == OrderStatus.FINISH){
			render("editFinish.html");	
		}
//			render("edit.html");

	}

	/**
	 * 跳转新增页面
	 * 
	 */
	public void goAdd() {
		String resp = PropKit.get("fault_responsible_dep");
		JSONArray reJson = JSON.parseArray(resp);
//		JSONObject reJson = JSON.parseObject(resp);
		String[] typeArr = PropKit.get("fault_type").split(",");
		String[] levelArr = PropKit.get("fault_level").split(",");
		setAttr("typeArr", typeArr);
		setAttr("levelArr", levelArr);
		setAttr("resp",reJson);
		render("add.html");
	}
	
	/**
	 * 上传图片获取accessToken
	 * 
	 */
	public void getToken(){
		String tokenUrl = PropKit.get("tokenUrl");
		String appId = PropKit.get("appId");
		String appSecret = PropKit.get("appSecret");
		
		Map<String, String> paras = new HashMap<String, String>();
		paras.put("appId", appId);
		paras.put("appSecret", appSecret);
		String reply = HttpKit.get(tokenUrl, paras);
		System.out.println(reply);
		JSONObject json = JSONObject.parseObject(reply);
		renderJson(json);
	}

	/**
	 * 新增工单
	 */
	public void save() {
		try {
			XxlUser xxlUser = (XxlUser) getAttr(Conf.SSO_USER);
			
			String location = getPara("location");
			String type = getPara("type");
			String level = getPara("level");
			String responsibleDep = getPara("responsible_dep");
			Integer responsibleDepId=getParaToInt("responsible_depid");	
			String describe = getPara("describe");
			String desPicture = getPara("desPicture");
			//报修人信息
			String reportName = getPara("report_name");
			String reportStaffNo = getPara("report_staffno");
			String reportTel = getPara("report_tel");
			String reportDep = getPara("report_dep");
			//备注
			String remark = getPara("remark");

			Record record = new Record()
			  .set("location", location)
			  .set("type", type)
			  .set("level", level)
			  .set("responsible_dep", responsibleDep)
			  .set("responsible_depid", responsibleDepId)
			  .set("describe", describe)
			  .set("des_picture", desPicture)
			  .set("report_name", reportName)
			  .set("report_staffno", reportStaffNo)
			  .set("report_tel", reportTel)
			  .set("report_dep", reportDep)
			  .set("remark", remark);
			
			FaultMessage message = new FaultMessage();
			message.setLocation(location);
			message.setFaultType(type);
			message.setLevel(level);
			message.setResponsibleDep(responsibleDep);
			message.setDescribe(describe);
			message.setDesPicture(desPicture);
			message.setReportName(reportName);
			message.setReportStaffNo(reportStaffNo);
			message.setReportTel(reportTel);
			message.setReportDep(reportDep);
			message.setRemark(remark);
			message.setRepairUserId(xxlUser.getUserid());
			JSONObject jsonObject = service.releaseOrder(record,xxlUser.getUserid(),message);
			if(jsonObject.getInteger("code") == ResponseCode.HT_IM_SUCCESS){
				renderJson(jsonObject.get("result"), jsonObject.getInteger("code"), "发布成功！");
			}else{
				renderJson(null, ResponseCode.HT_IM_ERROR, "发布失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(null, ResponseCode.HT_IM_SERVER_ERROR,ResponseCode.HT_IM_SERVER_ERROR_MSG);
		}
	}
	
	/**
	 * 派单(保存接单人信息)
	 */
	public void saveTake() {
		try {
			Integer faultId = getParaToInt("fault_id");
			Integer status = service.orderStatus(faultId);
			if(status != OrderStatus.RELEASE){
				renderJson(null, ResponseCode.FAULT_STATUS_UPDATE, ResponseCode.FAULT_STATUS_UPDATE_MSG);
				return;
			}
			
			Date date = new Date();
			Record take = new Record()
			.set("fault_id", getParaToInt("fault_id"))
			.set("order_tel", getPara("order_tel"))
			.set("update_time", date)
			.set("order_time",date)
			.set("order_deptid", getPara("order_deptid"))
			.set("order_dept", getPara("order_dept"))
			.set("order_staffno", getPara("order_staffno"))
			.set("order_name", getPara("order_name"))
			.set("order_userid", getPara("order_userid"));
			
			Record fault = new Record()
			.set("id", getParaToInt("fault_id"))
			.set("update_time", date)
			.set("status", OrderStatus.TAKE);
					
			JSONObject jsonObject = service.saveOrderTake(take,fault);
			if(jsonObject.getInteger("code") == ResponseCode.HT_IM_SUCCESS){
				renderJson(jsonObject.get("result"), jsonObject.getInteger("code"), "发布成功！");
			}else{
				renderJson(null, ResponseCode.HT_IM_ERROR, "发布失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(null, ResponseCode.HT_IM_SERVER_ERROR,ResponseCode.HT_IM_SERVER_ERROR_MSG);
		}
	}
	
	/**
	 * 修改接单信息
	 */
	public void updateTake() {
		try {
			
			Integer faultId = getParaToInt("faultId");
			Integer status = service.orderStatus(faultId);
			if(status != OrderStatus.TAKE){
				renderJson(null, ResponseCode.FAULT_STATUS_UPDATE, ResponseCode.FAULT_STATUS_UPDATE_MSG);
				return;
			}
			Date date = new Date();
			//工单基本信息
//			String type = getPara("type");
//			String level = getPara("level");
//			String responsible_depid = getPara("responsible_depid");
//			String location = getPara("location");
//			String describe = getPara("describe");
//			String remark = getPara("remark");
//			String report_name = getPara("report_name");
//			String report_staffno = getPara("report_staffno");
//			String report_tel = getPara("report_tel");
//			String report_dep = getPara("report_dep");
			
			Record fault = new Record()
					.set("id", faultId)
					.set("type", getPara("type"))
					.set("level", getPara("level"))
					.set("responsible_depid", getPara("responsible_depid"))
					.set("location", getPara("location"))
					.set("describe", getPara("describe"))
					.set("remark", getPara("remark"))
					.set("report_name", getPara("report_name"))
					.set("report_staffno", getPara("report_staffno"))
					.set("report_tel", getPara("report_tel"))
					.set("report_dep", getPara("report_dep"))
					.set("update_time", date);
			
			//接单信息
//			Integer id = getParaToInt("id");
//			String orderTel = getPara("order_tel");
//			String order_deptid = getPara("order_deptid");
//			String order_dept = getPara("order_dept");
//			String order_staffno = getPara("order_staffno");
//			String order_name = getPara("order_name");
//			String order_userid = getPara("order_userid");
			
			Record take = new Record()
			.set("id", getParaToInt("id"))
			.set("order_tel", getPara("order_tel"))
			.set("update_time", date)
			.set("order_deptid", getPara("order_deptid"))
			.set("order_dept", getPara("order_dept"))
			.set("order_staffno", getPara("order_staffno"))
			.set("order_name", getPara("order_name"))
			.set("order_userid", getPara("order_userid"));
			
			JSONObject jsonObject = service.updateOrderTake(take,fault);
			if(jsonObject.getInteger("code") == ResponseCode.HT_IM_SUCCESS){
				renderJson(jsonObject.get("result"), jsonObject.getInteger("code"), "发布成功！");
			}else{
				renderJson(null, ResponseCode.HT_IM_ERROR, "发布失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(null, ResponseCode.HT_IM_SERVER_ERROR,ResponseCode.HT_IM_SERVER_ERROR_MSG);
		}
	}
	/**
	 * 修改解决人信息
	 */
	public void updateSolver() {
		try {
			
			String param = getPara("param");	
			JSONObject Json=JSON.parseObject(param);
			
			Integer faultId=Json.getInteger("faultId");				//故障工单id
			Integer status = service.orderStatus(faultId);
			if(status != OrderStatus.FINISH){
				renderJson(null, ResponseCode.FAULT_STATUS_UPDATE, ResponseCode.FAULT_STATUS_UPDATE_MSG);
				return;
			}
			JSONArray solverArr =Json.getJSONArray("solver");
			
			Date date = new Date();
			List<Record> solverList = new ArrayList<Record>();
			if(null != solverArr) {
				for(int i=0; i<solverArr.size(); i++) {
					JSONObject json =solverArr.getJSONObject(i);
					
					Record solver =new Record().set("fault_id",faultId)
							.set("solver", json.getString("solver"))
							.set("solver_dep", json.getString("solverDep"))
							.set("solver_tel", json.getString("solverTel"))
							.set("solve_problem", json.getString("solveProblem"))
							.set("solve_time", date)
							.set("update_time",date);
					solverList.add(solver);
				}
			}
			
			JSONObject jsonObject = service.updateSolver(solverList,faultId);
			if(jsonObject.getInteger("code") == ResponseCode.HT_IM_SUCCESS){
				renderJson(jsonObject.get("result"), jsonObject.getInteger("code"), "发布成功！");
			}else{
				renderJson(null, ResponseCode.HT_IM_ERROR, "发布失败！");
			}
		}catch (Exception e) {
			e.printStackTrace();
			renderJson(null, ResponseCode.HT_IM_SERVER_ERROR,ResponseCode.HT_IM_SERVER_ERROR_MSG);
		}
	}
	
	/**
	 * 修改工单
	 */
	public void update() {
		try {
			
			Integer id = getParaToInt("id");
			Integer status = service.orderStatus(id);
			if(status != OrderStatus.RELEASE){
				renderJson(null, ResponseCode.FAULT_STATUS_UPDATE, ResponseCode.FAULT_STATUS_UPDATE_MSG);
				return;
			}
			String describe = getPara("describe");
			String level = getPara("level");
			String location = getPara("location");
			String remark = getPara("remark");			
			String type = getPara("type");
			
			String responsibleDep = getPara("responsible_dep");
			Integer responsibleDepId=getParaToInt("responsible_depid");	
//			String desPicture = getPara("desPicture");
			//报修人信息
			String reportName = getPara("report_name");
			String reportStaffNo = getPara("report_staffno");
			String reportTel = getPara("report_tel");
			String reportDep = getPara("report_dep");
			
			Record record = new Record()
			.set("id", id)
			.set("location", location)
			.set("type", type)
			.set("level", level)
			.set("responsible_dep", responsibleDep)
			.set("responsible_depid", responsibleDepId)
			.set("describe", describe)
//			.set("des_picture", desPicture)
			.set("report_name", reportName)
			.set("report_staffno", reportStaffNo)
			.set("report_tel", reportTel)
			.set("report_dep", reportDep)
			.set("remark", remark);
			JSONObject jsonObject = service.updateOrder(record);
			if(jsonObject.getInteger("code") == ResponseCode.HT_IM_SUCCESS){
				renderJson(jsonObject.get("result"), jsonObject.getInteger("code"), "发布成功！");
			}else{
				renderJson(null, ResponseCode.HT_IM_ERROR, "发布失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(null, ResponseCode.HT_IM_SERVER_ERROR,ResponseCode.HT_IM_SERVER_ERROR_MSG);
		}
	}

	/**
	 * 撤销工单
	 */
	public void repeal(){
		try {
			Integer id = getParaToInt("id");
			Integer status = service.orderStatus(id);
			if(status != OrderStatus.RELEASE){
				renderJson(null, ResponseCode.FAULT_STATUS_UPDATE, ResponseCode.FAULT_STATUS_UPDATE_MSG);
				return;
			}
			Record record = new Record()
			.set("id", id)
			.set("status", OrderStatus.REPEAL)
			.set("update_time", new Date());
			JSONObject jsonObject = service.updateOrder(record);
			if(jsonObject.getInteger("code") == ResponseCode.HT_IM_SUCCESS){
				renderJson(jsonObject.get("result"), jsonObject.getInteger("code"), "发布成功！");
			}else{
				renderJson(null, ResponseCode.HT_IM_ERROR, "发布失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(null, ResponseCode.HT_IM_SERVER_ERROR,ResponseCode.HT_IM_SERVER_ERROR_MSG);
		}
	}
	
	
	/**
	 * 添加接单人---暂遗弃
	 */
	public void findTaker() {
		try {
			String staffNO = getPara("staffNO");
			String respDeptId = getPara("respDeptId");

			JSONObject jsonObject = service.findTaker(staffNO,respDeptId);
			if(jsonObject.getInteger("code") == ResponseCode.HT_IM_SUCCESS){
				renderJson(jsonObject.get("result"), jsonObject.getInteger("code"), "发布成功！");
			}else if(jsonObject.getInteger("code") == ResponseCode.FAULT_NOTIN_DEPT){
				renderJson(null, ResponseCode.FAULT_NOTIN_DEPT, ResponseCode.FAULT_NOTIN_DEPT_MSG);
			}
			else{
				renderJson(null, ResponseCode.HT_IM_ERROR, "该工号不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(null, ResponseCode.HT_IM_SERVER_ERROR,ResponseCode.HT_IM_SERVER_ERROR_MSG);
		}
	}
	
	/**
	 * 查询部门下所有员工---修改工单接单人
	 */
	public void findAllStaff() {
		try {
			String respDeptId = getPara("respDeptId");

			JSONObject jsonObject = service.findAllStaff(respDeptId);
			if(jsonObject.getInteger("code") == ResponseCode.HT_IM_SUCCESS){
				renderJson(jsonObject.get("result"), jsonObject.getInteger("code"), "请求成功！");
			}else{
				renderJson(null, ResponseCode.HT_IM_ERROR, "该部门暂无员工！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(null, ResponseCode.HT_IM_SERVER_ERROR,ResponseCode.HT_IM_SERVER_ERROR_MSG);
		}
	}
	
	/**
	 * 人员评分
	 */
	public void scoreHtml() {

		render("score.html");
	}
	
	/**
	 * 人员评分
	 */
	public void staffScore() {
		int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("limit");
		
		XxlUser xxlUser = (XxlUser) getAttr(Conf.SSO_USER);
		String userId =  xxlUser.getUserid();
		//隶属部门人员
		Page<Record> page = service.findDepStaff(userId,pageNumber,pageSize);
		
		//获取人员评分
		for(Record r : page.getList()) {
			String id = r.get("id");
			JSONObject json = service.average(id);
			r.set("amount", json.getInteger("amount"));
			r.set("score", json.getBigDecimal("score"));
		}
		
		PagerList<Record> list = new PagerList<Record>();
		list.setTotalRow(page.getTotalRow());
		list.setList(page.getList());
		renderJson(list);
	}
	
	
	/**
	 * 维修评价记录
	 */
	public void record() {
		String[] typeArr = PropKit.get("fault_type").split(",");
		setAttr("typeArr", typeArr);
		String userId = getPara("userId");
		setAttr("userId", userId);
		render("record.html");
	}
	
	/**
	 * 维修评价记录
	 */
	public void commentRec() {
		// 设置查询参数
		Kv cond = Kv.create();
		
		int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("limit");
		String userId = getPara("userId");
		cond.set("t.order_userid", userId);
		
		String repairName = getPara("repairName");
		String orderNo = getPara("orderNo");
		String faultType = getPara("faultType");
		String faultLevel = getPara("faultLevel");
		
		if (StrKit.notBlank(repairName)) {
			cond.set("repair_name", repairName);
		}
		if (StrKit.notBlank(orderNo)) {
			cond.set("order_no", orderNo);
		}
		if (StrKit.notBlank(faultType)) {
			cond.set("type", faultType);
		}
		if (StrKit.notBlank(faultLevel)) {
			cond.set("c.`level`", faultLevel);
		}
		
		//维修评价记录
		Page<Record> page = service.commentRec(cond,pageNumber,pageSize);
		
		PagerList<Record> list = new PagerList<Record>();
		list.setTotalRow(page.getTotalRow());
		list.setList(page.getList());
		renderJson(list);
	}
	
	/**
	 * 用户首页
	 */
	public void userIndex() {
		render("index.html");
	}
	
	/**
	 * 故障类型分布(饼图)
	 */
	public void faultPie() {
		XxlUser xxlUser = (XxlUser) getAttr(Conf.SSO_USER);
		String userId =  xxlUser.getUserid();
		List<Record> group = service.groupType(userId);
		renderJson(group);
	}
	
	/**
	 * 人员工单数量(柱状图)
	 */
	public void faultColumnar() {
		
		XxlUser xxlUser = (XxlUser) getAttr(Conf.SSO_USER);
		String userId =  xxlUser.getUserid();
		//隶属部门人员
		List<Record> userList = service.findDepStaff(userId);
		for(Record user : userList) {
			String id = user.getStr("id");
			//总接单数
			Integer sum = service.faultSum(id);
			user.set("sum", sum);
		}
		renderJson(userList);
	}
}
