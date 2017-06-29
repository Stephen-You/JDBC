package com.practice.jdbc.first;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

/**
 * JDBC:Java DataBase Connectivity  Java���ݿ�����
 * 	��Ҫ����:
 * 		�����ݿ⽨��һ������
 * 		�����ݿⷢ��SQL���
 * 		��������ݿⷵ�صĽ��
 * @author Yorick
 *
 */
public class JDBCInduction {
	/**
	 * �������ݿ���Ҫ������:
	 * 	1.��������,ֻ��Ҫ��һ�η������ݿ����һ��
	 * 	2.��ȡ����,ÿ�η������ݿⴴ��һ������
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void jdbcConnection() throws ClassNotFoundException, SQLException{
		/*�Ĵ����Ӳ���
		 * 	1.��������--JDBC������ֱ�ӷ������ݿ�,�������������ݿ⳧���ṩ��JDBC��������,����Ϊ�����ݿ⳧���ṩ���������Ƽ���ز���
		 * 	2.���ݿ�������������:url
		 * 	3.���ݿ������������:username
		 * 	4.���ݿ������������:password
		 * */
		String driverManager = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/test1";
		String username = "root";
		String password = "123";
		//����������
		Class.forName(driverManager);
		//��ȡ���ݿ�����
		Connection conn = DriverManager.getConnection(url, username, password);
		//��ȡStatement����
		Statement sql = conn.createStatement();
		//ͨ��statement����,���������
		String insert = "INSERT INTO stu(sname,age,gander,province,tuition) VALUES ('����',34,'Ů','����',3000)";
		//���� SQL ���ݲ������� (DML) ��䣬�����м��� (2) ����ʲô�������ص� SQL ��䣬���� 0
		int r = sql.executeUpdate(insert);
		System.out.println(r);
		//���͸����
		String update = "update stu set sid=1 where sid=152";
		r = sql.executeUpdate(update);
		System.out.println(r);
		//����ɾ�����
		String delete = "delete from stu where sid>153";
		r = sql.executeUpdate(delete);
		System.out.println(r);
	}
	//��ѯ����
	@Test
	public void jdbcSelect() throws ClassNotFoundException, SQLException{
		//��ȡ����
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "123");
		//��ȡStatement����
		Statement sql = conn.createStatement();
		String select = "select * from dept order by deptno desc";
		ResultSet set = sql.executeQuery(select);
		//���������
		while (set.next()) {//ָ���ͣ���ڵ�һ�е��ϱ�,�ѹ�������ƶ�һ�У����жϵ�ǰ���Ƿ���ڣ�������ڣ���ӡ��ǰ������
			//�� Java ��������� int ����ʽ��ȡ�� ResultSet ����ĵ�ǰ����ָ���е�ֵ��
			int deptno = set.getInt(1);
			String dname = set.getString("dname");
			System.out.println(deptno+"  "+dname);
		}
	}
	//�淶������;���ӡ���伯�����������Ҫ�ر�
	@Test
	public void jdbcNormalize(){
		/*
		 * try�������
		 * try��ʵ����
		 * finally���ر�
		 */
		Connection conn = null;
		Statement sql = null;
		ResultSet set = null;
		try {
			String driverName = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/test1";
			String username = "root";
			String password = "123";
			Class.forName(driverName);
			//try��ʵ����
			conn = DriverManager.getConnection(url,username,password);
			sql = conn.createStatement();
			String select = "select * from dept order by deptno";
			set = sql.executeQuery(select);
			while (set.next()) {
				int deptno = set.getInt(1);
				String dname = set.getString("dname");
				System.out.println(deptno+"  "+dname);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// �ر�Ҫ����,�������,��ô�᲻ͣ�ĺķ���Դ,ֱ����Դ�ľ�,����bug,���Ǻ��ѷ���,��������Ҫ��������ϰ��
			try {
				//��Ϊ���ǿ��ܳ���һЩ�������,���»�ȡ����conn���ǿյ�,��ô��������Ҫ����һ����ָ���ж�
				/*���connΪnull,��ô�ϱ�catch�ᴦ���쳣,���Ǵ����쳣֮ǰ��Ҫ�ߵ�finally��,��������ж�,��ôfinally�лᱨ����ָ���쳣*/
				if(set != null) set.close();
				if(sql != null) sql.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
