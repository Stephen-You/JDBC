package com.you.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.you.dao.impl.UserDao;
import com.you.domain.User;
import com.you.utils.JdbcUtils;

public class UserDaoImp implements UserDao{
	//ͨ���û��������û�
	@Override
	public User findByName(String username) {	
		try {
			//�õ�����
			Connection conn = JdbcUtils.getConnection();
			//��ȡPreparedStatementʵ��,׼��SQLģ��
			String select = "select * from user where username = ?";
			PreparedStatement pstmt = conn.prepareStatement(select);
			pstmt.setString(1, username);
			//ִ������ȡ�����
			ResultSet set = pstmt.executeQuery();
			//�ж�,�������ӳ�䵽user������ȥ
			while (set!=null&&set.next()) {
				User user = new User();
				user.setUsername(set.getString("username"));
				user.setPassword(set.getString("password"));
				user.setEmail(set.getString("email"));
				return user;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//ͨ���ʼ����Ҷ���
	@Override
	public User findByEmail(String email) {		
		try {
			//��ȡ����
			Connection conn = JdbcUtils.getConnection();
			String select = "select * from user where email = ?";
			PreparedStatement pstmt = conn.prepareStatement(select);
			pstmt.setString(1, email);
			ResultSet set = pstmt.executeQuery();
			while (set!=null&&set.next()) {
				User user = new User();
				user.setUsername(set.getString("username"));
				user.setPassword(set.getString("password"));
				user.setEmail(set.getString("email"));
				return user;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//ע��
	@Override
	public void addUser(User user) {
		try {
			Connection conn = JdbcUtils.getConnection();
			String insert = "insert into user(username,password,email) values(?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(insert);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			//ִ��
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
