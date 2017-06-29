package com.you.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.you.dao.impl.UserDao;

//������ --> ����UserDAO�ӿڵ�ʵ����
/**
 * ����������ΪʲôҪʹ�ù�������,������Ϊ������service��Ҫ����dao���ʵ����,���ǿ�����������һ�����,�Ǿ���
 * ���ǿ��ܻ����Ժ�Ĵ����ع��и���ʵ����,��ô��ʱ��ͻ���Ҫȥ�޸�service��,��ô������ô�������������,ͨ����һЩ���ܸ��ĵĲ�����д��־û��ļ�
 * �ǲ��Ǹ������ṩ��һ��˼·��,����ֻ��Ҫ�����ʵ����д��־û��ļ�,Ȼ����ļ��ж�ȡ����,�����Ժ������������ʵ����,Ҳ��ֻ�Ǹ��ĳ־û��ļ�,�������޸Ĵ���
 * @author Yorick
 */
public class UserDaoFactory {
	//��ȡUserDaoʵ����
	public static UserDao getUserDao(){	
		try {
			//�����־õ����Լ�����
			Properties properties = new Properties();
			//���س־û��ļ�
			InputStream inputStream = UserDaoFactory.class.getClassLoader().getResourceAsStream("userDao.properties");
			properties.load(inputStream);
			//��ȡʵ��������
			String className = properties.getProperty("com.you.dao.impl.UserDao");
			//ͨ�������ȡʵ�����ֽ����ļ�
			Class clazz = Class.forName(className);
			//��ȡʵ��������
			return (UserDao)clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
