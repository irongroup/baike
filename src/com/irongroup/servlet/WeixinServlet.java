package com.irongroup.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.irongroup.response.PicMessageResponse;
import com.irongroup.unit.CommonUnit;
import com.irongroup.unit.DomParse;
import com.irongroup.unit.PrivateCache;
import com.irongroup.unit.XmlDocument;
import com.sina.sae.fetchurl.SaeFetchurl;
import com.sina.sae.memcached.SaeMemcache;

public class WeixinServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String TOKEN = "rice";
	private static Logger logger = Logger.getLogger("Servlet");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		logger.info("message:" + signature + ":" + timestamp + ":" + nonce);
		String[] tempstr = new String[] { TOKEN, timestamp, nonce };
		Arrays.sort(tempstr);
		String tempString = tempstr[0] + tempstr[1] + tempstr[2];
		try {
			String sha = CommonUnit.getSHAString(tempString);
			if (signature.equalsIgnoreCase(sha)) {
				resp.getWriter().write(req.getParameter("echostr"));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("get message...");
		req.getQueryString();
		BufferedReader sis = req.getReader();
		char[] buf = new char[1024];
		int len = 0;
		StringBuffer sb = new StringBuffer();
		while ((len = sis.read(buf)) != -1) {
			sb.append(buf, 0, len);
		}
		String message="<?xml version=\"1.0\" encoding=\"utf-8\" ?>"+sb.toString();
		XmlDocument xd=new DomParse();
		InputStream	 is=new ByteArrayInputStream(message.getBytes());
		Map<String, String> map= xd.parserXml(is);
		String response="";
		String queryString=map.get("Content");
		if(map.get("MsgType").equals("event")&&map.get("Event").equals("subscribe")){
			StringBuffer temp=new StringBuffer("<xml>");
			temp.append("<ToUserName><![CDATA[");
			temp.append(map.get("FromUserName"));
			temp.append("]]></ToUserName>");
			temp.append("<FromUserName><![CDATA[");
			temp.append(map.get("ToUserName"));
			temp.append("]]></FromUserName>");
			temp.append("<CreateTime>");
			temp.append(System.currentTimeMillis());
			temp.append("</CreateTime>");
			temp.append("<MsgType><![CDATA[");
			temp.append("text");
			temp.append("]]></MsgType>");
			temp.append("<Content><![CDATA[");
			temp.append("欢迎订阅百科，基于龙空数据方便书友查阅最新网文推荐榜。回复m查看评分最高十部网文，回复n查看下十条。欢迎反馈");
			temp.append("]]></Content>");
			temp.append("<FuncFlag>");
			temp.append("0");
			temp.append("</FuncFlag>");
			temp.append("</xml>");
			response=temp.toString();
		}else if (queryString.equalsIgnoreCase("M")) {
//			SaeMemcache mc=new SaeMemcache("127.0.0.1", 11211);
//			mc.init();
			if (PrivateCache.get("top250")==null) {
				SaeFetchurl fetchUrl = new SaeFetchurl();
				String content = fetchUrl.fetch("http://api.lkong.net/top250.json");
				logger.debug("json:"+content);
				List<JSONObject> list=new ArrayList<JSONObject>();
				JSONArray jsonArray=JSONArray.fromObject(content);
				for (int i = 0; i < jsonArray.size(); i++) {
					list.add(jsonArray.getJSONObject(i));
				}
				PrivateCache.set("top250", list);
			}
			
			response=PicMessageResponse.getPicMessageResponse(map);
		}else if (queryString.equalsIgnoreCase("n")) {
			if (PrivateCache.get("top250")==null) {
				SaeFetchurl fetchUrl = new SaeFetchurl();
				String content = fetchUrl.fetch("http://api.lkong.net/top250.json");
				logger.debug("json:"+content);
				List<JSONObject> list=new ArrayList<JSONObject>();
				JSONArray jsonArray=JSONArray.fromObject(content);
				for (int i = 0; i < jsonArray.size(); i++) {
					list.add(jsonArray.getJSONObject(i));
				}
				PrivateCache.set("top250", list);
			}
			response=PicMessageResponse.getNextPicMessageResponse(map);
		}else {
			StringBuffer temp=new StringBuffer("<xml>");
			temp.append("<ToUserName><![CDATA[");
			temp.append(map.get("FromUserName"));
			temp.append("]]></ToUserName>");
			temp.append("<FromUserName><![CDATA[");
			temp.append(map.get("ToUserName"));
			temp.append("]]></FromUserName>");
			temp.append("<CreateTime>");
			temp.append(System.currentTimeMillis());
			temp.append("</CreateTime>");
			temp.append("<MsgType><![CDATA[");
			temp.append("text");
			temp.append("]]></MsgType>");
			temp.append("<Content><![CDATA[");
			temp.append("hello...");
			temp.append("]]></Content>");
			temp.append("<FuncFlag>");
			temp.append("0");
			temp.append("</FuncFlag>");
			temp.append("</xml>");
			response=temp.toString();
		}
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(response);
		
//		 <xml>
//		 <ToUserName><![CDATA[toUser]]></ToUserName>
//		 <FromUserName><![CDATA[fromUser]]></FromUserName>
//		 <CreateTime>12345678</CreateTime>
//		 <MsgType><![CDATA[text]]></MsgType>
//		 <Content><![CDATA[content]]></Content>
//		 <FuncFlag>0</FuncFlag>
//		 </xml>
		
	}

}
