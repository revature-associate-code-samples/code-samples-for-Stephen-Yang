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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse respons;)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		outLogger.info("Log Out button pressed");
		outLogger.info(request.getRequestURI());
		HttpSession currentSess = request.getSession(false);
		try {
			outLogger.info("session belongs to "+currentSess.getAttribute("user"));
			currentSess.invalidate();
			outLogger.info("logout successful");
			response.sendRedirect("/project1ers/");
		} catch (IllegalStateException ise) {
			outLogger.warn("session is already invalid but should not be");
		}
		
	}

}
