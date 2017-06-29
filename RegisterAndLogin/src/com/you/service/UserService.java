package com.you.service;

import com.you.dao.UserDaoFactory;
import com.you.dao.impl.UserDao;
import com.you.domain.User;

public class UserService {
	//获取UserDao实例
	UserDao userDao = UserDaoFactory.getUserDao();
	//注册
	public void regist(User user) throws UserException{
		if(userDao.findByName(user.getUsername())!=null) throw new UserException("该用户名已被注册!");
		if(userDao.findByEmail(user.getEmail())!=null) throw new UserException("该邮箱已被注册");
		//如果用户名和邮箱都正常,那么把注册信息添加到数据库
		userDao.addUser(user);
	}
	//登录
	public User login(User user) throws UserException{
		User user1 = userDao.findByName(user.getUsername());
		if(user1==null) throw new UserException("用户名不正确!");
		if(!user1.getPassword().equals(user.getPassword())) throw new UserException("密码不正确");
		//登录成功,返回该User对象
		return user1;
	}
}
