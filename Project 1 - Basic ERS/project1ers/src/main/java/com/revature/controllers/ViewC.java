package com.revature.controllers;

import java.io.IOException;
import java.util.ArrayList;
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
import com.revature.model.BasicEmployee;
import com.revature.model.BasicRequest;

/**
 * Servlet implementation class ViewC
 */
public class ViewC extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger vcLogger = Logger.getLogger(ViewC.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		vcLogger.info("session belongs to "+session.getAttribute("user"));
		
		List<BasicEmployee> eList = BEDaoImp.getBEDao().selectAllEmps();
		vcLogger.info("got all employees: "+eList);
		ObjectMapper mapper = new ObjectMapper();
		response.setHeader("Content-Type", "application/json");
		mapper.writeValue(response.getOutputStream(), eList); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		vcLogger.info("session belongs to "+session.getAttribute("user"));
		
		String sentStr = request.getReader().readLine();
		vcLogger.info("clicked View "+sentStr+" Requests");
		List<BasicRequest> rList = new ArrayList<>();
		if (sentStr.startsWith("all")) {
			String status = sentStr.substring(4);
			rList = BRDaoImp.getBRDao().selectReqs(status, "all");
			vcLogger.info("all "+status+" requests: "+rList);
		} else if (sentStr.matches("pending") || sentStr.matches("resolved")) {
			rList = BRDaoImp.getBRDao().selectReqs(sentStr, session.getAttribute("user").toString());
			vcLogger.info("employee's "+sentStr+" requests: "+rList);
		} else {
			rList = BRDaoImp.getBRDao().selectReqs("all", sentStr);
			vcLogger.info(sentStr+"'s requests: "+rList);	
		}
		
		ObjectMapper mapper = new ObjectMapper(); // create an object mapper (Jackson)
		response.setHeader("Content-Type", "application/json");
		mapper.writeValue(response.getOutputStream(), rList); // marshal bean and output
	}

}
