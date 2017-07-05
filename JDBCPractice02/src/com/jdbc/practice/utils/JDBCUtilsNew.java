package com.jdbc.practice.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtilsNew {
	//��ȡ���ݿ����ӳض���
	private static DataSource ds = new ComboPooledDataSource();
	//�������ӳض���
	public static DataSource getDataSource(){
		return ds;
	}
	//��������
	public static Connection getConnection() throws SQLException{
		return ds.getConnection();
	}
}
