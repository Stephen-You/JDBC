package com.jdbc.practice.pool.c3p0;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.jdbc.practice.utils.JDBCUtilsNew;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Practice {
	//测试c3p0连接池,参数在方法中
	@Test
	public void test() throws PropertyVetoException, SQLException{
		//创建c3p0连接池对象
		ComboPooledDataSource db = new ComboPooledDataSource();
		//配置参数
		db.setDriverClass("com.mysql.jdbc.Driver");
		db.setUser("root");
		db.setPassword("123");
		db.setJdbcUrl("jdbc:mysql://localhost:3306/test1");
		//获取连接
		Connection conn = db.getConnection();
		System.out.println(conn.getClass().getName());
		//归还连接
		conn.close();
	}
	//测试c3p0连接池,参数在配置文件中
	@Test
	public void test1() throws SQLException{
		//创建连接池对象
		ComboPooledDataSource db = new ComboPooledDataSource();
		/**如果在这里配置参数,那么会覆盖配置文件
		 * 配置参数
		db.setDriverClass("com.mysql.jdbc.Driver");
		db.setUser("root");
		db.setPassword("123");
		db.setJdbcUrl("jdbc:mysql://localhost:3306/test1");
		 */
		//获取连接
		Connection conn = db.getConnection();
		System.out.println(conn.getClass().getName());
		//归还连接
		conn.close();
	}
	//使用c3p0配置文件,命名配置
	@Test
	public void test3() throws SQLException{
		ComboPooledDataSource db = new ComboPooledDataSource("orcale");
		Connection conn = db.getConnection();
		System.out.println(conn.getClass().getName());
		//归还连接
		conn.close();
	}
	//因为有了连接池,所以我们可以更改我们的JDBCUtils工具类,下面对其进行测试
	@Test
	public void test4() throws SQLException{
		Connection conn = JDBCUtilsNew.getConnection();
		System.out.println(conn.getClass().getName());
	}
}
