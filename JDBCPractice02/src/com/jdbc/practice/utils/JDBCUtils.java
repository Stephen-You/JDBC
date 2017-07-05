package com.jdbc.practice.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
	//�����ļ�·��
	private static String dbconfig = "dbconfig.properties";
	//���������ļ�����
	private static Properties properties = new Properties();
	//��̬�����,�����ļ�
	static {
		try {
			InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(dbconfig);
			properties.load(input);
			//��������
			Class.forName(properties.getProperty("driverClassName"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//��ȡ����
	public static Connection getConnection(){
		try {
			return DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}
