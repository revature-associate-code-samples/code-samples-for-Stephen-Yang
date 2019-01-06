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
        
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String urlPattern = request.getRequestURI();
		hcLogger.info("request uri: "+urlPattern);
			String[] inputStrs = request.getReader().readLine().split(" ");
			
			if (BasicService.getService().login(inputStrs[0], inputStrs[1])) {
				HttpSession session = request.getSession();
				session.setAttribute("user", inputStrs[0]);
				hcLogger.info("session: "+request.getSession().getAttribute("user"));
				BasicEmployee emp = BEDaoImp.getBEDao().selectEmp(inputStrs[0]);
				ObjectMapper mapper = new ObjectMapper(); 
				response.setHeader("Content-Type", "application/json");
				mapper.writeValue(response.getOutputStream(), emp);
			} else {
				response.getWriter().print("Login failed."); 
				
			}
		
	}

}
