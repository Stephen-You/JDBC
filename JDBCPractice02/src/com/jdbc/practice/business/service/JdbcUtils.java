package com.jdbc.practice.business.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * JdbcUtils 3.0
 * @author Yorick
 * 可以在事务环境下使用，也可以在非事务环境下使用！
 * 还要支持多线程并发访问
 * 还要去简化dao！
 */
public class JdbcUtils {
	//创建c3p0连接池
	private static DataSource dataSource = new ComboPooledDataSource();
	//需要为每个线程提供一个自己的事务专用连接
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	/**
	 * 开启事务
	 * @throws SQLException 
	 */
	public static void beginTransaction() throws SQLException{
		Connection con = threadLocal.get();//获取当前线程专用的事务连接
		if(con!=null){
			throw new SQLException("事务已经开启,不能重复开启!");
		}
		//走到这里,说明con为null,给con赋值,开启事务
		con = dataSource.getConnection();
		con.setAutoCommit(false);
		//把当前线程的事务专用连接保存到tl中，下次此线程中的事务其它操作就可以获取到了！
		threadLocal.set(con);
	}
	/**
	 * 提交事务
	 * @throws SQLException 
	 */
	public static void commitTransaction() throws SQLException{
		Connection con = threadLocal.get();
		if(con==null){
			throw new SQLException("事务不存在,不能提交!");
		}
		//走到这一步,说明获取到了事务连接,说明事务开启了,此时提交事务,关闭连接,将当前线程连接从ThreadLocal中移除
		con.commit();
		con.close();
		threadLocal.remove();
	}
	/**
	 * 回滚事务
	 * @throws SQLException 
	 */
	public static void rollbackTransaction() throws SQLException{
		Connection con = threadLocal.get();
		if(con==null){
			throw new SQLException("事务不存在,不能回滚!");
		}
		con.rollback();
		con.close();
		threadLocal.remove();
	}
	/**
	 * 获取连接池
	 */
	public static DataSource getDataSource(){
		return dataSource;
	}
	/**
	 * 使用本方法，要求必须提供c3p0的配置文件！
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException{
		Connection con = threadLocal.get();
		if(con==null){//说明没有开启事务
			return dataSource.getConnection();
		}
		//说明开启了事务,返回与事务相关的连接
		return con;
	}
	/**
	 * 释放连接
	 * @throws SQLException 
	 */
	public static void releaseConnection(Connection con) throws SQLException{
		if(con==null) return;
		Connection thCon = threadLocal.get();
		if(thCon==con)
			return;//如果当前要释放的连接就是当前线程的专用事务连接，那么不关！
		con.close();//如果不是当前线程的事务专用连接，那么关闭它！
	}
}
