package com.jdbc.practice.pool.jndi;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JNDIPracticeDAO {
	public void xxx(){
		//��ȡJNDI��Դ
		try {
			//����������
			Context initCtx = new InitialContext();
			/*
			 * ʹ�������Ĳ���ָ�����Ƶ���Դ
			 * ���ƣ�java:comp/env����ʾ��ڵ㣡�õ��Ļ���������
			 */
//			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			/*
			 * �̳в�����Դ������Ϊ��jdbc/mydatsource������Ӧ����<Resource name="jdbc/mydatsource">
			 */
//			DataSource dataSource = (DataSource) envCtx.lookup("jdbc/test1");
			DataSource dataSource = (DataSource) initCtx.lookup("java:comp/env/jdbc/test1");
			
//			DataSource dataSource = (DataSource) envCtx.lookup("jdbc/mydatasource");
			//��ȡ����
			Connection conn = dataSource.getConnection();
			System.out.println(conn.getClass().getName());
			//�黹����
			conn.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}