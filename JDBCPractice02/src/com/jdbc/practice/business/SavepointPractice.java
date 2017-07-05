package com.jdbc.practice.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.junit.Test;

import com.jdbc.practice.utils.JDBCUtils;

public class SavepointPractice {
	
	/*
	 * 李四对张三说，如果你给我转1W，我就给你转100W。
	 * ==========================================
	 * 
	 * 张三给李四转1W（张三减去1W，李四加上1W）
	 * 设置保存点！
	 * 李四给张三转100W（李四减去100W，张三加上100W）
	 * 查看李四余额为负数，那么回滚到保存点。
	 * 提交事务
	 */
	@Test
	public void test1(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtils.getConnection();
			//开启事务
			conn.setAutoCommit(false);
			//创建SQL模板
			String update = "update account set balance=balance+? where name=?";
			//获取SQL语句对象
			pstmt = conn.prepareStatement(update);
			//检查数据库是否支持保存点
			boolean flag = conn.getMetaData().supportsSavepoints();
			//假如支持保存点,设置保存点
			if(flag){
				//给张三减1w,给李四加1w
				pstmt.setDouble(1, -10000);
				pstmt.setString(2, "zs");
				pstmt.executeUpdate();
				pstmt.setDouble(1, 10000);
				pstmt.setString(2, "ls");
				pstmt.executeUpdate();
				//设置保存点
				Savepoint sp = conn.setSavepoint();
				//给李四减去100w,给张三加100w
				pstmt.setDouble(1, 1000000);
				pstmt.setString(2, "zs");
				pstmt.executeUpdate();
				pstmt.setDouble(1, -1000000);
				pstmt.setString(2, "ls");
				pstmt.executeUpdate();
				//判断李四的balance值是否大于0,如果小于,说明事务应该失败
				//查询语句模板
				String select = "select balance from account where name=?";
				pstmt = conn.prepareStatement(select);
				pstmt.setString(1, "ls");
				ResultSet set = pstmt.executeQuery();
				set.next();
				Double balance = set.getDouble("balance");
				//如果李四余额为负数，那么回滚到指定保存点
				if(balance<0){
					conn.rollback(sp);
					System.out.println("张三你上当了!");
				}
				if(false){
					throw new RuntimeException();
				}
				conn.commit();
			}
		} catch (Exception e) {
			//进行到这里,说明事务操作失败,此时执行回滚操作
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}finally{
			
				try {
					if(conn!=null) conn.close();
					if(pstmt!=null) pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
}
