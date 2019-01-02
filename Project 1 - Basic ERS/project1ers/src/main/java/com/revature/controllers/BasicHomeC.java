package com.revature.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.BEDaoImp;
import com.revature.model.BasicEmployee;
import com.revature.service.BasicService;

/**
 * Servlet implementation class BasicHomeC
 */
public class BasicHomeC extends DefaultServlet {
	private static final long serialVersionUID = 1L;
	
	final static Logger hcLogger = Logger.getLogger(BasicHomeC.class);

    /**
     * Default constructor. 
     */
    public BasicHomeC() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		hcLogger.info("pressed Manager Home button");
		request.getRequestDispatcher("mhome.html").forward(request, response);
		hcLogger.info("session: "+request.getSession().getAttribute("user"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String urlPattern = request.getRequestURI();
		hcLogger.info("request uri: "+urlPattern);
		//if (urlPattern.endsWith("login")) { // should put this as method in service or request helper class
			String[] inputStrs = request.getReader().readLine().split(" ");
			hcLogger.info("fc doPost read this request body: "+inputStrs);
			
			if (BasicService.getService().login(inputStrs[0], inputStrs[1])) {
				// get this user's session
				HttpSession session = request.getSession();
				session.setAttribute("user", inputStrs[0]);
				BasicEmployee emp = BEDaoImp.getBEDao().selectEmp(inputStrs[0]);
				// put emp info in a JSON - maybe later better
				ObjectMapper mapper = new ObjectMapper(); // create an object mapper (Jackson)
				response.setHeader("Content-Type", "application/json");
				mapper.writeValue(response.getOutputStream(), emp); // marshal bean and output
			} else {
				// set header to utf-8?
				response.getWriter().print("Login failed."); 
				
			}
		/*
		if (urlPattern.endsWith("logout")) {
			request.getSession().invalidate();
			response.sendRedirect("/");
		}
		*/
		
		
	}

}
