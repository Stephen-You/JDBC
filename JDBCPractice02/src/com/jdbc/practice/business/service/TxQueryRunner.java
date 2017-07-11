package com.jdbc.practice.business.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;


public class TxQueryRunner extends QueryRunner{

	@Override
	public int[] batch(String sql, Object[][] params) throws SQLException {
		Connection con = JdbcUtils.getConnection();
		int[] resultValue =  super.batch(con, sql, params);
		JdbcUtils.releaseConnection(con);
		return resultValue;
	}

	@Override
	public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params)
			throws SQLException {
		Connection con = JdbcUtils.getConnection();
		T resultValue =  super.query(con, sql, rsh, params);
		JdbcUtils.releaseConnection(con);
		return resultValue;
	}

	@Override
	public <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
		Connection con = JdbcUtils.getConnection();
		T resultValue =  super.query(con, sql, rsh);
		JdbcUtils.releaseConnection(con);
		return resultValue;
	}

	@Override
	public int update(String sql) throws SQLException {
		Connection con = JdbcUtils.getConnection();
		int resultValue =  super.update(con, sql);
		JdbcUtils.releaseConnection(con);
		return resultValue;
	}

	@Override
	public int update(String sql, Object param) throws SQLException {
		Connection con = JdbcUtils.getConnection();
		int resultValue =  super.update(con, sql, param);
		JdbcUtils.releaseConnection(con);
		return resultValue;
	}

	@Override
	public int update(String sql, Object... params) throws SQLException {
		Connection con = JdbcUtils.getConnection();
		int resultValue =  super.update(con, sql, params);
		JdbcUtils.releaseConnection(con);
		return resultValue;
	}
}
