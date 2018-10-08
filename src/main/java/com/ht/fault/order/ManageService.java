package com.ht.fault.order;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ht.fault.order.code.OrderStatus;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;

public class ManageService {
	/**
	 * 数据列表
	 * @return 
	 * 
	 */
	public Page<Record> list(int pageNumber, int pageSize, Kv cond) {
        //封装查询参数并返回sql
		SqlPara sqlPara =Db.getSqlPara("fault.findList",Kv.by("cond",cond));
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
		
		if(fault.getInt("status") == OrderStatus.REPEAL){
			//已撤销
			return json;
		}
		
		if(fault.getInt("status") >= OrderStatus.TAKE){
			//抢单信息
			Record take = Db.findFirst("select * from ht_im_order_take WHERE fault_id = ?", faultId);
			//已搁置
			List<Record>  shelve = Db.find("select * from ht_im_order_shelve WHERE fault_id = ? ORDER BY create_time ", faultId);
			json.fluentPut("take", take).fluentPut("shelve", shelve);
		}
		
		if(fault.getInt("status") >= OrderStatus.FINISH){
			//工程工单信息
			List<Record> solver = Db.find("select * from ht_im_solver_info WHERE fault_id = ?", faultId);
//			Record input = Db.findFirst("select * from ht_im_order_solve WHERE fault_id = ?", faultId);
//			json.fluentPut("input", input).fluentPut("solver", solver);
			json.fluentPut("solver", solver);
		}
		if(fault.getInt("status") >= OrderStatus.RECEIVE){
			//评价信息
			Record comment = Db.findFirst("select * from ht_im_order_comment WHERE fault_id = ?", faultId);
			json.fluentPut("comment", comment);
		}
		
		return json;
	}


}
