package com.practice.jdbc.preparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class PreparedStatementPractice {
	
	@Test
	public void test() throws Exception{
		User user = new User();
//		user.setUid("U_1002");
		user.setUid("U_1003");
		user.setUsername("����");
		user.setPassword("lisi");
//		addUser1(user);
		addUser2(user);
		
	}

	//����һ���������ݿ�,ʹ��Statement
	public void addUser1(User user) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "123");
		Statement sql = conn.createStatement();
		String insert = "insert into user values('"+user.getUid()+"','"+user.getUsername()+"','"+user.getPassword()+"')";
		int l = sql.executeUpdate(insert);
		System.out.println(insert);
		System.out.println(l);
		//�ر�
		sql.close();
		conn.close();
	}
	//����һ���������ݿ�,ʹ��PreparedStatement
	public void addUser2(User user) throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root","123");
		//����SQLģ��
		String insert = "insert into user values(?,?,?)";
		//��ȡPreparedStatementʵ��,��ʼ��SQLģ��
		PreparedStatement pstmt = conn.prepareStatement(insert);
		//���ʺŸ�ֵ
		pstmt.setString(1, user.getUid());
		pstmt.setString(2, user.getUsername());
		pstmt.setString(3, user.getPassword());
		//ִ��SQL���
		int l = pstmt.executeUpdate();
		System.out.println(l);
		//����ر�
		pstmt.close();
		conn.close();
	}
}
