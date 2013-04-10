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

import com.irongroup.response.CommandResponse;
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
		String response=CommandResponse.execute(map);
		
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(response);
		
		
	}

}
