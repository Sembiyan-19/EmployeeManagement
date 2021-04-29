//                                                                             ;
package com.ideas2it.projectManagement.controller;

import java.io.IOException;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ideas2it.projectManagement.model.Project;
import com.ideas2it.projectManagement.service.impl.ProjectServiceImpl;
import com.ideas2it.projectManagement.service.ProjectService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

/**
 * Servlet implementation class that act as Controller
 */
public class ProjectController extends HttpServlet {
    
    private ProjectService projectService = new ProjectServiceImpl();
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
    	    HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, 
    	    HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
         	case "showAll":
                showAllProjects(request, response);
                break;
            case "new":
                createNewProject(request, response);
                break;
            case "insert":
                insertProject(request, response);
                break;
            case "view":
                viewProject(request, response);
                break;
            case "update":
                updateProject(request, response);
                 break;
            case "delete":
                deleteProject(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            default:
                showAllProjects(request, response);
                break;
         }
    }
    
    private void createNewProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("editProject.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Project project = projectService.retrieveProject(id);
        request.setAttribute("obj", project);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("editProject.jsp");
        requestDispatcher.forward(request, response);
    }

    public void insertProject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String manager = request.getParameter("manager");
        String department = request.getParameter("department");
        projectService.addProject(id, name, manager, department);
        response.sendRedirect("project?action=showAll");
    }
    
    public void viewProject(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Project project = projectService.retrieveProject(id);
        request.setAttribute("obj", project);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewProject.jsp");
        requestDispatcher.forward(request, response);
    }
    
    public void deleteProject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        projectService.deleteProject(id);
        response.sendRedirect("project?action=showAll");
    }
    
    public void updateProject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String manager = request.getParameter("manager");
        String department = request.getParameter("department");
        System.out.println(name);
        System.out.println(manager);
        System.out.println(department);
        projectService.updateProject(id, name, manager, department);
        response.sendRedirect("project?action=showAll");
    }
    
    public void showAllProjects(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Project> projects = projectService.getAll();
        request.setAttribute("obj", projects);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("projectHome.jsp");
        requestDispatcher.forward(request, response);
    }
}
