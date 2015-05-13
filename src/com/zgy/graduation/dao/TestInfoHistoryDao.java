package com.zgy.graduation.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zgy.graduation.server.TestInfoHistoryServer;
import com.zgy.graduation.util.JdbcUtils;

public class TestInfoHistoryDao implements TestInfoHistoryServer{

	
	private JdbcUtils utils = null;
	
	public TestInfoHistoryDao() {
		utils = new JdbcUtils();
	}
	
	@Override
	public boolean addTestInfo(List<Object> params) {
		// TODO Auto-generated method stub
		boolean flag = false;

		String sql = "insert into testhistory(testInfor_id_fk,place,time,temperature_goods,dampness_goods,pestKind,pestNumber,testResult) values(?,?,?,?,?,?,?,?)";
		try {
			// 先获得链接
			utils.getConnection();
			flag = utils.updateByPreparedStatement(sql, params);
			System.out.println("-flag-->>" + flag);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			// 关闭数据库的链接
			utils.releaseConn();
		}
		return flag;
		
	}

	@Override
	public List<Map<String, Object>> FindAllTestInfoHistory(String storehouseId) {
		// TODO Auto-generated method stub
		String sql = "select * from testhistory where testInfor_id_fk = ?";
		List<Map<String, Object>> listMap = null;
		try {
			// 先获得链接
			utils.getConnection();
			List<Object> params = new ArrayList<Object>();
			params.add(storehouseId);
			utils.getConnection();
			listMap = utils.findMoreResult(sql, params);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			// 关闭数据库的链接
			utils.releaseConn();
		}
		
		return listMap;
	}

}
