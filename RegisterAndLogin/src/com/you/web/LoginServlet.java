package com.you.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.you.domain.User;
import com.you.service.UserException;
import com.you.service.UserService;

import cn.itcast.commons.CommonUtils;

/**
 * �û���¼У��
 * @author Yorick
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("������...");
		//����¼��Ϣ��װ��JavaBean
		User user = CommonUtils.toBean(request.getParameterMap(), User.class);
		UserService us = new UserService();
		//�ж��Ƿ�������û�
		try {
			//ִ�е�����,˵����������û�
			User user1 = us.login(user);
			//ִ�е�����˵��,������û�,��ô���û���Ϣ���浽session
			request.getSession().setAttribute("user", user1);
			//�ض���
			response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
		} catch (UserException e) {
			//ִ�е�����,˵��û������û�,�����û����������,�Ѵ�����Ϣ���浽request����,������Ϣ���ص���¼ҳ��
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
	}

}
