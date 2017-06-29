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
		user.setUsername("李四");
		user.setPassword("lisi");
//		addUser1(user);
		addUser2(user);
		
	}

	//插入一个对象到数据库,使用Statement
	public void addUser1(User user) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "123");
		Statement sql = conn.createStatement();
		String insert = "insert into user values('"+user.getUid()+"','"+user.getUsername()+"','"+user.getPassword()+"')";
		int l = sql.executeUpdate(insert);
		System.out.println(insert);
		System.out.println(l);
		//关闭
		sql.close();
		conn.close();
	}
	//插入一个对象到数据库,使用PreparedStatement
	public void addUser2(User user) throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root","123");
		//给出SQL模板
		String insert = "insert into user values(?,?,?)";
		//获取PreparedStatement实例,初始化SQL模板
		PreparedStatement pstmt = conn.prepareStatement(insert);
		//给问号赋值
		pstmt.setString(1, user.getUid());
		pstmt.setString(2, user.getUsername());
		pstmt.setString(3, user.getPassword());
		//执行SQL语句
		int l = pstmt.executeUpdate();
		System.out.println(l);
		//倒序关闭
		pstmt.close();
		conn.close();
	}
}
