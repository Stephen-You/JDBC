package com.you.web;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.vcode.utils.VerifyCode;

/**
 * ��ȡͼƬ��֤��
 * @author Yorick
 *
 */
@WebServlet("/VerifyCodeServlet")
public class VerifyCodeServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡͼƬ��֤�����
		VerifyCode vc = new VerifyCode();
		BufferedImage image = vc.getImage();
		System.out.println("����...");
		//��ȡ��֤��
		String code = vc.getText();
		//����֤�뱣�浽session
		request.getSession().setAttribute("code", code);
		//ͼƬ������ͻ���
		response.setContentType("image/jpeg");
		ImageIO .write(image, "JPEG", response.getOutputStream());
	}

}
