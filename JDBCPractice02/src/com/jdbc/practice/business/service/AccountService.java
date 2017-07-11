package com.jdbc.practice.business.service;

import java.sql.SQLException;

import org.junit.Test;

public class AccountService {
	private AccountDao dao = new AccountDao();
	/**
	 * ת�˹���
	 * @param from
	 * @param to
	 * @param money
	 * 
	 */
	public void zz(String from,String to,double money){
		try {
			//��������
			JdbcUtils.beginTransaction();
			dao.update(from, money);
			dao.update(to, -money);
			//�ύ����
			JdbcUtils.commitTransaction();
		} catch (Exception e) {
			//���е�����,˵��Ҫ�ع�����
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	@Test
	public void test(){
		zz("zs", "ls", 10000);
	}
}