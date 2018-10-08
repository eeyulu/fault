package com.ht.fault.order;

import java.util.List;

public class PagerList<T> {
	
	private String msg="请求成功";
	
	private int code = 200;
	
	private List<T> list;	
	
	private int totalRow;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}
	
	

}
