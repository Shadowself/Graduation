package com.zgy.graduation.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zgy.graduation.server.GoodsInfoServer;
import com.zgy.graduation.util.JdbcUtils;

public class GoodInfoDao implements GoodsInfoServer {
	private JdbcUtils utils = null;

	public GoodInfoDao() {
		utils = new JdbcUtils();
	}

	@Override
	public List<Map<String, Object>> FindGoodsInfo(String storehouseId) {
		// TODO Auto-generated method stub
		String sql = "select * from goodsInfo where goodsInfo_storehouseId_fk = ?";
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
