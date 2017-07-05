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
	//配置文件路径
	private static String dbconfig = "dbconfig.properties";
	//创建配置文件对象
	private static Properties properties = new Properties();
	//静态代码块,加载文件
	static {
		try {
			InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(dbconfig);
			properties.load(input);
			//加载驱动
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
	//获取连接
	public static Connection getConnection(){
		try {
			return DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}
