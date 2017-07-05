package com.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import com.jdbc.practice.utils.JDBCUtilsNew;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * ���Ȼع�һ�������ݿ������
 * 	JDBC����,��������,�Ĵ����,��ȡ����,��ȡSQL������
 * 	���ӳ�,�������ӳ������ļ�,��ȡ����
 * 	DBUtils��
 * @author Yorick
 *
 */
public class DBUtilsTest {
	/**
	 * jdbc�� 
	 * 1. �õ�����d 
	 * 2. ����sqlģ�� 
	 * 3. �õ�pstmt 
	 * 4. ���ʺŸ�ֵ 
	 * 5. ִ��
	 * @throws SQLException 
	 */
	@Test
	public void testJDBC() throws SQLException{
		ComboPooledDataSource source = new ComboPooledDataSource();
		Connection conn = source.getConnection();
		String sql ="update account set balance=balance+? where name=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setDouble(1, 10000);
		pstmt.setString(2, "zs");
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
	}
	/**
	 * dbutils�� 
		 * 1. ����sqlģ�� 
		 * 2. ����QueryRunner 
		 * 3. �õ�Connection 
		 * 4. ִ��update����
	 * @throws SQLException 
	 */
	@Test
	public void testDBUtils() throws SQLException{
		String insert = "insert into user values(?,?,?)";
		QueryRunner queryRunner = new QueryRunner();
		queryRunner.update(JDBCUtilsNew.getConnection(),insert,"U_1004","ls","lsls");
	}
	/**
	 * ����JavaBeanUser,�����ݷ�װ��JavaBean
	 * ����һ��������
	 * @throws SQLException 
	 */
	public void addTest(User user) throws SQLException{
		String insert = "insert into user values(?,?,?)";
//		Connection con = JDBCUtilsNew.getConnection();
		Object[] param = {user.getUid(),user.getUsername(),user.getPassword()};
		QueryRunner queryRunner = new QueryRunner(JDBCUtilsNew.getDataSource());
		queryRunner.update(insert, param);
	}
	public void addTest1(User user) throws SQLException{
		String insert = "insert into user values(?,?,?)";
		Connection con = JDBCUtilsNew.getConnection();
		Object[] param = {user.getUid(),user.getUsername(),user.getPassword()};
//		QueryRunner queryRunner = new QueryRunner(JDBCUtilsNew.getDataSource());
		QueryRunner queryRunner = new QueryRunner();
		queryRunner.update(con,insert, param);
	}
	//����������
	@Test
	public void testAdd() throws SQLException{
		User user = new User("U_1005", "½ѷ", "sunshi");
		User user2 = new User("U_1006", "˾��ܲ", "zhangshi");
		addTest(user);
		addTest1(user2);
	}
	/**
	 * ������
	 * @throws SQLException 
	 */
	@Test
	public void batch() throws SQLException{
		String sql = "insert into user values(?,?,?)";
		QueryRunner queryRunner = new QueryRunner(JDBCUtilsNew.getDataSource());
		Object[][] params = {
				{"user1", "�����","����"},
				{"user2", "����","xiaozhi"},
				{"user3", "��ͳ", "���"}
		};
		queryRunner.batch(sql, params);
	}
	/**
	 * QueryRunner query()ʹ��
	 * @throws SQLException 
	 * 
	 */
	@Test
	public void testQuery() throws SQLException{
		String select = "select * from user";
		User user = null;
	
		QueryRunner queryRunner = new QueryRunner(JDBCUtilsNew.getDataSource());
		List<User> list = queryRunner.query(select, new BeanListHandler<>(User.class));
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			User user2 = (User) iterator.next();
			System.out.println(user2.toString());
		}
	}
}