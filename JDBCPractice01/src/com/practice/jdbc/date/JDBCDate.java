package com.practice.jdbc.date;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import org.junit.Test;

import com.practice.jdbc.jdbcUtils.JdbcUtils;

public class JDBCDate {
	//添加
	@Test
	public void addDate() throws SQLException{
		//获取连接
		Connection conn = JdbcUtils.getConnection();
		String insert = "insert into dt values(?,?,?)";
		java.util.Date date = new java.util.Date();
		PreparedStatement pstmt = conn.prepareStatement(insert);
		pstmt.setDate(1, new Date(date.getTime()));
		pstmt.setTime(2, new Time(date.getTime()));
		pstmt.setTimestamp(3, new Timestamp(date.getTime()));
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
	}
	//查询
	@Test
	public void getDate() throws SQLException{
		Connection conn = JdbcUtils.getConnection();
		String select = "select * from dt";
		PreparedStatement pstmt = conn.prepareStatement(select);
		ResultSet set = pstmt.executeQuery();
		set.next();
		java.util.Date date = set.getDate(1);
		System.out.println(date);
		date = set.getTime(2);
		System.out.println(date);
		date = set.getTimestamp(3);
		System.out.println(date);
	}
}
