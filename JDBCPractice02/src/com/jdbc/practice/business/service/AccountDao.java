package com.jdbc.practice.business.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import com.jdbc.practice.utils.JDBCUtilsNew;

/**
 * 在前面的事物学习中,我们在DAO中操作是很简单的也很容易理解,但实际开发中我们处理业务是在service层,DAo层只是与数据库的
 * 交互,DAO中不是处理事务的地方，因为DAO中的每个方法都是对数据库的一次操作，而Service中的方法才是
 * 对应一个业务逻辑。也就是说我们需要在Service中的一方法中调用DAO的多个方法，而这些方
 * 法应该在一起事务中。
 * 让DAO处理多个方法,且这多个方法在一个事物中,那么要保证是一个连接的操作,此时不能让DAO再自己获取连接,而应该是外面传入连接
 * @author Yorick
 */
public class AccountDao {
	
	/*
	  * 给指定用户添加指定金额
	  */
	 public void update(String name, double money) throws SQLException {
		 QueryRunner qr = new QueryRunner();//创建QueryRunner
		 
		 // 应该得到JdbcUtils.beginTransaction()时创建的连接对象！
		 Connection con = JdbcUtils.getConnection();//得到连接对象
		 // 给出sql模板
		 String sql = "update account set balance=balance+? where name=?";
		 //使用con、sql、还有参数来调用update方法
		 qr.update(con, sql, money, name);
		 
		 JdbcUtils.releaseConnection(con);//释放连接
	 }
	 
	 /*
	  * 给指定用户添加指定金额
	  */
	 public void update1(String name, double money) throws SQLException {
		 QueryRunner qr = new TxQueryRunner();//创建QueryRunner
		 String sql = "update account set balance=balance+? where name=?";
		 qr.update(sql, money, name);
	 }
	
	
	@Test
	public void test() throws SQLException{
		update("zs", (double) 10000);
	}
	
}
