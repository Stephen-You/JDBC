package com.jdbc.practice.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.junit.Test;

import com.jdbc.practice.utils.JDBCUtils;

public class SavepointPractice {
	
	/*
	 * ���Ķ�����˵����������ת1W���Ҿ͸���ת100W��
	 * ==========================================
	 * 
	 * ����������ת1W��������ȥ1W�����ļ���1W��
	 * ���ñ���㣡
	 * ���ĸ�����ת100W�����ļ�ȥ100W����������100W��
	 * �鿴�������Ϊ��������ô�ع�������㡣
	 * �ύ����
	 */
	@Test
	public void test1(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtils.getConnection();
			//��������
			conn.setAutoCommit(false);
			//����SQLģ��
			String update = "update account set balance=balance+? where name=?";
			//��ȡSQL������
			pstmt = conn.prepareStatement(update);
			//������ݿ��Ƿ�֧�ֱ����
			boolean flag = conn.getMetaData().supportsSavepoints();
			//����֧�ֱ����,���ñ����
			if(flag){
				//��������1w,�����ļ�1w
				pstmt.setDouble(1, -10000);
				pstmt.setString(2, "zs");
				pstmt.executeUpdate();
				pstmt.setDouble(1, 10000);
				pstmt.setString(2, "ls");
				pstmt.executeUpdate();
				//���ñ����
				Savepoint sp = conn.setSavepoint();
				//�����ļ�ȥ100w,��������100w
				pstmt.setDouble(1, 1000000);
				pstmt.setString(2, "zs");
				pstmt.executeUpdate();
				pstmt.setDouble(1, -1000000);
				pstmt.setString(2, "ls");
				pstmt.executeUpdate();
				//�ж����ĵ�balanceֵ�Ƿ����0,���С��,˵������Ӧ��ʧ��
				//��ѯ���ģ��
				String select = "select balance from account where name=?";
				pstmt = conn.prepareStatement(select);
				pstmt.setString(1, "ls");
				ResultSet set = pstmt.executeQuery();
				set.next();
				Double balance = set.getDouble("balance");
				//����������Ϊ��������ô�ع���ָ�������
				if(balance<0){
					conn.rollback(sp);
					System.out.println("�������ϵ���!");
				}
				if(false){
					throw new RuntimeException();
				}
				conn.commit();
			}
		} catch (Exception e) {
			//���е�����,˵���������ʧ��,��ʱִ�лع�����
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}finally{
			
				try {
					if(conn!=null) conn.close();
					if(pstmt!=null) pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
}
