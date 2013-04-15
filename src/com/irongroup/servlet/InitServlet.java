package com.irongroup.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.irongroup.unit.DataInitUtil;

public class InitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String parameter=req.getParameter("a");
		if (parameter.equals("a") ) {
			DataInitUtil.initCache();
		}else if(parameter.equals("b")){
			DataInitUtil.initTop200();
		}
		resp.getWriter().print("done.");
	}
	
}
