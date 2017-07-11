package com.customer.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {
	private static DataSource dataSource = new ComboPooledDataSource();
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	//开启事务
	public static void beginTransaction() throws SQLException{
		Connection con = threadLocal.get();
		if(con!=null){
			throw new SQLException("事务已经开启,不能重复开启!");
		}
		con = dataSource.getConnection();
		threadLocal.set(con);
		con.setAutoCommit(false);
	}
	//提交事务
	public static void commitTransaction() throws SQLException{
		Connection con = threadLocal.get();
		if(con==null){
			throw new SQLException("事务不存在,不能提交!");
		}
		con.commit();
		con.close();
		threadLocal.remove();
	}
	//回滚事务
	public static void rollbackTransaction() throws SQLException{
		Connection con = threadLocal.get();
		if(con==null){
			throw new SQLException("事务不存在,不能回滚!");
		}
		con.rollback();
		con.close();
		threadLocal.remove();
	}
	//获取连接池对象
	public static DataSource getDataSource(){
		return dataSource;
	}
	//获取连接
	public static Connection getConnection() throws SQLException{
		Connection con = threadLocal.get();
		if(con==null){//说明没有开启事务
			return dataSource.getConnection();
		}
		//进行到这里,说明开启事务
		return con;
	}
	//释放连接
	public static void releaseConnnection(Connection con) throws SQLException{
		Connection thCon = threadLocal.get();
		if(con==null) return;
		if(con==thCon)
			//如果当前要释放的连接就是当前线程的专用事务连接，那么不关！
			return;
		//如果不是当前线程的事务专用连接，那么关闭它！
		con.close();
		
	}
}
