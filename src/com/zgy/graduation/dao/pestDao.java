package com.zgy.graduation.dao;

import java.util.List;
import java.util.Map;

import com.zgy.graduation.server.pestService;
import com.zgy.graduation.util.JdbcUtils;

public class pestDao implements pestService{

	private JdbcUtils jdbcUtils = null;

	public pestDao() {
		// TODO Auto-generated constructor stub
		jdbcUtils = new JdbcUtils();
	}
	
	@Override
	public boolean AddPest(List<Object> params) {
		// TODO Auto-generated method stub
		boolean flag = false;
		String sql = "insert into pest(storehouse_id_fk,pestInfo,pestPath) values(?,?,?)";
		try {
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

}
