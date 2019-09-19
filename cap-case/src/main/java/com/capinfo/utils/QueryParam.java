package com.capinfo.utils;

import java.util.List;

public class QueryParam {
	private String beginStartTime;
	private String endStartTime;
	private String beginCallTime;
	private String endCallTime;
	private List<String> orderUnitId;
	private String orderId;
	private Integer pageNo;
	private Integer pageSize;
	public String getBeginStartTime() {
		return beginStartTime;
	}
	public void setBeginStartTime(String beginStartTime) {
		this.beginStartTime = beginStartTime;
	}
	public String getEndStartTime() {
		return endStartTime;
	}
	public void setEndStartTime(String endStartTime) {
		this.endStartTime = endStartTime;
	}
	public String getBeginCallTime() {
		return beginCallTime;
	}
	public void setBeginCallTime(String beginCallTime) {
		this.beginCallTime = beginCallTime;
	}
	public String getEndCallTime() {
		return endCallTime;
	}
	public void setEndCallTime(String endCallTime) {
		this.endCallTime = endCallTime;
	}
	public List<String> getOrderUnitId() {
		return orderUnitId;
	}
	public void setOrderUnitId(List<String> orderUnitId) {
		this.orderUnitId = orderUnitId;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
