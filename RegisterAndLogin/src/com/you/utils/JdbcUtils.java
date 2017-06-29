package com.you.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
	//创建一个配置文件对象
	private static Properties prop = new Properties();
	//静态代码块,加载配置文件,加载驱动,因为这些都只需要操作一次,所以我们放在静态代码块中,随着类的加载而加载
	static{	
		try {
			//把文件加载到流中,从流中获取参数
			InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("dbconfig.properties");
			prop.load(inputStream);
			//加载驱动类
			Class.forName(prop.getProperty("driverClassName"));
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
			return DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
}
