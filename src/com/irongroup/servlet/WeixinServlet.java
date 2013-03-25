package com.irongroup.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.irongroup.unit.CommonUnit;
import com.irongroup.unit.DomParse;
import com.irongroup.unit.XmlDocument;

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
		
		String queryString=map.get("Content");
		
		
		StringBuffer response=new StringBuffer("<xml>");
//		 <xml>
//		 <ToUserName><![CDATA[toUser]]></ToUserName>
//		 <FromUserName><![CDATA[fromUser]]></FromUserName>
//		 <CreateTime>12345678</CreateTime>
//		 <MsgType><![CDATA[text]]></MsgType>
//		 <Content><![CDATA[content]]></Content>
//		 <FuncFlag>0</FuncFlag>
//		 </xml>
		response.append("<ToUserName><![CDATA[");
		response.append(map.get("FromUserName"));
		response.append("]]></ToUserName>");
		response.append("<FromUserName><![CDATA[");
		response.append(map.get("ToUserName"));
		response.append("]]></FromUserName>");
		response.append("<CreateTime>");
		response.append(System.currentTimeMillis());
		response.append("</CreateTime>");
		response.append("<MsgType><![CDATA[");
		response.append("text");
		response.append("]]></MsgType>");
		response.append("<Content><![CDATA[");
		response.append("hello...");
		response.append("]]></Content>");
		response.append("<FuncFlag>");
		response.append("0");
		response.append("</FuncFlag>");
		response.append("</xml>");
		logger.info(sb.toString()); // sb为POST过来的数据
		logger.info(response.toString());
		resp.getWriter().print(response.toString());
		logger.info(req.getQueryString());
		logger.info(req.getPathInfo());
		logger.info(req.getRequestURI());
	}

}
