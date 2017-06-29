package com.practice.jdbc.jdbcUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JdbcUtils {
	//�����ļ�·��
		private static final String dbconfig = "dbconfig.properties";
	//��Ӧ�����ļ�
		private static Properties prop = new Properties();
	//�������ļ����ݼ��ص�prop�����С���Ϊ�Ƿŵ�static������ɵļ��ز��������Լ��ز���ֻ����JdbcUtils�౻����ʱ��ɶ������ļ��ļ��ء�
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
	//��ȡConnection�������������Ǵ�prop�л�ȡ��
		public static Connection getConnection() {
			try {
				return DriverManager.getConnection(prop.getProperty("url"),
						prop.getProperty("username"), prop.getProperty("password"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
