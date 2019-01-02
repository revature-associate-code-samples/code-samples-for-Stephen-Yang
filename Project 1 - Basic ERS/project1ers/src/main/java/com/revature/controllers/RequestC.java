package com.revature.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.service.BasicService;

/**
 * Servlet implementation class RequestC
 */
public class RequestC extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger rcLogger = Logger.getLogger(RequestC.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String urlPattern = request.getRequestURI();
		rcLogger.info("request uri: "+urlPattern);
		String reqStr = request.getReader().readLine();
		if (reqStr.startsWith("a") || reqStr.startsWith("d")) {
			String[] reqArr = reqStr.split(" ");
			if (!BasicService.getService().resolveReq(reqArr[0], Integer.parseInt(reqArr[1]))) {
				response.setStatus(404);
			}
			
		} else {
			double inAmt = Double.parseDouble(reqStr);
			if (!BasicService.getService().makeReq(inAmt)) {
				response.setStatus(500);
			}
		}
	}

}
