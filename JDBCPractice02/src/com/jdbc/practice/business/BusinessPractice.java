package com.jdbc.practice.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import com.jdbc.practice.utils.JDBCUtils;

public class BusinessPractice {

	@Test
	public void businessTest(){
		/*
		 * try�������
		 * try��ʵ����
		 * finally���ر�
		 */
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//��ȡ����
			conn = JDBCUtils.getConnection();
			//��������
			conn.setAutoCommit(false);
			/*3. �޸�zs��������100
			 * 4. �޸�ls��������100
			 * */
			String update = "update account set balance=balance+? where name=?";
			pstmt = conn.prepareStatement(update);
			pstmt.setDouble(1, -100);
			pstmt.setString(2, "zs");
			pstmt.executeUpdate();
			pstmt.setDouble(1, 100);
			pstmt.setString(2, "ls");
			pstmt.executeUpdate();
			//�ж����ʧ���׳�����ʱ�쳣,�����ύ����
			if(false){
				throw new RuntimeException();
			}
			//�����ύ
			conn.commit();
		} catch (SQLException e) {
			//������е�����˵���ϱ߳����쳣,��ʱҪ�ع�
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