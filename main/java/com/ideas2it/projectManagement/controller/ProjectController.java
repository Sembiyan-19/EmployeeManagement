package com.ideas2it.projectManagement.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ideas2it.projectManagement.service.impl.ProjectServiceImpl;
import com.ideas2it.projectManagement.service.ProjectService;

/**
 * Servlet implementation class projectController
 */
public class ProjectController extends HttpServlet {
	
	private ProjectService projectService = new ProjectServiceImpl();
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String action = request.getParameter("action");
		 System.out.println("hi........."+action);
		 switch (action) {
		     case "add":
		    	 addProject(request, response);
		    	 break;
		     case "/retrieve":
		    	 break;
		     case "/update":
		    	 break;
		     case "/delete":
		    	 break;
		    default:
		    	break;
		 }
	}
	
    public void addProject(HttpServletRequest request, HttpServletResponse response) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	String name = request.getParameter("name");
    	String manager = request.getParameter("manager");
    	String department = request.getParameter("department");
    	projectService.addProject(id, name, manager, department);
    }
}
