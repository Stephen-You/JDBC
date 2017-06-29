package com.practice.jdbc.bigData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.practice.jdbc.jdbcUtils.JdbcUtils;

public class JDBCData {
	
	//插入大数据
	@Test
	public void addImage() throws SQLException, FileNotFoundException{
		Connection conn = JdbcUtils.getConnection();
		String insert = "insert into tab_bin(filename,data) values(?,?)";
		PreparedStatement pstmt = conn.prepareStatement(insert);
		pstmt.setString(1, "bai.jpg");
		FileInputStream input = new FileInputStream("F:\\bai.jpg");
		pstmt.setBinaryStream(2, input);
		pstmt.executeUpdate();
	}
	
	//查询大数据
	@Test
	public void findImage() throws SQLException, IOException{
		Connection conn = JdbcUtils.getConnection();
		String select = "select filename,data from tab_bin where id=?";
		PreparedStatement pstmt = conn.prepareStatement(select);
		pstmt.setInt(1, 1);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		String filename = rs.getString(2);
		//读取输入流对象
		InputStream inputStream = rs.getBinaryStream("data");
		//创建输出流对象
		OutputStream outputStream = new FileOutputStream("F://bing.jpg");
		//把输入流的数据输出到输出流上去
		IOUtils.copy(inputStream, outputStream);
	}
	
	//插入大数据--方式二
	@Test
	public void addMp3() throws SQLException, IOException{
		Connection conn = JdbcUtils.getConnection();
		String insert = "insert into tab_bin(filename,data) values(?,?)";
		PreparedStatement pstmt = conn.prepareStatement(insert);
		pstmt.setString(1, "bai.jpg");
		File file = new File("F:\\bai.jpg");
		byte[] datas = FileUtils.readFileToByteArray(file);
		Blob blog = new SerialBlob(datas);
		pstmt.setBlob(2, blog);
		pstmt.executeUpdate();
	}
	//查询大数据--方式二
	@Test
	public void findMp3() throws SQLException, IOException{
		Connection conn = JdbcUtils.getConnection();
		String select = "select filename,data from tab_bin where id=?";
		PreparedStatement pstmt = conn.prepareStatement(select);
		pstmt.setInt(1, 2);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		String filename = rs.getString("filename");
		Blob blob = rs.getBlob(2);
		InputStream in = blob.getBinaryStream();
		OutputStream out = new FileOutputStream("F://bing1.jpg");
		IOUtils.copy(in, out);
	}
}
