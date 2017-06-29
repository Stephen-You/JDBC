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
		//我们查看数据库发现并没有用户a,但是却返回了true,这就是SQL攻击的一个特例,我们要做的就是防止客户端进行这种攻击
		System.out.println(flag);
		flag = loginPreparedStatement(username, password);
		System.out.println(flag);
	}
	
	//实验SQL攻击
	public boolean login(String username,String password) throws SQLException, ClassNotFoundException{
		Connection conn = null;
		Statement sql = null;
		ResultSet set = null;
		Class.forName("com.mysql.jdbc.Driver");
		//try内实例化,获取连接
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "123");
		sql = conn.createStatement();
		String select = "SELECT * FROM user WHERE username='" + username + "' and password='" + password + "'";
//		String select = "select * from user where username = '"+username+"' and password = '"+password+"'";
		System.out.println("查询语句:"+select);
		set = sql.executeQuery(select);
//			while (set.next()) {
//				String uid = set.getString(1);
//				String username1 = set.getString("username");
//				String password1 = set.getString("password");
//				System.out.println(uid+":"+username1+":"+password1);
//			}
		return set.next();
	}
	//PreparedStatement防SQL攻击
	public boolean loginPreparedStatement(String username,String password) throws ClassNotFoundException, SQLException{
		//加载MySQL驱动
		Class.forName("com.mysql.jdbc.Driver");
		//获取数据库连接
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "123");
		//SQL模板
		String select = "select * from user where username=? and password=?";
		//使用SQL模板得到PreparedStatement实例
		PreparedStatement pstml = conn.prepareStatement(select);
		//给SQL模板中的问号赋值
		pstml.setString(1, username);
		pstml.setString(2, password);
		//pstml:com.mysql.jdbc.JDBC4PreparedStatement@fbc9c9: select * from user where username='a\' or \'a\'=\'a' and password='a\' or \'a\'=\'a'
		System.out.println(select);
		//获取结果集
		ResultSet set = pstml.executeQuery();
		return set.next();
	}
}
