package com.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/AServlet")
public class AServlet extends BaseServlet {
	//µÇÂ¼
	public String login(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("login()....");
		return "/index.jsp";
	}
	//×¢²á
	public String regist(HttpServletRequest request,HttpServletResponse response){
		System.out.println("regist()....");
		return "f:/index.jsp";
	}
	//ÍË³ö
	public String quit(HttpServletRequest request,HttpServletResponse response){
		System.out.println("quit()...");
		return null;
	}
	//²âÊÔ
	public String test(HttpServletRequest request,HttpServletResponse response){
		System.out.println("test()....");
		return "r:/index.jsp";
	}
}
