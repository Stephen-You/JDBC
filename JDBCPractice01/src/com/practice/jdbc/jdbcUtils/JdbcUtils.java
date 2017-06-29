package com.practice.jdbc.jdbcUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JdbcUtils {
	//配置文件路径
		private static final String dbconfig = "dbconfig.properties";
	//对应配置文件
		private static Properties prop = new Properties();
	//把配置文件内容加载到prop对象中。因为是放到static块中完成的加载操作，所以加载操作只会在JdbcUtils类被加载时完成对配置文件的加载。
		static {
			try {
				InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(dbconfig);
				prop.load(in);
				Class.forName(prop.getProperty("driverClassName"));
			} catch(IOException e) {
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	//获取Connection方法，参数都是从prop中获取。
		public static Connection getConnection() {
			try {
				return DriverManager.getConnection(prop.getProperty("url"),
						prop.getProperty("username"), prop.getProperty("password"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
