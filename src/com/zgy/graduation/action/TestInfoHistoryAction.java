package com.zgy.graduation.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.zgy.graduation.dao.TestInfoHistoryDao;
import com.zgy.graduation.util.IOUtils;

@WebServlet("/TestInfoHistoryAction")
public class TestInfoHistoryAction extends HttpServlet{

	/**
	 */
	private static final long serialVersionUID = 2928646480967121102L;
	private TestInfoHistoryDao service;

	public void init() throws ServletException {
		// Put your code here
		service = new TestInfoHistoryDao();
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
		int flag = json.getIntValue("flag");
		String storehouseId = json.getString("storeHouseId");
		JSONObject responseJson = new JSONObject();
		switch (flag) {
		case 1:
			out.print(addTestInfoHistory(json).toJSONString());
			break;
		case 2:
			out.print(findAllHistory(storehouseId).toJSONString());
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
	
	public JSONObject addTestInfoHistory(JSONObject json){
		JSONObject responseJson = new JSONObject();
		String storehouseId = json.getString("storeHouseId");
		String place = json.getString("place");
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		String time = dateFormat.format(now); 
		
		String temperature_goods = json.getString("temperature_goods");
		String dampness_goods = json.getString("dampness_goods");
		String pestKind = json.getString("pestKind");
		String pestNumber = json.getString("pestNumber");
		String testResult ;
		List<Object> params = new ArrayList<Object>();
		params.add(storehouseId);
		params.add(place);
		params.add(time);
		params.add(temperature_goods);
		params.add(dampness_goods);
		params.add(pestKind);
		params.add(pestNumber);
		if(pestKind.equals("无")){
			testResult = "该区域无害虫，";
			
		}else{
			testResult = "该区域有害虫" + pestKind + "为防止虫害，保证仓库粮食完好，请及时采取措施";
		}
		
		if(Integer.valueOf(temperature_goods) > 28){
			testResult = testResult + "温度高于警戒值28℃，";
		}else if(Integer.valueOf(temperature_goods) < 23){
			testResult = testResult + "温度低于警戒值23℃，";
		}else{
			testResult = testResult + "温度适宜，";
		}
		
		if(Integer.valueOf(dampness_goods) > 40){
			testResult = testResult + "湿度高于警戒值40%rh。";
		}else if(Integer.valueOf(dampness_goods) < 20){
			testResult = testResult + "湿度低于警戒值20%rh。";
		}else{
			testResult = testResult + "湿度适宜。";
		}
		
		params.add(testResult);
		
		boolean success = service.addTestInfo(params);
		if (success) {
			responseJson.put(ReqCmd.RESULT, true);
			responseJson.put(ReqCmd.CODE, 0);
			responseJson.put(ReqCmd.MESSAGE, testResult);
			responseJson.put(ReqCmd.DATA, "");
		} else {
			responseJson.put(ReqCmd.RESULT, false);
			responseJson.put(ReqCmd.CODE, 1);
			responseJson.put(ReqCmd.MESSAGE, "提交失败！");
			responseJson.put(ReqCmd.DATA, "");
		}
		
		return responseJson;
	}
	
	public JSONObject findAllHistory(String storehouseId){
		JSONObject responseJson = new JSONObject();
		List<Map<String, Object>> listMap = null;
		try {
			listMap = service.FindAllTestInfoHistory(storehouseId);
			if (listMap.size() > 0) {
				responseJson.put(ReqCmd.RESULT, true);
				responseJson.put(ReqCmd.CODE, 0);
				responseJson.put(ReqCmd.MESSAGE, "获取成功！");
				Map<String, Object> map = null;
				JSONArray jsonArray = new JSONArray();
				for (Map<String, Object> m : listMap) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", m.get("id"));
					jsonObject.put("storehouse_id", m.get("testInfor_id_fk"));
					jsonObject.put("place", m.get("place"));
					jsonObject.put("time", m.get("time"));
					jsonObject.put("temperature_goods", m.get("temperature_goods"));
					jsonObject.put("dampness_goods", m.get("dampness_goods"));
					jsonObject.put("pestKind", m.get("pestKind"));
					jsonObject.put("pestNumber", m.get("pestNumber"));
					jsonObject.put("testResult", m.get("testResult"));
					
					jsonArray.add(jsonObject);
				}

				responseJson.put(ReqCmd.DATA, jsonArray.toJSONString());
			} else {
				responseJson.put(ReqCmd.RESULT, true);
				responseJson.put(ReqCmd.CODE, 1);
				responseJson.put(ReqCmd.MESSAGE, "暂时没有信息！");
				responseJson.put(ReqCmd.DATA, "");
			}
		} catch (Exception e) {
			responseJson.put(ReqCmd.RESULT, false);
			responseJson.put(ReqCmd.CODE, 2);
			responseJson.put(ReqCmd.MESSAGE, "获取历史信息失败！" + e.toString());
			responseJson.put(ReqCmd.DATA, "");
		}

		return responseJson;
	}
	

}
