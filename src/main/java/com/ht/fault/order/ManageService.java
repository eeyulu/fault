package com.ht.fault.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ht.fault.common.kit.HtImDataResultKit;
import com.ht.fault.common.kit.ResponseCode;
import com.ht.fault.common.kit.RongCloudKit;
import com.ht.fault.common.kit.SqlKit;
import com.ht.fault.common.kit.StringKit;
import com.ht.fault.common.kit.TimeKit;
import com.ht.fault.order.code.OrderStatus;
import com.ht.fault.order.model.FaultMessage;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;

import io.rong.models.CodeSuccessResult;

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

	public JSONObject releaseOrder(Record record, String userId, FaultMessage message) {
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
		
		//推送工单-->负责部门员工
		String depId = record.getStr("responsible_depid");
		JSONObject userJson = HtImDataResultKit.findUserId("departmentId=" + depId);
		JSONArray userArr = userJson.getJSONArray("userIdList");
		String[] toUserId = new String[userArr.size()];
		userArr.toArray(toUserId);
		
//				String[] toUserId={"da461588dd1e4d53bb96bc6249969436","ac40dba6fd0b469e85710c65c87794d6"};
		String fromUserId = "4028814265a2a28a0165a2a3ef320000";	//故障工单推送-- alertFault
		message.setOrderNo(orderNo);
		message.setId(record.getInt("id"));
		message.setTime(TimeKit.dateToStrLong(date));
		message.setRepairName(user.getStr("REALNAME"));
		message.setRepairTel(user.getStr("USERNAME"));
		message.setRepairDep(user.getStr("STAFF_DEPTNAME"));
		
		CodeSuccessResult result = RongCloudKit.pushGroupMessage(fromUserId, toUserId, message,"故障工单");
				
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

	@Before(Tx.class)
	public JSONObject updateOrderTake(Record record, Record fault) {
		JSONObject json = new JSONObject();
		boolean b =Db.update("ht_im_order_take", record);
		boolean b1 =Db.update("ht_im_fault_form", fault);
		if (b&b1) {
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

	public JSONObject findTaker(String staffNO, String respDeptId) {
		JSONObject json=new JSONObject();
		Record taker = Db.use("oracle").findFirst(
				"select * from t_s_base_user where staff_no = ?", staffNO);
		if(null == taker) {
			json.put("code", ResponseCode.HT_IM_ERROR);
		}else {
			String takerDeptId = taker.getStr("STAFF_DEPT");
			
			//查询某部门所有父部门
			List<String> deptList = Db.use("oracle").query(" SELECT DEPTPID FROM  t_s_base_dept START WITH DEPTID = ? CONNECT BY PRIOR DEPTPID = DEPTID", takerDeptId);
			deptList.add(takerDeptId);
			
			if(deptList.contains(respDeptId)) {
				json.put("code", ResponseCode.HT_IM_SUCCESS);
				json.put("result", taker);
			}else {
				json.put("code", ResponseCode.FAULT_NOTIN_DEPT);
			}
		}
		return json;
	}

	public JSONObject findAllStaff(String deptId) {
		JSONObject json=new JSONObject();
		//某部门下所有员工
		List<Record> staffList = Db.use("oracle").find("SELECT * FROM t_s_base_user WHERE STAFF_DEPT IN " +
				"( select DEPTID from t_s_base_dept start with DEPTPID = ? connect by PRIOR DEPTID = DEPTPID ) or STAFF_DEPT = ?", deptId, deptId);	
		
		if(null != staffList) {
			json.put("code", ResponseCode.HT_IM_SUCCESS);
			json.put("result", staffList);
		}else {
			json.put("code", ResponseCode.HT_IM_ERROR);
		}
		return json;
	}

	
	public Page<Record> findDepStaff(String userId, int pageNumber, int pageSize) {
		//登录用户部门ID
		Record user = findBaseUser(userId);
		String deptId = user.getStr("STAFF_DEPT");
		//某部门下所有员工
		Page<Record> staffPage = Db.use("oracle").paginate(pageNumber, pageSize, "SELECT * ",
				"FROM t_s_base_user WHERE STAFF_DEPT IN ( select DEPTID from t_s_base_dept start with DEPTPID = ? connect by PRIOR DEPTID = DEPTPID ) or STAFF_DEPT = ?", deptId, deptId);	
		
		return staffPage;
	}

	public JSONObject average(String id) {
		JSONObject json=new JSONObject();
		
		BigDecimal score = new BigDecimal(0);	//平均得分
		Integer amount = 0;			//评价工单数量
		List<Integer> scoreList = Db.query("select c.`level` from ht_im_order_take t "
					+ "join ht_im_order_comment c on t.fault_id = c.fault_id where t.order_userid = ?", id);
		if(scoreList != null && scoreList.size()>0) {
			amount = scoreList.size();
			Integer sum = 0;
			for(Integer s : scoreList) {
				sum += s;
			}
			score = new BigDecimal(sum).divide(new BigDecimal(amount), 2, RoundingMode.HALF_UP);
		}
		json.put("amount", amount);
		json.put("score", score);
		return json;
	}

	public Page<Record> commentRec(Kv cond, int pageNumber, int pageSize) {
		// 封装查询参数并返回sql
		SqlPara sqlPara = Db.getSqlPara("fault.findCommentList", Kv.by("cond", cond));
		return Db.paginate(pageNumber, pageSize, sqlPara);

	}

	public List<Record> groupType(String userId) {
		//登录用户部门ID
		Record user = findBaseUser(userId);
		String deptId = user.getStr("STAFF_DEPT");
		List<Integer > deptList = Db.use("oracle").query("select DEPTID from t_s_base_dept start with DEPTPID = ? connect by PRIOR DEPTID = DEPTPID", deptId);
		deptList.add(Integer.valueOf(deptId));
		//故障分类
		StringBuilder dept = new StringBuilder();
		SqlKit.joinIds(deptList, dept);
		List<Record> groupType = Db.find("select  count(*) value, f.`type` name from ht_im_order_take t JOIN ht_im_fault_form f ON t.fault_id = f.id where f.`status` != 5 and t.order_deptid in "+dept.toString()+" GROUP BY `type` ");
		return groupType;
	}

	public List<Record> findDepStaff(String userId) {
		//登录用户部门ID
		Record user = findBaseUser(userId);
		String deptId = user.getStr("STAFF_DEPT");
		//某部门下所有员工
		List<Record> userList = Db.use("oracle").find( "SELECT id,realname FROM t_s_base_user WHERE STAFF_DEPT IN ( select DEPTID from t_s_base_dept start with DEPTPID = ? connect by PRIOR DEPTID = DEPTPID ) or STAFF_DEPT = ?", deptId, deptId);	
		
		return userList;
	}

	public Integer faultSum(String id) {
		return  Db.queryInt("select  count(*) from ht_im_order_take t JOIN ht_im_fault_form f ON t.fault_id = f.id where f.`status` != 5 and t.order_userid=?", id);
	}

}
