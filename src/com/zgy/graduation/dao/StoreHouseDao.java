package com.zgy.graduation.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zgy.graduation.server.StoreHouseServer;
import com.zgy.graduation.util.JdbcUtils;

public class StoreHouseDao implements StoreHouseServer{

	
	private JdbcUtils utils = null;
	
	public StoreHouseDao() {
		utils = new JdbcUtils();
	}
	
	@Override
	public boolean AddStoreHouse(List<Object> params) {
		// TODO Auto-generated method stub
		
		boolean flag = false;

		String sql = "insert into storehouse(storeName,goods) values(?,?)";
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
	public Map<String, Object> FindStoreHouseByName(String storeHouseName) {
		// TODO Auto-generated method stub
		String sql = "select * from storehouse where storeName = ?";
		Map<String, Object> map = null;
		try {
			// 先获得链接
			utils.getConnection();
			List<Object> params = new ArrayList<Object>();
			params.add(storeHouseName);
			utils.getConnection();
			map = utils.findSimpleResult(sql, params);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			// 关闭数据库的链接
			utils.releaseConn();
		}
		
		return map;
	}

	@Override
	public List<Map<String, Object>> FindAllStoreHouse() {
		// TODO Auto-generated method stub
		String sql = "select * from storehouse";
		List<Map<String, Object>> listMap = null;
		try {
			// 先获得链接
			utils.getConnection();
			List<Object> params = new ArrayList<Object>();
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

	@Override
	public boolean ChangeStoreHouse(List<Object> params) {
		// TODO Auto-generated method stub
		boolean flag = false;

		String sql = "update storehouse set storeName = '" + params.get(0) + "',goods = '" + params.get(1) +"' where storeName = '" + params.get(2) +"'";
		try {
			// 先获得链接
			utils.getConnection();
			flag = utils.ChangeByPreparedStatement(sql);
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
	public boolean deleteStoreHouse(String storeHouseName) {
		// TODO Auto-generated method stub
		boolean flag = false;

		String sql = "delete from storehouse where storeName = ?";
		try {
			// 先获得链接
			utils.getConnection();
			List<Object> params = new ArrayList<Object>();
			params.add(storeHouseName);
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
	public int FindStoreHouseIdByName(String str) {
		// TODO Auto-generated method stub
		return 0;
	}
	

	
}
