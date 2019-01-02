package com.revature.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class LogoutC
 */
public class LogoutC extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger outLogger = Logger.getLogger(LogoutC.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse respons;)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		outLogger.info("Log Out button pressed");
		outLogger.info(request.getRequestURI());
		HttpSession currentSess = request.getSession(false);
		try {
			outLogger.info(currentSess.getAttribute("user"));
		} catch (NullPointerException npe) {
			outLogger.warn("session is already null but should not be");
		}
		if (currentSess != null) { currentSess.invalidate(); }
		response.sendRedirect("/project1ers/");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

}
