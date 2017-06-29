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
	
	//���������
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
	
	//��ѯ������
	@Test
	public void findImage() throws SQLException, IOException{
		Connection conn = JdbcUtils.getConnection();
		String select = "select filename,data from tab_bin where id=?";
		PreparedStatement pstmt = conn.prepareStatement(select);
		pstmt.setInt(1, 1);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		String filename = rs.getString(2);
		//��ȡ����������
		InputStream inputStream = rs.getBinaryStream("data");
		//�������������
		OutputStream outputStream = new FileOutputStream("F://bing.jpg");
		//��������������������������ȥ
		IOUtils.copy(inputStream, outputStream);
	}
	
	//���������--��ʽ��
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
	//��ѯ������--��ʽ��
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
