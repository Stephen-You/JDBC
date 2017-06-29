package com.practice.jdbc.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import com.practice.jdbc.jdbcUtils.JdbcUtils;

public class JDBCBatch {
	@Test
	public void addBatch() throws SQLException{
		Connection conn = JdbcUtils.getConnection();
		String insert = "insert into stu2 values(?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(insert);
		for(int i =0;i<10000;i++){
			pstmt.setString(1, i+"");
			pstmt.setString(2, "sname"+i);
			pstmt.setInt(3, i);
			pstmt.setString(4, "gender"+i);
			pstmt.addBatch();
		}
		Long start = System.currentTimeMillis();
		pstmt.executeBatch();
		Long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}
