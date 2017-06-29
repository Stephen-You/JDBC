package com.you.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.you.dao.impl.UserDao;
import com.you.domain.User;
import com.you.utils.JdbcUtils;

public class UserDaoImp implements UserDao{
	//通过用户名查找用户
	@Override
	public User findByName(String username) {	
		try {
			//得到连接
			Connection conn = JdbcUtils.getConnection();
			//获取PreparedStatement实例,准备SQL模板
			String select = "select * from user where username = ?";
			PreparedStatement pstmt = conn.prepareStatement(select);
			pstmt.setString(1, username);
			//执行语句获取结果集
			ResultSet set = pstmt.executeQuery();
			//判断,将结果集映射到user对象中去
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
	//通过邮件查找对象
	@Override
	public User findByEmail(String email) {		
		try {
			//获取连接
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
	//注册
	@Override
	public void addUser(User user) {
		try {
			Connection conn = JdbcUtils.getConnection();
			String insert = "insert into user(username,password,email) values(?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(insert);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			//执行
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
