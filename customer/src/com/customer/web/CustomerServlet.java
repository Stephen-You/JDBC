package com.customer.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;

import com.customer.domain.Customer;
import com.customer.domain.PageBean;
import com.customer.service.CustomerService;
import com.customer.utils.BaseServlet;
@WebServlet("/CustomerServlet")
public class CustomerServlet extends BaseServlet{
	private CustomerService service = new CustomerService();
	/**
	 * 添加客户
	 * @param request
	 * @param response
	 * @return
	 */
	public String add(HttpServletRequest request,HttpServletResponse response){
		/*
		 * 1. 封装表单数据到Customer对象中
		 * 1.5 给Customer中数据进行补全！补cid
		 * 2. 把Customer对象传递给customerService#add()方法，完成添加业务
		 * 3. 调用本类的findAll()方法完成显示所有客户
		 */
		Customer cstm = CommonUtils.toBean(request.getParameterMap(), Customer.class);
		cstm.setCid(CommonUtils.uuid());
		service.add(cstm);
		return findAll(request, response);
	}
	/**
	 * 查询所有客户
	 */
//	public String findAll(HttpServletRequest request,HttpServletResponse response){
//		List<Customer> cstmList = service.findAll();
//		request.setAttribute("cstmList", cstmList);
//		return "f:/list.jsp";
//	}
	public String findAll(HttpServletRequest request,HttpServletResponse response){
		/*
		 * 1. 得到cp
		 */
		int cp = 1;//默认为1
		String param = request.getParameter("cp");
		if(param != null && !param.trim().isEmpty()) {
			cp = Integer.parseInt(param);
		}
		
		/*
		 * 2. 设置ps
		 */
		int ps = 10;
		PageBean<Customer> pb = service.findAll(cp,ps);
		/*
		 * 给pb的url赋值
		 * url = contextPath + servletpath + queryString
		 */
		String url = request.getRequestURI() + "?" + request.getQueryString();
		int index = url.lastIndexOf("&cp=");
		if(index != -1) {
			url = url.substring(0, index);
		}
		
		pb.setUrl(url);
		/*
		 * 4. 保存到request域中，转发到list.jsp
		 */
		request.setAttribute("pb", pb);
		return "f:/list.jsp";
	}
	/**
	 * 加载客户为编辑
	 */
	public String load(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("cstm",service.load(request.getParameter("cid")));
		return "f:/edit.jsp";
	}
	/**
	 * 修改客户信息
	 */
	public String edit(HttpServletRequest request,HttpServletResponse response){
		//封装表单数据到JavaBean
		Customer cstm = CommonUtils.toBean(request.getParameterMap(), Customer.class);
		service.edit(cstm);
		return findAll(request, response);
	}
	/**
	 * 删除用户信息
	 */
	public String delete(HttpServletRequest request,HttpServletResponse response){
		service.delete(request.getParameter("cid"));
		return findAll(request, response);
	}
	/**
	 * 条件查询
	 * @throws UnsupportedEncodingException 
	 */
	public String query(HttpServletRequest request,HttpServletResponse response) throws IOException{
		/*
		 * 封装表单参数到Customer对象中
		 * 表单中的数据就是查询条件
		 */
		/*
		 * 处理GET请求编码问题
		 * 1. 表单为GET
		 * 2. 超链接也为GET
		 */
		Customer cstm = CommonUtils.toBean(request.getParameterMap(), Customer.class);
		if(cstm.getCname()!=null&&!cstm.getCname().trim().isEmpty())
		cstm.setCname(new String(cstm.getCname().getBytes("ISO-8859-1"), "UTF-8"));
		if(cstm.getGender()!=null&&!cstm.getGender().trim().isEmpty())
		cstm.setGender(new String(cstm.getGender().getBytes("ISO-8859-1"), "UTF-8"));
		if(cstm.getCellphone()!=null&&!cstm.getCellphone().trim().isEmpty())
		cstm.setCellphone(new String(cstm.getCellphone().getBytes("ISO-8859-1"), "UTF-8"));
		if(cstm.getEmail()!=null&&!cstm.getEmail().trim().isEmpty())
		cstm.setEmail(new String(cstm.getEmail().getBytes("ISO-8859-1"), "UTF-8"));
		//得到cp
		int cp = 1;
		String param = request.getParameter("cp");
		if(param!=null && !param.trim().isEmpty()){
			cp = Integer.parseInt(param);
		}
		//设置ps
		int ps = 10;
		//使用cp和ps调用service方法，得到PageBean
		PageBean<Customer> pb = service.query(cp, ps, cstm);
		/*
		 * 给pb的url赋值
		 * url = contextPath + servletpath + queryString
		 */
		String url = request.getRequestURI() + "?" + request.getQueryString();
		int index = url.lastIndexOf("&cp=");
		if(index != -1) {
			url = url.substring(0, index);
		}
		
		pb.setUrl(url);
		request.setAttribute("pb",pb);
		return "f:/list.jsp";
	}
}
