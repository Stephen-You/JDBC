package com.jdbc.practice.pool.DBCP;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.junit.Test;

public class DBCPPractice {
	@Test
	public void test() throws SQLException{
		//创建DBCP连接池对象
		BasicDataSource dbsc = new BasicDataSource();
		//基本配置
		dbsc.setUsername("root");
		dbsc.setPassword("123");
		dbsc.setUrl("jdbc:mysql://localhost:3306/test1");
		dbsc.setDriverClassName("com.mysql.jdbc.Driver");
		//配置连接池参数
		dbsc.setMaxActive(20);//最大连接数
		dbsc.setMaxIdle(10);//最大空闲连接数
		dbsc.setMaxWait(1000);//最大等待时间
		dbsc.setMinIdle(2);//最小连接数
		//使用连接池,获取连接
		Connection conn = dbsc.getConnection();
		System.out.println(conn.getClass().getName());
		//归还连接
		conn.close();
		
	}
}
