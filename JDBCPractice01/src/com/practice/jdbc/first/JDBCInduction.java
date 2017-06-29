package com.practice.jdbc.first;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

/**
 * JDBC:Java DataBase Connectivity  Java数据库链接
 * 	主要任务:
 * 		与数据库建立一个连接
 * 		向数据库发送SQL语句
 * 		处理从数据库返回的结果
 * @author Yorick
 *
 */
public class JDBCInduction {
	/**
	 * 连接数据库需要做的事:
	 * 	1.加载驱动,只需要第一次访问数据库加载一次
	 * 	2.获取连接,每次访问数据库创建一个连接
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void jdbcConnection() throws ClassNotFoundException, SQLException{
		/*四大连接参数
		 * 	1.驱动名称--JDBC并不能直接访问数据库,必须依赖于数据库厂商提供的JDBC驱动程序,下面为各数据库厂商提供的驱动名称及相关参数
		 * 	2.数据库连接三个参数:url
		 * 	3.数据库连接三大参数:username
		 * 	4.数据库连接三大参数:password
		 * */
		String driverManager = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/test1";
		String username = "root";
		String password = "123";
		//加载驱动类
		Class.forName(driverManager);
		//获取数据库连接
		Connection conn = DriverManager.getConnection(url, username, password);
		//获取Statement对象
		Statement sql = conn.createStatement();
		//通过statement对象,发送增语句
		String insert = "INSERT INTO stu(sname,age,gander,province,tuition) VALUES ('吕嬃',34,'女','湖南',3000)";
		//对于 SQL 数据操作语言 (DML) 语句，返回行计数 (2) 对于什么都不返回的 SQL 语句，返回 0
		int r = sql.executeUpdate(insert);
		System.out.println(r);
		//发送该语句
		String update = "update stu set sid=1 where sid=152";
		r = sql.executeUpdate(update);
		System.out.println(r);
		//发送删除语句
		String delete = "delete from stu where sid>153";
		r = sql.executeUpdate(delete);
		System.out.println(r);
	}
	//查询操作
	@Test
	public void jdbcSelect() throws ClassNotFoundException, SQLException{
		//获取连接
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "123");
		//获取Statement对象
		Statement sql = conn.createStatement();
		String select = "select * from dept order by deptno desc";
		ResultSet set = sql.executeQuery(select);
		//遍历结果集
		while (set.next()) {//指针会停留在第一行的上边,把光标向下移动一行，并判断当前行是否存在，如果存在，打印当前行数据
			//以 Java 编程语言中 int 的形式获取此 ResultSet 对象的当前行中指定列的值。
			int deptno = set.getInt(1);
			String dname = set.getString("dname");
			System.out.println(deptno+"  "+dname);
		}
	}
	//规范化操作;连接、语句集、结果集都需要关闭
	@Test
	public void jdbcNormalize(){
		/*
		 * try外给引用
		 * try内实例化
		 * finally来关闭
		 */
		Connection conn = null;
		Statement sql = null;
		ResultSet set = null;
		try {
			String driverName = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/test1";
			String username = "root";
			String password = "123";
			Class.forName(driverName);
			//try内实例化
			conn = DriverManager.getConnection(url,username,password);
			sql = conn.createStatement();
			String select = "select * from dept order by deptno";
			set = sql.executeQuery(select);
			while (set.next()) {
				int deptno = set.getInt(1);
				String dname = set.getString("dname");
				System.out.println(deptno+"  "+dname);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// 关闭要倒关,如果不关,那么会不停的耗费资源,直到资源耗尽,这种bug,我们很难发现,所以我们要养成良好习惯
			try {
				//因为我们可能出现一些输入错误,导致获取到的conn等是空的,那么这里我们要进行一个空指针判断
				/*如果conn为null,那么上边catch会处理异常,但是处理异常之前是要走到finally的,如果不加判断,那么finally中会报出空指针异常*/
				if(set != null) set.close();
				if(sql != null) sql.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
