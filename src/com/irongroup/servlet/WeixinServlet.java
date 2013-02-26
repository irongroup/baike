package com.irongroup.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.irongroup.unit.CommonUnit;

public class WeixinServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String TOKEN = "";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String[] tempstr = new String[] { TOKEN, timestamp, nonce };
		Arrays.sort(tempstr);
		String tempString = tempstr[0] + tempstr[1] + tempstr[2];
		try {
			String sha = CommonUnit.getSHAString(tempString);
			if (signature.equals(sha)) {
				resp.getWriter().write(req.getParameter("echostr"));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}

}
