package com.irongroup.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.irongroup.unit.PrivateCache;

public class MockServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(MockServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		SaeFetchurl fetchUrl = new SaeFetchurl();
//		String content = fetchUrl.fetch("http://api.lkong.net/top250.json");
//		List<JSONObject> list1=new ArrayList<JSONObject>();
//		JSONArray jsonArray=JSONArray.fromObject(content);
//		for (int i = 0; i < jsonArray.size(); i++) {
//			list1.add(jsonArray.getJSONObject(i));
//		}
//		PrivateCache.set("top250", list1);
		String parameter = req.getParameter("action");
		resp.setContentType("text/html; charset=utf-8");
		StringBuffer sb = new StringBuffer("<!DOCTYPE HTML>");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("	<meta charset=\"utf-8\"/>");
		sb.append("	<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,user-scalable=no\"/>");
		sb.append("	<title>网文推荐 推荐好书</title>");
		sb.append("	<style type=\"text/css\">");
		sb.append("	h1	{color: #000;font-size: 20px;font-weight: bold;word-break: normal;word-wrap: break-word;}");
		sb.append("	li	{list-style-type:none;font-size:18px;line-height: 150%;position: relative;overflow: visible;}");
		sb.append("	body	{background: #f8f7f5;-webkit-overflow-scrolling: touch;font-family: Helvetica,STHeiti STXihei,Microsoft JhengHei,Microsoft YaHei,Tohoma,Arial;}");
		sb.append("	a	{color: #3e3e3e}");
		sb.append("	</style>");
		sb.append("</head>");
		sb.append("<body>");
		if (parameter.equals("getAll")) {
			sb.append("<header>");
			sb.append("<h1>龙空推书TOP250</h1>");
			sb.append("</header>");
			sb.append("<nav>");
			sb.append("<ul>");
			List<JSONObject> list=(List<JSONObject>) PrivateCache.get("top250");
			for (int i = 0; i < list.size(); i++) {
				JSONObject json= list.get(i);
				sb.append("<li>"+(i+1)+"."+"<a href=\""+json.getString("bookUrl")+"\">"+json.getString("name")+"-"+json.getString("author")+"("+json.getString("rate")+")"+"</a></li>");
			}
			sb.append("<ul>");
			sb.append("</nav>");
		}else if (parameter.equals("qlxs")) {
			sb.append("<header>");
			sb.append("<h1>龙空推书:潜力新书</h1>");
			sb.append("</header>");
			sb.append("<nav>");
			sb.append("<ul>");
			List<JSONObject> list=(List<JSONObject>) PrivateCache.get("qlxs");
			for (int i = 0; i < list.size(); i++) {
				JSONObject json= list.get(i);
				sb.append("<li>"+(i+1)+"."+"<a href=\""+json.getString("bookUrl")+"\">"+json.getString("name")+"-"+"("+json.getString("rate")+")"+"</a></li>");
			}
			sb.append("<ul>");
			sb.append("</nav>");
		}else if (parameter.equals("hslz")) {
			sb.append("<header>");
			sb.append("<h1>龙空推书:好评连载</h1>");
			sb.append("</header>");
			sb.append("<nav>");
			sb.append("<ul>");
			List<JSONObject> list=(List<JSONObject>) PrivateCache.get("hslz");
			for (int i = 0; i < list.size(); i++) {
				JSONObject json= list.get(i);
				sb.append("<li>"+(i+1)+"."+"<a href=\""+json.getString("bookUrl")+"\">"+json.getString("name")+"-"+"("+json.getString("rate")+")"+"</a></li>");
			}
			sb.append("<ul>");
			sb.append("</nav>");
		}else if (parameter.equals("wbhs")) {
			sb.append("<header>");
			sb.append("<h1>龙空推书:完本好书</h1>");
			sb.append("</header>");
			sb.append("<nav>");
			sb.append("<ul>");
			List<JSONObject> list=(List<JSONObject>) PrivateCache.get("wbhs");
			for (int i = 0; i < list.size(); i++) {
				JSONObject json= list.get(i);
				sb.append("<li>"+(i+1)+"."+"<a href=\""+json.getString("bookUrl")+"\">"+json.getString("name")+"-"+"("+json.getString("rate")+")"+"</a></li>");
			}
			sb.append("<ul>");
			sb.append("</nav>");
		}else if (parameter.equals("khbd")) {
			sb.append("<header>");
			sb.append("<h1>龙空推书:葵花宝典</h1>");
			sb.append("</header>");
			sb.append("<nav>");
			sb.append("<ul>");
			List<JSONObject> list=(List<JSONObject>) PrivateCache.get("khbd");
			for (int i = 0; i < list.size(); i++) {
				JSONObject json= list.get(i);
				sb.append("<li>"+(i+1)+"."+"<a href=\""+json.getString("bookUrl")+"\">"+json.getString("name")+"-"+"("+json.getString("rate")+")"+"</a></li>");
			}
			sb.append("<ul>");
			sb.append("</nav>");
		}
		sb.append("</body>");
		sb.append("</html>");
		resp.getWriter().print(sb.toString());
	}

}
