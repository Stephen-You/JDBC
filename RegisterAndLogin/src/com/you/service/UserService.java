package com.you.service;

import com.you.dao.UserDaoFactory;
import com.you.dao.impl.UserDao;
import com.you.domain.User;

public class UserService {
	//��ȡUserDaoʵ��
	UserDao userDao = UserDaoFactory.getUserDao();
	//ע��
	public void regist(User user) throws UserException{
		if(userDao.findByName(user.getUsername())!=null) throw new UserException("���û����ѱ�ע��!");
		if(userDao.findByEmail(user.getEmail())!=null) throw new UserException("�������ѱ�ע��");
		//����û��������䶼����,��ô��ע����Ϣ��ӵ����ݿ�
		userDao.addUser(user);
	}
	//��¼
	public User login(User user) throws UserException{
		User user1 = userDao.findByName(user.getUsername());
		if(user1==null) throw new UserException("�û�������ȷ!");
		if(!user1.getPassword().equals(user.getPassword())) throw new UserException("���벻��ȷ");
		//��¼�ɹ�,���ظ�User����
		return user1;
	}
}
