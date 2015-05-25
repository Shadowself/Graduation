package com.zgy.graduation.server;

import java.util.List;
import java.util.Map;

public interface TestInfoHistoryServer {
	
	public boolean addTestInfo(List<Object> params);
	
	public List<Map<String, Object>> FindAllTestInfoHistory(String storehouseId);
	
}
