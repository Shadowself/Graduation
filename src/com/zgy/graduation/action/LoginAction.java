package com.zgy.graduation.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.zgy.graduation.bean.ReqCmd;
import com.zgy.graduation.dao.LoginDao;
import com.zgy.graduation.server.LoginService;
import com.zgy.graduation.util.IOUtils;


@WebServlet("/LoginAction")
public class LoginAction extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private LoginService service;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        
        String str1 = IOUtils.stream2String(request.getInputStream());
        
        JSONObject json = JSONObject.parseObject(str1);
        String username = json.getString("username");
        String password = json.getString("password");
        
        List<Object> params = new ArrayList<Object>();
		params.add(username);
		params.add(password);
		boolean flag = service.login(params);
        
    	JSONObject jsonObject = new JSONObject();           
        //判断用户名密码是否正确
        if(flag) {

        	jsonObject.put(ReqCmd.RESULT, true);
        	jsonObject.put(ReqCmd.CODE, 0);
        	jsonObject.put(ReqCmd.MESSAGE, "登陆成功");
        	
        }else {
        	jsonObject.put(ReqCmd.RESULT, false);
        	jsonObject.put(ReqCmd.CODE, 1);
        	jsonObject.put(ReqCmd.MESSAGE, "用户名或密码错误！");
        }
    	jsonObject.put(ReqCmd.DATA, "");
        out.print(jsonObject.toJSONString());
        out.flush();
        out.close();
    }
	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		service = new LoginDao();
	}

}
