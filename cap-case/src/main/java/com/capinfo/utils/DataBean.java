package com.capinfo.utils;

import java.util.List;

public class DataBean {
	 private int pageNo;
     private int pageSize;
     private int count;
     private int totalPage;
     private List<ListBean> list;
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<ListBean> getList() {
		return list;
	}
	public void setList(List<ListBean> list) {
		this.list = list;
	}
     

}
