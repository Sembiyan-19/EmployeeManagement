//                                                                             ;
package com.ideas2it.projectManagement.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.ideas2it.employeeManagement.model.Employee;
import com.ideas2it.projectManagement.model.Project;
import com.ideas2it.projectManagement.service.impl.ProjectServiceImpl;
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
     *     if an input or output error is detected when the servlet handles the POST request
     * @throws IOException    
     *     if the request for the POST could not be handled
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		switch(request.getParameter("action")) {
		case "insert":
            insertProject(request, response);
            break;
		case "update":
            updateProject(request, response);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
         	case "showAll":
                showAllProjects(request, response);
                break;
            case "new":
                createNewProject(request, response);
                break;
            case "view":
                viewProject(request, response);
                break;
            case "index":
            	goHomePage(request, response);
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
	        case "delete":
	        	deleteProject(request, response);
	        	break;
	        case "edit":
		        editProject(request, response);
		        break;
		    case "assign":
		        assignAEmployee(request, response);
		        break;
		    case "unassign":
		        unnassignAEmployee(request, response);
		        break;
		    case "showAvailableEmployees":
		    	showAvailableEmployees(request, response);
		        break;
		    case "viewDeletedProjects":
	        	showDeletedProjects(request, response);
	        	break;
		    case "restore":
		    	restoreProject(request, response);
		    	break;
		    default:
		        showAllProjects(request, response);
		        break;
    	}
	}
    
	/**
     * Displays the deleted projectss
     * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     */
    private void showDeletedProjects(HttpServletRequest request, HttpServletResponse response) {
    	List<Project> projects = projectService.getDeletedProjects();
    	request.setAttribute("projects", projects);
    	RequestDispatcher requestDispatcher 
    	        = request.getRequestDispatcher("deletedProjects.jsp");
    	try {
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
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
    private void restoreProject(HttpServletRequest request, HttpServletResponse response) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	projectService.restoreProject(id);
    	showAllProjects(request, response);
	}
    
    /**
     * Redirects to the index page
     * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     */
	private void goHomePage(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect("index.jsp");
		} catch (Exception e) {
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
    	int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		projectService.assignAEmployee(projectId, employeeId);
		try {
			response.sendRedirect("project?action=view&id=" + projectId);
		} catch (Exception e) {
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
		request.setAttribute("project", 
                projectService.retrieveProject(id));
		request.setAttribute("availableEmployees", 
                projectService.getAvailableEmployees(id));
		RequestDispatcher requestDispatcher 
                = request.getRequestDispatcher("viewProject.jsp");
        try {
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
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
    	int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		projectService.unassignAEmployee(projectId, employeeId);
		try {
			response.sendRedirect("project?action=view&id=" + projectId);
		} catch (Exception e) {
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
        RequestDispatcher requestDispatcher 
                = request.getRequestDispatcher("projectForm.jsp");
        try {
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
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
        Project project = projectService.retrieveProject(id);
        request.setAttribute("project", project);
        RequestDispatcher requestDispatcher 
                = request.getRequestDispatcher("projectForm.jsp");
        try {
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
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
    private void insertProject(HttpServletRequest request,
            HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        if (projectService.checkProjectIdPresenceWithDeleted(id)) {
        	request.setAttribute("usedProjectId", id);
            RequestDispatcher requestDispatcher 
                    = request.getRequestDispatcher("errorMessage.jsp");
            try {
    			requestDispatcher.forward(request, response);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	} else {
	        String name = request.getParameter("name");
	        String manager = request.getParameter("manager");
	        String department = request.getParameter("department");
	        projectService.addProject(id, name, manager, department);
	        showAllProjects(request, response);
	        /*try {
				response.sendRedirect("project?action=showAll");
			} catch (Exception e) {
				e.printStackTrace();
			}*/
        }
    }
    
    /**
	 * Displays a project's details
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void viewProject(HttpServletRequest request,
            HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        if (projectService.checkProjectIdPresence(id)) {
	        Project project = projectService.retrieveProject(id);
	        request.setAttribute("project", project);
	        RequestDispatcher requestDispatcher 
	                = request.getRequestDispatcher("viewProject.jsp");
	        try {
				requestDispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
        } else {
        	request.setAttribute("invalidProjectId", id);
	        RequestDispatcher requestDispatcher 
	                = request.getRequestDispatcher("errorMessage.jsp");
	        try {
				requestDispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
        int id = Integer.parseInt(request.getParameter("id"));
        projectService.deleteProject(id);
        showAllProjects(request, response);
        /*try {
			response.sendRedirect("project?action=showAll");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
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
        String name = request.getParameter("name");
        String manager = request.getParameter("manager");
        String department = request.getParameter("department");
        projectService.updateProject(id, name, manager, department);
        try {
			response.sendRedirect("project?action=view&id="+id);
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
        List<Project> projects = projectService.getAllProjects();
        request.setAttribute("projects", projects);
        RequestDispatcher requestDispatcher 
            = request.getRequestDispatcher("projectHome.jsp");
        try {
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}