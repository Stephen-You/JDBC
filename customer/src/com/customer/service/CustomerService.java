package com.customer.service;

import java.util.List;

import com.customer.dao.CustomerDao;
import com.customer.domain.Customer;
import com.customer.domain.PageBean;

public class CustomerService {
	private CustomerDao customerDao = new CustomerDao();
	/**
	 * 添加客户
	 */
	public void add(Customer cstm){
		customerDao.add(cstm);
	}
	/**
	 * 查询所有客户
	 */
//	public List<Customer> findAll(){
//		return customerDao.findAll();
//	}
	public PageBean<Customer> findAll(int cp,int ps){
		return customerDao.findAll(cp,ps);
	}
	
	/**
	 * 加载客户
	 */
	public Customer load(String cid){
		return customerDao.load(cid);
	}
	/**
	 * 修改客户信息
	 */
	public void edit(Customer cstm){
		customerDao.edit(cstm);
	}
	/**
	 * 删除用户信息
	 */
	public void delete(String cid){
		customerDao.delete(cid);
	}
	//条件查询
	public PageBean<Customer> query(int cp,int ps,Customer cstm){
		return customerDao.query(cp, ps, cstm);
	}
}
