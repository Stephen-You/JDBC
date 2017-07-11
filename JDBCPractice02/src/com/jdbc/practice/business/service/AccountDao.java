package com.jdbc.practice.business.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import com.jdbc.practice.utils.JDBCUtilsNew;

/**
 * ��ǰ�������ѧϰ��,������DAO�в����Ǻܼ򵥵�Ҳ���������,��ʵ�ʿ��������Ǵ���ҵ������service��,DAo��ֻ�������ݿ��
 * ����,DAO�в��Ǵ�������ĵط�����ΪDAO�е�ÿ���������Ƕ����ݿ��һ�β�������Service�еķ�������
 * ��Ӧһ��ҵ���߼���Ҳ����˵������Ҫ��Service�е�һ�����е���DAO�Ķ������������Щ��
 * ��Ӧ����һ�������С�
 * ��DAO����������,������������һ��������,��ôҪ��֤��һ�����ӵĲ���,��ʱ������DAO���Լ���ȡ����,��Ӧ�������洫������
 * @author Yorick
 */
public class AccountDao {
	
	/*
	  * ��ָ���û����ָ�����
	  */
	 public void update(String name, double money) throws SQLException {
		 QueryRunner qr = new QueryRunner();//����QueryRunner
		 
		 // Ӧ�õõ�JdbcUtils.beginTransaction()ʱ���������Ӷ���
		 Connection con = JdbcUtils.getConnection();//�õ����Ӷ���
		 // ����sqlģ��
		 String sql = "update account set balance=balance+? where name=?";
		 //ʹ��con��sql�����в���������update����
		 qr.update(con, sql, money, name);
		 
		 JdbcUtils.releaseConnection(con);//�ͷ�����
	 }
	 
	 /*
	  * ��ָ���û����ָ�����
	  */
	 public void update1(String name, double money) throws SQLException {
		 QueryRunner qr = new TxQueryRunner();//����QueryRunner
		 String sql = "update account set balance=balance+? where name=?";
		 qr.update(sql, money, name);
	 }
	
	
	@Test
	public void test() throws SQLException{
		update("zs", (double) 10000);
	}
	
}
