package com.zgy.graduation.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zgy.graduation.bean.ReqCmd;
import com.zgy.graduation.dao.StoreHouseDao;
import com.zgy.graduation.server.StoreHouseServer;
import com.zgy.graduation.util.IOUtils;

@WebServlet("/StoreHouseAction")
public class StoreHouseAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private StoreHouseServer service;

	public void init() throws ServletException {
		// Put your code here
		service = new StoreHouseDao();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String requsetString = IOUtils.stream2String(request.getInputStream());

		JSONObject json = JSONObject.parseObject(requsetString);
		int flag = json.getIntValue("flag");
		JSONObject responseJson = new JSONObject();
		switch (flag) {
		case 1:
			out.print(AddStoreHouseAction(json).toJSONString());
			break;
		case 2:

			out.print(ChangeStoreHouseAction(json).toJSONString());

			break;

		case 3:
			out.print(deleteStoreHouseAction(json).toJSONString());
			break;

		case 4:
			out.print(findAllStorehouse().toJSONString());
			break;

		default:
			responseJson.put(ReqCmd.RESULT, false);
			responseJson.put(ReqCmd.CODE, 99);
			responseJson.put(ReqCmd.MESSAGE, "找不到对应的flag");
			responseJson.put(ReqCmd.DATA, "");
			out.print(responseJson.toJSONString());
			break;
		}
		out.flush();
		out.close();

	}

	public JSONObject AddStoreHouseAction(JSONObject json) {
		String storeHouseName = json.getString("storeHouseName");
		String goods = json.getString("goods");
		List<Object> params = new ArrayList<Object>();
		params.add(storeHouseName);
		params.add(goods);
		JSONObject responseJson = new JSONObject();
		Map<String, Object> mapResult = service
				.FindStoreHouseByName(storeHouseName);
		if (mapResult.size() != 0) {
			responseJson.put(ReqCmd.RESULT, false);
			responseJson.put(ReqCmd.CODE, 3);
			responseJson.put(ReqCmd.MESSAGE, "添加失败，该仓库已存在！");
			responseJson.put(ReqCmd.DATA, "");
		} else {
			try {
				boolean success = service.AddStoreHouse(params);
				if (success) {
					responseJson.put(ReqCmd.RESULT, true);
					responseJson.put(ReqCmd.CODE, 0);
					responseJson.put(ReqCmd.MESSAGE, "添加成功！");
					responseJson.put(ReqCmd.DATA, "");
				} else {
					responseJson.put(ReqCmd.RESULT, false);
					responseJson.put(ReqCmd.CODE, 1);
					responseJson.put(ReqCmd.MESSAGE, "添加失败！");
					responseJson.put(ReqCmd.DATA, "");
				}
			} catch (Exception e) {
				responseJson.put(ReqCmd.RESULT, false);
				responseJson.put(ReqCmd.CODE, 2);
				responseJson.put(ReqCmd.MESSAGE, "添加失败！" + e.toString());
				responseJson.put(ReqCmd.DATA, "");
			}
		}

		return responseJson;

	}

	public JSONObject ChangeStoreHouseAction(JSONObject json) {
		String oldStoreHouse = json.getString("storeHouseId");
		String storeHouseName = json.getString("storeHouseName");
		String goods = json.getString("goods");
		List<Object> params = new ArrayList<Object>();
		params.add(storeHouseName);
		params.add(goods);
		params.add(oldStoreHouse);
		JSONObject responseJson = new JSONObject();
		if (service.FindStoreHouseByName(oldStoreHouse).size() == 0
				|| service.FindStoreHouseByName(storeHouseName).size() != 0) {
			responseJson.put(ReqCmd.RESULT, false);
			responseJson.put(ReqCmd.CODE, 3);
			responseJson.put(ReqCmd.MESSAGE, "修改失败，该仓库不存在！或者新仓库名已存在！");
			responseJson.put(ReqCmd.DATA, "");
		} else {
			try {
				boolean success = service.ChangeStoreHouse(params);
				if (success) {
					responseJson.put(ReqCmd.RESULT, true);
					responseJson.put(ReqCmd.CODE, 0);
					responseJson.put(ReqCmd.MESSAGE, "修改成功！");
					responseJson.put(ReqCmd.DATA, "");
				} else {
					responseJson.put(ReqCmd.RESULT, false);
					responseJson.put(ReqCmd.CODE, 1);
					responseJson.put(ReqCmd.MESSAGE, "修改失败！");
					responseJson.put(ReqCmd.DATA, "");
				}
			} catch (Exception e) {
				responseJson.put(ReqCmd.RESULT, false);
				responseJson.put(ReqCmd.CODE, 2);
				responseJson.put(ReqCmd.MESSAGE, "修改失败！" + e.toString());
				responseJson.put(ReqCmd.DATA, "");
			}
		}

		return responseJson;
	}

	public JSONObject findAllStorehouse() {
		JSONObject responseJson = new JSONObject();
		List<Map<String, Object>> listMap = null;
		try {
			listMap = service.FindAllStoreHouse();
			if (listMap.size() > 0) {
				responseJson.put(ReqCmd.RESULT, true);
				responseJson.put(ReqCmd.CODE, 0);
				responseJson.put(ReqCmd.MESSAGE, "获取成功！");
				Map<String, Object> map = null;
				JSONArray jsonArray = new JSONArray();
				for (Map<String, Object> m : listMap) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("storehouseId", m.get("id"));
					jsonObject.put("storehouseName", m.get("storeName"));
					jsonObject.put("goods", m.get("goods"));
					jsonArray.add(jsonObject);
				}

				responseJson.put(ReqCmd.DATA, jsonArray.toJSONString());
			} else {
				responseJson.put(ReqCmd.RESULT, true);
				responseJson.put(ReqCmd.CODE, 1);
				responseJson.put(ReqCmd.MESSAGE, "暂时没有仓库！");
				responseJson.put(ReqCmd.DATA, "");
			}
		} catch (Exception e) {
			responseJson.put(ReqCmd.RESULT, false);
			responseJson.put(ReqCmd.CODE, 2);
			responseJson.put(ReqCmd.MESSAGE, "获取仓库信息失败！" + e.toString());
			responseJson.put(ReqCmd.DATA, "");
		}

		return responseJson;
	}

	
	public JSONObject deleteStoreHouseAction(JSONObject json) {
		String storeHouseName = json.getString("storeHouseId");
		JSONObject responseJson = new JSONObject();
		if (service.FindStoreHouseByName(storeHouseName).size() == 0) {
			responseJson.put(ReqCmd.RESULT, false);
			responseJson.put(ReqCmd.CODE, 3);
			responseJson.put(ReqCmd.MESSAGE, "删除失败，该仓库不存在！");
			responseJson.put(ReqCmd.DATA, "");
		} else {
			try {
				boolean success = service.deleteStoreHouse(storeHouseName);
				if (success) {
					responseJson.put(ReqCmd.RESULT, true);
					responseJson.put(ReqCmd.CODE, 0);
					responseJson.put(ReqCmd.MESSAGE, "删除成功！");
					responseJson.put(ReqCmd.DATA, "");
				} else {
					responseJson.put(ReqCmd.RESULT, false);
					responseJson.put(ReqCmd.CODE, 1);
					responseJson.put(ReqCmd.MESSAGE, "删除失败！");
					responseJson.put(ReqCmd.DATA, "");
				}
			} catch (Exception e) {
				responseJson.put(ReqCmd.RESULT, false);
				responseJson.put(ReqCmd.CODE, 2);
				responseJson.put(ReqCmd.MESSAGE, "删除失败！" + e.toString());
				responseJson.put(ReqCmd.DATA, "");
			}
		}

		return responseJson;
	}
	
}
