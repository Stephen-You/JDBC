package com.you.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
	//����һ�������ļ�����
	private static Properties prop = new Properties();
	//��̬�����,���������ļ�,��������,��Ϊ��Щ��ֻ��Ҫ����һ��,�������Ƿ��ھ�̬�������,������ļ��ض�����
	static{	
		try {
			//���ļ����ص�����,�����л�ȡ����
			InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("dbconfig.properties");
			prop.load(inputStream);
			//����������
			Class.forName(prop.getProperty("driverClassName"));
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
			return DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
}
