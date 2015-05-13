package com.zgy.graduation.dao;

import java.util.List;
import java.util.Map;

import com.zgy.graduation.server.LoginService;
import com.zgy.graduation.util.JdbcUtils;

public class LoginDao implements LoginService {

	private JdbcUtils jdbcUtils = null;

	public LoginDao() {
		// TODO Auto-generated constructor stub
		jdbcUtils = new JdbcUtils();
	}

	public boolean login(List<Object> params) {
		// TODO Auto-generated method stub
		boolean flag = false;
		String sql = "select * from userInfo where userName=? and password=?";
		try {
			jdbcUtils.getConnection();
			Map<String, Object> map = jdbcUtils.findSimpleResult(sql, params);
			flag = !map.isEmpty() ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

}
