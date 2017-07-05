package com.jdbc.practice.pool.jndi;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JNDIPracticeDAO {
	public void xxx(){
		//获取JNDI资源
		try {
			//创建上下文
			Context initCtx = new InitialContext();
			/*
			 * 使用上下文查找指定名称的资源
			 * 名称：java:comp/env，表示入口点！得到的还是上下文
			 */
//			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			/*
			 * 继承查找资源，名称为：jdbc/mydatsource，它对应的是<Resource name="jdbc/mydatsource">
			 */
//			DataSource dataSource = (DataSource) envCtx.lookup("jdbc/test1");
			DataSource dataSource = (DataSource) initCtx.lookup("java:comp/env/jdbc/test1");
			
//			DataSource dataSource = (DataSource) envCtx.lookup("jdbc/mydatasource");
			//获取连接
			Connection conn = dataSource.getConnection();
			System.out.println(conn.getClass().getName());
			//归还连接
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
