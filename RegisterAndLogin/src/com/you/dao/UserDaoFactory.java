package com.you.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.you.dao.impl.UserDao;

//工厂类 --> 生产UserDAO接口的实现类
/**
 * 在这里我们为什么要使用工厂类呢,这是因为我们在service层要调用dao层的实现类,我们可能面临这样一种情况,那就是
 * 我们可能会在以后的代码重构中更改实现类,那么这时候就还需要去修改service层,那么我们怎么避免这个问题呢,通过把一些可能更改的参数的写入持久化文件
 * 是不是给我们提供了一个思路呢,我们只需要把这个实现类写入持久化文件,然后从文件中读取即可,这样以后我们如果更改实现类,也是只是更改持久化文件,而不用修改代码
 * @author Yorick
 */
public class UserDaoFactory {
	//获取UserDao实现类
	public static UserDao getUserDao(){	
		try {
			//创建持久的属性集对象
			Properties properties = new Properties();
			//加载持久化文件
			InputStream inputStream = UserDaoFactory.class.getClassLoader().getResourceAsStream("userDao.properties");
			properties.load(inputStream);
			//获取实现类名称
			String className = properties.getProperty("com.you.dao.impl.UserDao");
			//通过反射获取实现类字节码文件
			Class clazz = Class.forName(className);
			//获取实例化对象
			return (UserDao)clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
