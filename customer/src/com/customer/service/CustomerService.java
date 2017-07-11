package com.customer.service;

import java.util.List;

import com.customer.dao.CustomerDao;
import com.customer.domain.Customer;
import com.customer.domain.PageBean;

public class CustomerService {
	private CustomerDao customerDao = new CustomerDao();
	/**
	 * ��ӿͻ�
	 */
	public void add(Customer cstm){
		customerDao.add(cstm);
	}
	/**
	 * ��ѯ���пͻ�
	 */
//	public List<Customer> findAll(){
//		return customerDao.findAll();
//	}
	public PageBean<Customer> findAll(int cp,int ps){
		return customerDao.findAll(cp,ps);
	}
	
	/**
	 * ���ؿͻ�
	 */
	public Customer load(String cid){
		return customerDao.load(cid);
	}
	/**
	 * �޸Ŀͻ���Ϣ
	 */
	public void edit(Customer cstm){
		customerDao.edit(cstm);
	}
	/**
	 * ɾ���û���Ϣ
	 */
	public void delete(String cid){
		customerDao.delete(cid);
	}
	//������ѯ
	public PageBean<Customer> query(int cp,int ps,Customer cstm){
		return customerDao.query(cp, ps, cstm);
	}
}
