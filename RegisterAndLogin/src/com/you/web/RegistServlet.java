package com.you.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
 * ע��
 * @author Yorick
 *
 */
@WebServlet("/RegistServlet")
public class RegistServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��������������Ӧ����
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		UserService userService = new UserService();
		//��װ���ݵ�JavaBean
		User user = CommonUtils.toBean(request.getParameterMap(), User.class);
		/* ��user����У��
		 *   > ���У�����
		 *     * ���������Ϣ��request��
		 *     * ����form��request�򣨻��ԣ�
		 */
		//����һ��Map����,����������еĴ�����Ϣ
		Map<String, String> map = new HashMap<>();
		//У��Username
		String username = user.getUsername();
		if(username==null||username.trim().isEmpty()){
			map.put("username", "�û�������Ϊ��");
		}else if (username.length()<6||username.length()>15) {
			map.put("username", "�û���������6��15λ֮��!");
		}
		//У��ͼƬ��֤��
		String verifyCode = user.getVerifyCode();
		String code = (String) request.getSession().getAttribute("code");
		if(!verifyCode.equals(code)){
			map.put("code", "ͼƬ��֤�벻��ȷ!");
		}
		//������ڴ�����Ϣ
		/*
		 * 1. ����map��request��
		 * 2. ���浱ǰuser��request�򣨻��ԣ�
		 * 3. ת����regist.jsp
		 * 4. ���return�����������ݲ���ִ��
		 */
		if(map.size()>0){
			request.setAttribute("map", map);
			request.setAttribute("user", user);
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		//���������֤��Ϣ����ȷ,��ô�ͽ��е�ҵ��㴦��
		try {
			//�����֤��Ϣ����ȷ,��ô����regist(),����user����
			userService.regist(user);
			/*
			 * 3. ���ִ�е����ע���ѳɹ���
			 * * ����ɹ���Ϣ��request��
			 * * ת����msg.jsp��ʾ�ɹ���Ϣ
			 */
			request.setAttribute("msg", "ע��ɹ�");
			request.getRequestDispatcher("/msg.jsp").forward(request, response);
		} catch (UserException e) {
			/*
			 * 4. ���ִ�е����˵��ע��ʧ���ˣ�
			 * * �����쳣��Ϣ��request��
			 * * ת����regist.jsp��ʾ������Ϣ
			 */
			request.setAttribute("msg", e.getMessage());
			/*
			 * Ϊ��ҳ��Ļ��ԣ���Ҫ�ѵ�ǰ�����ݱ��浽request���У�
			 */
			request.setAttribute("user", user);
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			
		}
	}

}
