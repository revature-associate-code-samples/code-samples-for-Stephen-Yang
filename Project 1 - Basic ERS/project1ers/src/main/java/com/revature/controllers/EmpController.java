package com.revature.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.BEDaoImp;
import com.revature.dao.BRDaoImp;
import com.revature.drivers.BasicDriver;
import com.revature.model.BasicEmployee;
import com.revature.model.BasicRequest;
import com.revature.service.BasicService;

/**
 * Servlet implementation class EmpController
 */
public class EmpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger ecLogger = Logger.getLogger(EmpController.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ecLogger.info("pressed Manager Home button");
		request.getRequestDispatcher("mhome.html").forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ecLogger.info("clicked Update button");
		HttpSession session = request.getSession(); 
		ecLogger.info("session belongs to this employee: "+session.getAttribute("user"));
		
		String urlPattern = request.getRequestURI();
		ecLogger.info(urlPattern);
		String[] inputStrs = request.getReader().readLine().split(" ");
		//ecLogger.info("ec doPost read this request body: "+inputStrs.toString());
		// update fields to values sent by client - ajax
		if (BasicService.getService().changeInfo(inputStrs[0], inputStrs[1], inputStrs[2])) {
			response.getWriter().write(inputStrs[2]); // using select is stronger guarantee
		} else {
			response.getWriter().write("Failed to update.");
		}
		
	}

}
