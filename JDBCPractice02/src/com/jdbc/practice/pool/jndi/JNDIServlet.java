package com.jdbc.practice.pool.jndi;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JNDIServlet
 */
@WebServlet("/JNDIServlet")
public class JNDIServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JNDIPracticeDAO dao = new JNDIPracticeDAO();
		dao.xxx();
	}

}
