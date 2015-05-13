package com.zgy.graduation.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zgy.graduation.bean.ReqCmd;
import com.zgy.graduation.dao.GoodInfoDao;
import com.zgy.graduation.util.IOUtils;

@WebServlet("/GoodsInfoAction")
public class GoodsInfoAction extends HttpServlet{
	/**
	 */
	private static final long serialVersionUID = 1L;
	private GoodInfoDao service;
	
	public void init() throws ServletException {
		// Put your code here
		service = new GoodInfoDao();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String requsetString = IOUtils.stream2String(request.getInputStream());
		JSONObject json = JSONObject.parseObject(requsetString);
		String storehouseId = json.getString("storeHouseId");
		JSONObject responseJson = new JSONObject();
		
		out.print(findAllHistory(storehouseId).toJSONString());
		out.flush();
		out.close();
	}
	
	
	public JSONObject findAllHistory(String storehouseId){
		JSONObject responseJson = new JSONObject();
		List<Map<String, Object>> listMap = null;
		try {
			listMap = service.FindGoodsInfo(storehouseId);
			if (listMap.size() > 0) {
				responseJson.put(ReqCmd.RESULT, true);
				responseJson.put(ReqCmd.CODE, 0);
				responseJson.put(ReqCmd.MESSAGE, "获取成功！");
				Map<String, Object> map = null;
				JSONArray jsonArray = new JSONArray();
				for (Map<String, Object> m : listMap) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", m.get("id"));
					jsonObject.put("storehouse_id", m.get("goodsInfo_storehouseId_fk"));
					jsonObject.put("temperature_goods", m.get("temperature"));
					jsonObject.put("dampness_goods", m.get("dampness"));
					jsonObject.put("pestKind", m.get("pestKind"));
					jsonObject.put("pestNumber", m.get("pestNumber"));
					
					jsonArray.add(jsonObject);
				}

				responseJson.put(ReqCmd.DATA, jsonArray.toJSONString());
			} else {
				responseJson.put(ReqCmd.RESULT, true);
				responseJson.put(ReqCmd.CODE, 1);
				responseJson.put(ReqCmd.MESSAGE, "该仓库暂时没有传感器数据！");
				responseJson.put(ReqCmd.DATA, "");
			}
		} catch (Exception e) {
			responseJson.put(ReqCmd.RESULT, false);
			responseJson.put(ReqCmd.CODE, 2);
			responseJson.put(ReqCmd.MESSAGE, "获取信息失败！" + e.toString());
			responseJson.put(ReqCmd.DATA, "");
		}

		return responseJson;
	}
	
	
}
