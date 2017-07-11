package com.customer.domain;

import java.util.List;

public class PageBean<T> {
	private int cp; // 当前页码
	private int tr; // 总记录数
	private int ps; // 每页记录数
	private List<T> beans;// 当前页的数据
	private int totalPage;//总页数
	
	private int beginIndex;//页码列表的开始页码
	private int endIndex;//页码列表的结束页码
	private int pageCodeListSize = 8;//页码列表长度
	private int currPageCodeListIndex = 4;//当前页码在页码列表中的位置
	private String url;//保留：contextPath + servletPath + queryString
	
	
	public int getTotalPage() {
		totalPage = tr / ps;
		return tr%ps==0 ? totalPage : totalPage + 1;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}


	public int getTr() {
		return tr;
	}

	public void setTr(int tr) {
		this.tr = tr;
	}

	public int getPs() {
		return ps;
	}

	public void setPs(int ps) {
		this.ps = ps;
	}

	public List<T> getBeans() {
		return beans;
	}

	public void setBeans(List<T> beans) {
		this.beans = beans;
	}
	//计算开始页面
	public int getBeginIndex() {
		if(totalPage<=pageCodeListSize) return 1;
		int begin = cp - currPageCodeListIndex+1;
		if(begin<1) begin = 1;
		int end = begin+pageCodeListSize-1;
		if(end > totalPage){
			begin = totalPage - pageCodeListSize + 1;
		} 
		return begin;
	}
	public int getEndIndex() {
		if(totalPage<pageCodeListSize) return totalPage;
		int end = cp + (pageCodeListSize - currPageCodeListIndex);
		if(end > totalPage){
			end = totalPage;
		}
		if(end < pageCodeListSize){
			end = pageCodeListSize;
		}
		return end;
	}
}
