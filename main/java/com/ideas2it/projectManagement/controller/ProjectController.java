//                                                                             ;
package com.ideas2it.projectManagement.controller;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.ideas2it.projectManagement.service.impl.ProjectServiceImpl;
import com.ideas2it.employeeManagementException.EmployeeManagementException;
import com.ideas2it.projectManagement.service.ProjectService;

/**
 * Servlet implementation class that act as Controller
 * 
 * @version 1.1 21 April 2021
 * @author Sembiyan
 */
public class ProjectController extends HttpServlet {
    
    private ProjectService projectService = new ProjectServiceImpl();
       
	/**
     * 
     * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     * @throws ServletException    
     *     if an input or output error is detected when the servlet handles
     * the POST request
     * @throws IOException    
     *     if the request for the POST could not be handled
     */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		switch(request.getParameter("action")) {
		case "addProject":
            addProject(request, response);
            break;
		case "updateProject":
            updateProject(request, response);
            break;
		case "showProject":
            showProject(request, response);
            break;
		}
	}

	/**
     * 
     * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     * @throws ServletException    
     *     if an input or output error is detected when the servlet handles the GET request
     * @throws IOException    
     *     if the request for the GET could not be handled
     */
    protected void doGet(HttpServletRequest request, 
    	HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
         	case "showAllProjects":
                showAllProjects(request, response);
                break;
            case "createProject":
                createNewProject(request, response);
                break;
            case "showProject":
                showProject(request, response);
                break;
            case "index":
            	goHomePage(request, response);
            	break;
            case "deleteProject":
	        	deleteProject(request, response);
	        	break;
            default:
            	doGetExtention(action, request, response);
    	        break;
         }
    }
    
    /**
     * Extension the switch case in doGet method
     * @action        action chosen by the user
     * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     */
    private void doGetExtention(String action, HttpServletRequest request,
    		HttpServletResponse response) {
    	switch (action) {
	        case "editProject":
		        editProject(request, response);
		        break;
		    case "assignEmployee":
		        assignAEmployee(request, response);
		        break;
		    case "unassignEmployee":
		        unnassignAEmployee(request, response);
		        break;
		    case "showAvailableEmployees":
		    	showAvailableEmployees(request, response);
		        break;
		    case "showDeletedProjects":
	        	showDeletedProjects(request, response);
	        	break;
		    case "restoreProject":
		    	restoreProject(request, response);
		    	break;
		    default:
		        showAllProjects(request, response);
		        break;
    	}
	}
    
    /**
     * Redirects to the index page
     * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     */
	private void goHomePage(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.sendRedirect("index.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    /**
	 * Shows all available projecs
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void showAllProjects(HttpServletRequest request,
            HttpServletResponse response) {
    	try {
    		request.setAttribute("projects", projectService.getAllProjects());
    		RequestDispatcher requestDispatcher 
    				= request.getRequestDispatcher("projectHome.jsp");
			requestDispatcher.forward(request, response);
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		} catch (ServletException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}
		
    }

	/**
	 * Fetches a new project details
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
	private void createNewProject(HttpServletRequest request,
            HttpServletResponse response) {
		request.setAttribute("operation", "addProject");
        RequestDispatcher requestDispatcher 
                = request.getRequestDispatcher("projectForm.jsp");
        try {
			requestDispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
    }

	/**
	 * Inserts the new project created
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void addProject(HttpServletRequest request,
            HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
			if (projectService.checkProjectIdPresenceWithDeleted(id)) {
				displayMessage(request, response, "project Id already present");
			} else {
			    try {
					projectService.addProject(id, request.getParameter("name"),
							request.getParameter("manager"), 
							request.getParameter("department"));
					response.sendRedirect("project?action=showAllProjects");
				} catch (EmployeeManagementException e) {
					displayMessage(request, response, e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (EmployeeManagementException e) {
			e.printStackTrace();
		}
    }
	
	/**
     * Displays the deleted projectss
     * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     */
    private void showDeletedProjects(HttpServletRequest request,
    		HttpServletResponse response) {
    	try {
    		request.setAttribute("projects",
    				projectService.getDeletedProjects());
    		RequestDispatcher requestDispatcher 
    				= request.getRequestDispatcher("deletedProjects.jsp");
			requestDispatcher.forward(request, response);
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	/**
     * Restores a deleted project
     * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     */
    private void restoreProject(HttpServletRequest request,
    		HttpServletResponse response) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	try {
    		projectService.restoreProject(id);
			response.sendRedirect("project?action=showAllProjects");
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}    

    /**
	 * Assigns a employee for the project
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
	private void assignAEmployee(HttpServletRequest request,
            HttpServletResponse response) {
    	int projectId = Integer.parseInt(request.getParameter("projectId"));
    	try {
    		projectService.assignAEmployee(projectId,
    				Integer.parseInt(request.getParameter("employeeId")));
			response.sendRedirect("project?action=showProject&id=" 
					+ projectId);
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the available employees who can be assigned for the project
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
	private void showAvailableEmployees(HttpServletRequest request,
            HttpServletResponse response) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	try {
    		request.setAttribute("project", 
    				projectService.retrieveProject(id));
    		request.setAttribute("availableEmployees", 
    				projectService.getAvailableEmployees(id));
    		RequestDispatcher requestDispatcher 
                	= request.getRequestDispatcher("viewProject.jsp");
			requestDispatcher.forward(request, response);
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Unassigns a employee from the project
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
	private void unnassignAEmployee(HttpServletRequest request,
            HttpServletResponse response) {
    	int projectId = Integer.parseInt(request.getParameter("projectId"));
    	try {
			projectService.unassignAEmployee(projectId,
					Integer.parseInt(request.getParameter("employeeId")));
			response.sendRedirect("project?action=showProject&id=" + projectId);
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fetches a new set of project's details
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void editProject(HttpServletRequest request,
            HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("operation", "updateProject");
        try {
        request.setAttribute("project", projectService.retrieveProject(id));
        RequestDispatcher requestDispatcher 
                = request.getRequestDispatcher("projectForm.jsp");
			requestDispatcher.forward(request, response);
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
 
    /**
	 * Displays a project's details
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void showProject(HttpServletRequest request,
            HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
			if (projectService.checkProjectIdPresence(id)) {
			    request.setAttribute("project", 
			    	projectService.retrieveProject(id));
			    RequestDispatcher requestDispatcher 
			            = request.getRequestDispatcher("viewProject.jsp");
				requestDispatcher.forward(request, response);
			} else {
				displayMessage(request, response, "project Id not present");
			}
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * Deletes a project
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void deleteProject(HttpServletRequest request,
            HttpServletResponse response) {
    	try {
        projectService.deleteProject(Integer.parseInt(request.getParameter("id")));
			response.sendRedirect("project?action=showAllProjects");
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * Updates a project
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void updateProject(HttpServletRequest request,
            HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
        	projectService.updateProject(id, request.getParameter("name"), 
        			request.getParameter("manager"),
        			request.getParameter("department"));
			response.sendRedirect("project?action=showProject&id="+id);
		} catch (EmployeeManagementException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * Redircts to a page and displays a given message
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     * @param message        message which has to be displayed
	 */
    private void displayMessage(HttpServletRequest request, 
    		HttpServletResponse response, String message) {
		request.setAttribute("message", message);
		RequestDispatcher requestDispatcher 
				= request.getRequestDispatcher("displayMessage.jsp");
		try {
			requestDispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}