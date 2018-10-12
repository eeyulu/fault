package com.ht.fault.order.model;

import io.rong.messages.BaseMessage;
import io.rong.util.GsonUtil;

public class FaultMessage extends BaseMessage{

    
	private String repairName;		//录入人
	private String repairTel;		//录入人电话
	private String repairDep;		//录入人部门
	private String location;		//报障位置
	private String faultType;		//故障类型
	private String level;			//紧急程度
	private String responsibleDep;	//负责部门	
	private String describe;		//故障描述
	private String desPicture;		//故障描述图片
	private String time;			//时间
	private String orderNo;			//工单号
	private Integer id;				//工单id
	
	//报修人信息
	private String reportName;
	private String reportStaffNo;
	private String reportTel;
	private String reportDep; 
	private String remark;
	
	//接单人userId
	private String repairUserId;
	private transient static final String TYPE = "FaultMes";
	


	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String toString() {
		return GsonUtil.toJson(this, FaultMessage.class);
	}

	public String getRepairName() {
		return repairName;
	}

	public void setRepairName(String repairName) {
		this.repairName = repairName;
	}

	public String getRepairTel() {
		return repairTel;
	}

	public void setRepairTel(String repairTel) {
		this.repairTel = repairTel;
	}

	public String getRepairDep() {
		return repairDep;
	}

	public void setRepairDep(String repairDep) {
		this.repairDep = repairDep;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getResponsibleDep() {
		return responsibleDep;
	}

	public void setResponsibleDep(String responsibleDep) {
		this.responsibleDep = responsibleDep;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getDesPicture() {
		return desPicture;
	}

	public void setDesPicture(String desPicture) {
		this.desPicture = desPicture;
	}


	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportStaffNo() {
		return reportStaffNo;
	}

	public void setReportStaffNo(String reportStaffNo) {
		this.reportStaffNo = reportStaffNo;
	}

	public String getReportTel() {
		return reportTel;
	}

	public void setReportTel(String reportTel) {
		this.reportTel = reportTel;
	}

	public String getReportDep() {
		return reportDep;
	}

	public void setReportDep(String reportDep) {
		this.reportDep = reportDep;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRepairUserId() {
		return repairUserId;
	}

	public void setRepairUserId(String repairUserId) {
		this.repairUserId = repairUserId;
	}

	
}	
