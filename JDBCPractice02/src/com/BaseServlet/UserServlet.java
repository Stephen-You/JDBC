package com.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	//登录
	public String login(HttpServletRequest request,HttpServletResponse response){
		System.out.println("login().....");
		throw new RuntimeException("login方法内部异常！");
	}
	//注册
	public String regist(HttpServletRequest request,HttpServletResponse response){
		System.out.println("regist()......");
		return "R:http://www.baidu.com";
	}

}
