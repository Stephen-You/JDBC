package com.customer.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.customer.domain.Customer;
import com.customer.domain.PageBean;

import cn.itcast.jdbc.TxQueryRunner;

/**
 * ���ݲ�
 * @author Yorick
 *
 */
public class CustomerDao {
	//����QueryRunner����
	private QueryRunner qr = new TxQueryRunner();
	/**
	 * �����û�
	 */
	public void add(Customer cstm){
		try {
			//����SQLģ��
			String insert = "insert into customer values(?,?,?,?,?,?,?)";
			//����SQLģ���ж�Ӧ���ʺŵ�ֵ
			Object[] objects = {cstm.getCid(),cstm.getCname(),cstm.getGender(),cstm.getBirthday()
					,cstm.getCellphone(),cstm.getEmail(),cstm.getDescription()};
			//�������ݿ�
			qr.update(insert,objects);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ��ѯ���пͻ�
	 */
//	public List<Customer> findAll(){
//		try {
//			//SQLģ��
//			String select = "select * from customer";
//			return qr.query(select, new BeanListHandler<Customer>(Customer.class));
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}
	/**
	 * ��ҳ��ѯ
	 * ������listҳ�淢��,�Ὣ���ݿ���,���е��û���Ϣ����ʾ����,��������ݿ����м���������,��ʱ��ô����,��ҳ��������õ��ֶ�
	 */
	//�������Ϊÿҳ��¼���͵�ǰҳ��
	public PageBean<Customer> findAll(int cp,int ps){
		try {
			//�õ����м�¼����Ŀ
			String sql = "select count(*) from customer";
			Number num = (Number) qr.query(sql, new ScalarHandler());
			int tr = num.intValue();
			//�õ���ǰҳ������
			sql = "select * from customer limit ?,?";
			//��һҳ,��ʾ��1����10������,�ڶ�ҳ��11��20,(pagenumber-1)*10
			Object[] objects = {(cp-1)*ps,ps};
			List<Customer> beans = qr.query(sql, new BeanListHandler<Customer>(Customer.class),objects);
			/*
			 * 3. ʹ��tr��beans��cp��ps����PageBean�����أ�
			 */
			PageBean<Customer> pb = new PageBean<>();
			pb.setBeans(beans);
			pb.setCp(cp);
			pb.setTr(tr);
			pb.setPs(ps);
			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ��cid��ѯ�ͻ�
	 */
	public Customer load(String cid){	
		try {
			//SQLģ��
			String select = "select * from customer where cid=?";
			return qr.query(select, new BeanHandler<Customer>(Customer.class),cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * �޸��û�
	 */
	public void edit(Customer cstm){
		try {
			String update = "update customer set cname=?,gender=?,birthday=?,cellphone=?" +
					", email=?, description=? where cid=?";
			Object[] params = {cstm.getCname(), cstm.getGender(),
				cstm.getBirthday(), cstm.getCellphone(), cstm.getEmail(),
				cstm.getDescription(), cstm.getCid()};
			qr.update(update, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ɾ���û���Ϣ
	 */
	public void delete(String cid){
		try {
			String delete = "delete from customer where cid=?";
			qr.update(delete, cid);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * ��������ϲ�ѯ
	 */
	public PageBean<Customer> query(int cp,int ps,Customer cstm){
		try {
			/*
			 * 1. ƴ��sqlģ��
			 * 2. ����?��Ӧ��ֵ
			 * 3. ����qr��query������ʹ��BeanListHandler
			 */
			//ƴ��SQLģ��
			//����List�洢?
			List<Object> params = new ArrayList<Object>();
			StringBuilder sb= new StringBuilder("from customer where 1=1");
			// �ж��������Ƿ����cname������������������Ӿ䣡
			String cname = cstm.getCname();
			if(cname!=null && !cname.trim().isEmpty()){
				sb.append(" and cname like ?");
				params.add("%"+cname+"%");//������ʺŴ���ʱ�����Ӷ�Ӧ��ֵ��params��
			}
			// �ж��������Ƿ����gender������������������Ӿ䣡
			String gender = cstm.getGender();
			if(gender!=null && !gender.trim().isEmpty()){
				sb.append("and gender=?");
				params.add(gender);
			}
			// �ж��������Ƿ����cellphone������������������Ӿ䣡
			String cellphone = cstm.getCellphone();
			if(cellphone != null && !cellphone.trim().isEmpty()){
				sb.append("and cellphone like ?");
				params.add("%"+cellphone+"%");
			}
			// �ж��������Ƿ����email������������������Ӿ䣡
			String email = cstm.getEmail();
			if(email!=null && !email.trim().isEmpty()){
				sb.append("and email like ?");
				params.add("%"+email+"%");
			}
			// ƴ��count��䣬�����õ��ܼ�¼��
			StringBuilder cnt = new StringBuilder("select count(*)").append(sb);
			Number num = (Number)qr.query(cnt.toString(), new ScalarHandler(), params.toArray());
			int tr = num.intValue();
			// ƴ�ղ�ѯ��ǰҳ��¼��SQL���
			StringBuilder sql = new StringBuilder("select *").append(sb);
			sql.append(" limit ?,?");
			// ���������������ֵ��
			params.add((cp-1)*ps);
			params.add(ps);
			// ��ѯ
			List<Customer> list = qr.query(sql.toString(), new BeanListHandler<Customer>(Customer.class), params.toArray());
			/*
			 * ����PageBean
			 */
			PageBean<Customer> pb = new PageBean<Customer>();
			pb.setCp(cp);
			pb.setPs(ps);
			pb.setTr(tr);
			pb.setBeans(list);
			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
	}
	
}