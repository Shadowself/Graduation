package com.zgy.graduation.server;

import java.util.List;
import java.util.Map;

public interface StoreHouseServer {
	
	//完成用户的添加功能
		public boolean AddStoreHouse(List<Object> params);
		
		public Map<String, Object> FindStoreHouseByName(String str);
		
		public int FindStoreHouseIdByName(String str);
		
		public List<Map<String, Object>> FindAllStoreHouse();
		
		public boolean ChangeStoreHouse(List<Object> params);
		
		public boolean deleteStoreHouse(String str);

}
