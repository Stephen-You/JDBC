package com.jdbc.practice.business.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * JdbcUtils 3.0
 * @author Yorick
 * ���������񻷾���ʹ�ã�Ҳ�����ڷ����񻷾���ʹ�ã�
 * ��Ҫ֧�ֶ��̲߳�������
 * ��Ҫȥ��dao��
 */
public class JdbcUtils {
	//����c3p0���ӳ�
	private static DataSource dataSource = new ComboPooledDataSource();
	//��ҪΪÿ���߳��ṩһ���Լ�������ר������
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	/**
	 * ��������
	 * @throws SQLException 
	 */
	public static void beginTransaction() throws SQLException{
		Connection con = threadLocal.get();//��ȡ��ǰ�߳�ר�õ���������
		if(con!=null){
			throw new SQLException("�����Ѿ�����,�����ظ�����!");
		}
		//�ߵ�����,˵��conΪnull,��con��ֵ,��������
		con = dataSource.getConnection();
		con.setAutoCommit(false);
		//�ѵ�ǰ�̵߳�����ר�����ӱ��浽tl�У��´δ��߳��е��������������Ϳ��Ի�ȡ���ˣ�
		threadLocal.set(con);
	}
	/**
	 * �ύ����
	 * @throws SQLException 
	 */
	public static void commitTransaction() throws SQLException{
		Connection con = threadLocal.get();
		if(con==null){
			throw new SQLException("���񲻴���,�����ύ!");
		}
		//�ߵ���һ��,˵����ȡ������������,˵����������,��ʱ�ύ����,�ر�����,����ǰ�߳����Ӵ�ThreadLocal���Ƴ�
		con.commit();
		con.close();
		threadLocal.remove();
	}
	/**
	 * �ع�����
	 * @throws SQLException 
	 */
	public static void rollbackTransaction() throws SQLException{
		Connection con = threadLocal.get();
		if(con==null){
			throw new SQLException("���񲻴���,���ܻع�!");
		}
		con.rollback();
		con.close();
		threadLocal.remove();
	}
	/**
	 * ��ȡ���ӳ�
	 */
	public static DataSource getDataSource(){
		return dataSource;
	}
	/**
	 * ʹ�ñ�������Ҫ������ṩc3p0�������ļ���
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException{
		Connection con = threadLocal.get();
		if(con==null){//˵��û�п�������
			return dataSource.getConnection();
		}
		//˵������������,������������ص�����
		return con;
	}
	/**
	 * �ͷ�����
	 * @throws SQLException 
	 */
	public static void releaseConnection(Connection con) throws SQLException{
		if(con==null) return;
		Connection thCon = threadLocal.get();
		if(thCon==con)
			return;//�����ǰҪ�ͷŵ����Ӿ��ǵ�ǰ�̵߳�ר���������ӣ���ô���أ�
		con.close();//������ǵ�ǰ�̵߳�����ר�����ӣ���ô�ر�����
	}
}
