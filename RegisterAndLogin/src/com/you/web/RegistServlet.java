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
 * 注册
 * @author Yorick
 *
 */
@WebServlet("/RegistServlet")
public class RegistServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//处理请求编码和响应编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		UserService userService = new UserService();
		//封装数据到JavaBean
		User user = CommonUtils.toBean(request.getParameterMap(), User.class);
		/* 对user进行校验
		 *   > 如果校验出错
		 *     * 保存错误信息到request域
		 *     * 保存form到request域（回显）
		 */
		//创建一个Map对象,用来存放所有的错误信息
		Map<String, String> map = new HashMap<>();
		//校验Username
		String username = user.getUsername();
		if(username==null||username.trim().isEmpty()){
			map.put("username", "用户名不能为空");
		}else if (username.length()<6||username.length()>15) {
			map.put("username", "用户名长度在6到15位之间!");
		}
		//校验图片验证码
		String verifyCode = user.getVerifyCode();
		String code = (String) request.getSession().getAttribute("code");
		if(!verifyCode.equals(code)){
			map.put("code", "图片验证码不正确!");
		}
		//如果存在错误信息
		/*
		 * 1. 保存map到request域
		 * 2. 保存当前user到request域（回显）
		 * 3. 转发到regist.jsp
		 * 4. 添加return，让下面内容不再执行
		 */
		if(map.size()>0){
			request.setAttribute("map", map);
			request.setAttribute("user", user);
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		//如果上面验证信息都正确,那么就进行到业务层处理
		try {
			//如果验证信息都正确,那么调用regist(),传递user参数
			userService.regist(user);
			/*
			 * 3. 如果执行到这里，注册已成功！
			 * * 保存成功信息到request域
			 * * 转发到msg.jsp显示成功信息
			 */
			request.setAttribute("msg", "注册成功");
			request.getRequestDispatcher("/msg.jsp").forward(request, response);
		} catch (UserException e) {
			/*
			 * 4. 如果执行到这里，说明注册失败了！
			 * * 保存异常信息到request域
			 * * 转发到regist.jsp显示错误信息
			 */
			request.setAttribute("msg", e.getMessage());
			/*
			 * 为了页面的回显，需要把当前表单数据保存到request域中！
			 */
			request.setAttribute("user", user);
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			
		}
	}

}
