package com.ht.fault.order;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ht.fault.common.kit.ResponseCode;
import com.ht.fault.common.kit.StringKit;
import com.ht.fault.order.code.OrderStatus;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;

public class ManageService {
	/**
	 * 数据列表
	 * 
	 * @return
	 * 
	 */
	public Page<Record> list(int pageNumber, int pageSize, Kv cond) {
		// 封装查询参数并返回sql
		SqlPara sqlPara = Db.getSqlPara("fault.findList", Kv.by("cond", cond));
		return Db.paginate(pageNumber, pageSize, sqlPara);

	}

	/**
	 * 查询工单详情
	 * 
	 * @param faultId
	 * @return
	 */
	public JSONObject selOrderInfo(Integer faultId) {
		JSONObject json = new JSONObject();
		Record fault = Db.findById("ht_im_fault_form", "id", faultId);
		json.fluentPut("fault", fault);

		if (fault.getInt("status") == OrderStatus.REPEAL) {
			// 已撤销
			return json;
		}

		if (fault.getInt("status") >= OrderStatus.TAKE) {
			// 抢单信息
			Record take = Db.findFirst(
					"select * from ht_im_order_take WHERE fault_id = ?",
					faultId);
			// 已搁置
			List<Record> shelve = Db
					.find("select * from ht_im_order_shelve WHERE fault_id = ? ORDER BY create_time ",
							faultId);
			json.fluentPut("take", take).fluentPut("shelve", shelve);
		}

		if (fault.getInt("status") >= OrderStatus.FINISH) {
			// 工程工单信息
			List<Record> solver = Db.find(
					"select * from ht_im_solver_info WHERE fault_id = ?",
					faultId);
			// Record input =
			// Db.findFirst("select * from ht_im_order_solve WHERE fault_id = ?",
			// faultId);
			// json.fluentPut("input", input).fluentPut("solver", solver);
			json.fluentPut("solver", solver);
		}
		if (fault.getInt("status") >= OrderStatus.RECEIVE) {
			// 评价信息
			Record comment = Db.findFirst(
					"select * from ht_im_order_comment WHERE fault_id = ?",
					faultId);
			json.fluentPut("comment", comment);
		}

		return json;
	}

	public Record findBaseUser(String userid) {
		return Db.use("oracle").findFirst(
				"select * from t_s_base_user where id = ?", userid);
	}

	public JSONObject releaseOrder(Record record, String userId) {
		JSONObject json = new JSONObject();

		Record user = findBaseUser(userId);
		Date date = new Date();
		String orderNo = "0000" + StringKit.getFixLenthString(6);

		record.set("repair_staffno", user.getStr("STAFF_NO"))
				.set("repair_name", user.getStr("REALNAME"))
				.set("repair_tel", user.getStr("USERNAME"))
				.set("repair_dep", user.getStr("STAFF_DEPTNAME"))
				.set("repair_depid", user.getStr("STAFF_DEPT"))
				.set("repair_userid", userId)
				.set("status", OrderStatus.RELEASE).set("order_no", orderNo)
				.set("repair_time", date).set("update_time", date);

		boolean b = Db.save("ht_im_fault_form", record);
		if (b) {
			json.put("code", ResponseCode.HT_IM_SUCCESS);
		} else {
			json.put("code", ResponseCode.HT_IM_ERROR);
		}
		return json;
	}

	public JSONObject updateOrder(Record record) {
		JSONObject json = new JSONObject();
		Date date = new Date();
		record.set("update_time", date);
		boolean b =Db.update("ht_im_fault_form", record);
		if (b) {
			json.put("code", ResponseCode.HT_IM_SUCCESS);
		} else {
			json.put("code", ResponseCode.HT_IM_ERROR);
		}
		return json;
	}

	public Integer orderStatus(Integer id) {
		return Db.queryInt("select status from ht_im_fault_form where id = ?", id);
	}

	public JSONObject updateOrderTake(Record record) {
		JSONObject json = new JSONObject();
		boolean b =Db.update("ht_im_order_take", record);
		if (b) {
			json.put("code", ResponseCode.HT_IM_SUCCESS);
		} else {
			json.put("code", ResponseCode.HT_IM_ERROR);
		}
		return json;
	}

	public JSONObject updateSolver(List<Record> solverList, Integer faultId) {
		JSONObject json=new JSONObject();
		
		Db.update("delete from ht_im_solver_info where fault_id = ?", faultId);
		if(solverList.size()>0) {
			//保存解决人信息
			Db.batchSave("ht_im_solver_info", solverList, solverList.size());	
		}
		json.put("code", ResponseCode.HT_IM_SUCCESS);
		return json;
	}

}
