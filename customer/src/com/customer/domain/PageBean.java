package com.customer.domain;

import java.util.List;

public class PageBean<T> {
	private int cp; // ��ǰҳ��
	private int tr; // �ܼ�¼��
	private int ps; // ÿҳ��¼��
	private List<T> beans;// ��ǰҳ������
	private int totalPage;//��ҳ��
	
	private int beginIndex;//ҳ���б�Ŀ�ʼҳ��
	private int endIndex;//ҳ���б�Ľ���ҳ��
	private int pageCodeListSize = 8;//ҳ���б���
	private int currPageCodeListIndex = 4;//��ǰҳ����ҳ���б��е�λ��
	private String url;//������contextPath + servletPath + queryString
	
	
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
	//���㿪ʼҳ��
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
