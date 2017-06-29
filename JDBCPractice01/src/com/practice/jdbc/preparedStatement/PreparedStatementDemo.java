package com.practice.jdbc.preparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class PreparedStatementDemo {
	private final String name = "";
	@Test
	public void test() throws SQLException, ClassNotFoundException{
		String username = "a' or 'a'='a";
		String password = "a' or 'a'='a";
		boolean flag = login(username, password);
		//���ǲ鿴���ݿⷢ�ֲ�û���û�a,����ȴ������true,�����SQL������һ������,����Ҫ���ľ��Ƿ�ֹ�ͻ��˽������ֹ���
		System.out.println(flag);
		flag = loginPreparedStatement(username, password);
		System.out.println(flag);
	}
	
	//ʵ��SQL����
	public boolean login(String username,String password) throws SQLException, ClassNotFoundException{
		Connection conn = null;
		Statement sql = null;
		ResultSet set = null;
		Class.forName("com.mysql.jdbc.Driver");
		//try��ʵ����,��ȡ����
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "123");
		sql = conn.createStatement();
		String select = "SELECT * FROM user WHERE username='" + username + "' and password='" + password + "'";
//		String select = "select * from user where username = '"+username+"' and password = '"+password+"'";
		System.out.println("��ѯ���:"+select);
		set = sql.executeQuery(select);
//			while (set.next()) {
//				String uid = set.getString(1);
//				String username1 = set.getString("username");
//				String password1 = set.getString("password");
//				System.out.println(uid+":"+username1+":"+password1);
//			}
		return set.next();
	}
	//PreparedStatement��SQL����
	public boolean loginPreparedStatement(String username,String password) throws ClassNotFoundException, SQLException{
		//����MySQL����
		Class.forName("com.mysql.jdbc.Driver");
		//��ȡ���ݿ�����
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "123");
		//SQLģ��
		String select = "select * from user where username=? and password=?";
		//ʹ��SQLģ��õ�PreparedStatementʵ��
		PreparedStatement pstml = conn.prepareStatement(select);
		//��SQLģ���е��ʺŸ�ֵ
		pstml.setString(1, username);
		pstml.setString(2, password);
		//pstml:com.mysql.jdbc.JDBC4PreparedStatement@fbc9c9: select * from user where username='a\' or \'a\'=\'a' and password='a\' or \'a\'=\'a'
		System.out.println(select);
		//��ȡ�����
		ResultSet set = pstml.executeQuery();
		return set.next();
	}
}
