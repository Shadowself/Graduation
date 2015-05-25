package com.zgy.graduation.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONObject;
import com.zgy.graduation.bean.ReqCmd;
import com.zgy.graduation.dao.pestDao;
import com.zgy.graduation.server.pestService;

@WebServlet("/pestAction")
public class pestAction extends HttpServlet{

	private pestService service = null;
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException {
		// Put your code here
		service = new pestDao();
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
		String path = request.getContextPath();
		response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		// 构建一个文件上传类
		ServletFileUpload servletFileUpload = new ServletFileUpload(
				diskFileItemFactory);
//		servletFileUpload.setFileSizeMax(100 * 1024 * 1024);
		servletFileUpload.setSizeMax(2 * 1024 * 1024 * 1024);// 上传文件总大小
		PrintWriter out = response.getWriter();
		JSONObject responseJson = new JSONObject();
		List<Object> params = new ArrayList<Object>();
		List<FileItem> list = null;
		try {
			list = servletFileUpload.parseRequest(request);
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					if (fileItem.getFieldName().equals("storehouseId")) {
						params.add(fileItem.getString("utf-8"));
					}
					if (fileItem.getFieldName().equals("title")) {
						params.add(fileItem.getString("utf-8"));
					}
				} else {
					try {
						String image = fileItem.getName();
						params.add(image);
						String upload_path = request.getRealPath("/upload");
//						System.out.println("--->>" + upload_path);
						//
//						File real_path = new File("D:/Program Files/workspace/Graduation/upload" + "/" + image);
						File real_path = new File(upload_path + "/" + image);
						fileItem.write(real_path);
						boolean flag = service.AddPest(params);
						
						if(flag){
							responseJson.put(ReqCmd.RESULT, true);
							responseJson.put(ReqCmd.CODE, 0);
							responseJson.put(ReqCmd.MESSAGE, "上传成功");
				        	responseJson.put(ReqCmd.DATA, "该虫为赤拟谷盗，是甲虫类昆虫!");
						}else{
							responseJson.put(ReqCmd.RESULT, false);
							responseJson.put(ReqCmd.CODE, 0);
							responseJson.put(ReqCmd.MESSAGE, "上传失败");
				        	responseJson.put(ReqCmd.DATA, "");
						}
						// 把数据插入到数据库中
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(responseJson.toJSONString());
		out.flush();
        out.close();
		
	}
	
	

}
