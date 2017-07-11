package com.customer.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {
	private static DataSource dataSource = new ComboPooledDataSource();
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	//��������
	public static void beginTransaction() throws SQLException{
		Connection con = threadLocal.get();
		if(con!=null){
			throw new SQLException("�����Ѿ�����,�����ظ�����!");
		}
		con = dataSource.getConnection();
		threadLocal.set(con);
		con.setAutoCommit(false);
	}
	//�ύ����
	public static void commitTransaction() throws SQLException{
		Connection con = threadLocal.get();
		if(con==null){
			throw new SQLException("���񲻴���,�����ύ!");
		}
		con.commit();
		con.close();
		threadLocal.remove();
	}
	//�ع�����
	public static void rollbackTransaction() throws SQLException{
		Connection con = threadLocal.get();
		if(con==null){
			throw new SQLException("���񲻴���,���ܻع�!");
		}
		con.rollback();
		con.close();
		threadLocal.remove();
	}
	//��ȡ���ӳض���
	public static DataSource getDataSource(){
		return dataSource;
	}
	//��ȡ����
	public static Connection getConnection() throws SQLException{
		Connection con = threadLocal.get();
		if(con==null){//˵��û�п�������
			return dataSource.getConnection();
		}
		//���е�����,˵����������
		return con;
	}
	//�ͷ�����
	public static void releaseConnnection(Connection con) throws SQLException{
		Connection thCon = threadLocal.get();
		if(con==null) return;
		if(con==thCon)
			//�����ǰҪ�ͷŵ����Ӿ��ǵ�ǰ�̵߳�ר���������ӣ���ô���أ�
			return;
		//������ǵ�ǰ�̵߳�����ר�����ӣ���ô�ر�����
		con.close();
		
	}
}