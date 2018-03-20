package com.itcast.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.service.UserService;

public class ActiveServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String activeCode = request.getParameter("activeCode");
		UserService service = new UserService();
		service.active(activeCode);
		//跳转登陆
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}