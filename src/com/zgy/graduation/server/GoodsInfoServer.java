package com.zgy.graduation.server;

import java.util.List;
import java.util.Map;

public interface GoodsInfoServer {
	
	public List<Map<String, Object>> FindGoodsInfo(String storehouseId);
	
}
