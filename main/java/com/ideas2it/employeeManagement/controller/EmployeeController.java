                                                                               ;
package com.ideas2it.employeeManagement.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.ideas2it.employeeManagement.service.EmployeeService;
import com.ideas2it.employeeManagement.service.impl.EmployeeServiceImpl;
import com.ideas2it.employeeManagementException.EmployeeManagementException;

/**
 * Servlet implementation class projectController
 * 
 * @version 1.1 21 April 2021
 * @author Sembiyan
 */
public class EmployeeController extends HttpServlet {
	
	private EmployeeService employeeService = new EmployeeServiceImpl();
      
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
		switch (request.getParameter("action")) {
			case "addEmployee":     
			    addEmployee(request, response);
		    	 break;
			case "updateEmployee":
			     updateEmployee(request, response);
		    	 break;
			case "addAddress":
	       	 	addNewAddress(request, response);
	       	 	break;
	        case "updateAddress":
	       	 	updateAddress(request, response);
	       	 	break;
	        case "showEmployee":
		    	 showEmployee(request, response);
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
     *     if an input or output error is detected when the servlet handles
     * the GET request
     * @throws IOException    
     *     if the request for the GET could not be handled
     */
	protected void doGet(HttpServletRequest request, 
				HttpServletResponse response) throws ServletException, IOException {
		 String action = request.getParameter("action");
		 switch (action) {
		     case "showAllEmployees":
	    	     showAllEmployees(request, response);
	    	     break;
		     case "createEmployee":
		    	 createNewEmployee(request, response);
		    	 break;
		     case "showEmployee":
		    	 showEmployee(request, response);
		    	 break;
		     case "deleteEmployee":
		    	 deleteEmployee(request, response);
		    	 break;
		     case "editEmployee":
		    	 editEmployee(request, response);
		    	 break;
		     case "assignProject":
	             assignAProject(request, response);
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
	 *  @throws ServletException    
     *     if an input or output error is detected when the servlet handles
     * the GET request
     * @throws IOException    
     *     if the request for the GET could not be handled
     */
    private void doGetExtention(String action, HttpServletRequest request,
    		HttpServletResponse response) throws ServletException, IOException {
    	switch (action) {
    	case "unassignProject":
            unnassignAProject(request, response);
            break;
        case "showAvailableProjects":
            showAvailableProjects(request, response);
            break;
        case "deleteAddress":
       	 	deleteExistingAddress(request, response);
       	 	break;
        case "editAddress":
       	 	editAddress(request, response);
       	 	break;
        case "createAddress":
       	 	createNewAddress(request, response);
       	 	break;
        case "index":
        	goHomePage(request, response);
        	break;
        case "viewDeletedEmployees":
        	showDeletedEmployees(request, response);
        	break;
        case "restoreEmployee":
	    	restoreEmployee(request, response);
	    	break;
	    default:
	    	showAllEmployees(request, response);
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
	 * Shows all available employees
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void showAllEmployees(HttpServletRequest request,
    	        HttpServletResponse response) {
    	try {
    		request.setAttribute("employees", employeeService.getAllEmployees());
    		RequestDispatcher requestDispatcher 
    				= request.getRequestDispatcher("employeeHome.jsp");
			requestDispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		}
    }
	
	/**
     * Displays the deleted employees
     * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     */
    private void showDeletedEmployees(HttpServletRequest request,
    		HttpServletResponse response) {
    	try {
    		request.setAttribute("employees",
    				employeeService.getDeletedEmployees());
    		RequestDispatcher requestDispatcher 
    	        	= request.getRequestDispatcher("deletedEmployees.jsp");
			requestDispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		}
	}

	/**
     * Restores a deleted employee
     * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     */
    private void restoreEmployee(HttpServletRequest request,
    		HttpServletResponse response) {
    	try {
    		employeeService.restoreEmployee(Integer.parseInt(request.getParameter("id")));
			response.sendRedirect("employee?action=showAllEmployees");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		}
		
	}

    /**
     * Deletes a existing address for the employee
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     */
	private void deleteExistingAddress(HttpServletRequest request,
		        HttpServletResponse response) {
		int employeeId = Integer.parseInt(request.getParameter("id"));
		try {
			employeeService.deleteExistingAddress(employeeId,
					Integer.parseInt(request.getParameter("option")));
			response.sendRedirect("employee?action=showEmployee&id=" 
					+ employeeId);
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Assigns a project to the employee
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
	private void assignAProject(HttpServletRequest request,
		        HttpServletResponse response) {
    	int projectId = Integer.parseInt(request.getParameter("projectId"));
    	int employeeId = Integer.parseInt(request.getParameter("employeeId"));
    	try {
    		employeeService.assignAProject(projectId, employeeId);
			response.sendRedirect("employee?action=showEmployee&id=" 
					+ employeeId);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		}
	}

	/**
	 * Gets the available projects which can be assigned for the employee
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
	private void showAvailableProjects(HttpServletRequest request,
		        HttpServletResponse response) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	try {
    		request.setAttribute("employee",
    				employeeService.retrieveEmployee(id));
    		request.setAttribute("availableProjects",
    				employeeService.getAvailableProjects(id));
    		RequestDispatcher requestDispatcher 
    				= request.getRequestDispatcher("viewEmployee.jsp");
			requestDispatcher.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (EmployeeManagementException e) {
			displayMessage(request, response,
					"Failed to get available projects");
		}
	}
	
	/**
	 * Unassigns a project for the employee
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
	private void unnassignAProject(HttpServletRequest request,
		        HttpServletResponse response) {
    	int projectId = Integer.parseInt(request.getParameter("projectId"));
    	int employeeId = Integer.parseInt(request.getParameter("employeeId"));
    	try {
    		employeeService.unassignAProject(projectId, employeeId);
			response.sendRedirect("employee?action=showEmployee&id=" 
					+ employeeId);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		}
	}
	
	/**
	 * Fetches the new address details of the employee
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
	private void createNewAddress(HttpServletRequest request,
		        HttpServletResponse response) {
		request.setAttribute("operation", "addAddress");
		request.setAttribute("id",
				Integer.parseInt(request.getParameter("id")));
		request.setAttribute("option", 1);
        RequestDispatcher requestDispatcher 
                = request.getRequestDispatcher("addressForm.jsp");
        try {
			requestDispatcher.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
    }

	/**
	 * Edits the existing address of the employee
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void editAddress(HttpServletRequest request,
    	        HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        int selectedOption = Integer.parseInt(request.getParameter("option"));
        try {
	        request.setAttribute("address",
	        		employeeService.retrieveEmployee(id).getAddresses().get(selectedOption));
	        request.setAttribute("id", id);
	        request.setAttribute("option", selectedOption);
	        request.setAttribute("operation", "updateAddress");
	        RequestDispatcher requestDispatcher 
	                = request.getRequestDispatcher("addressForm.jsp");
			requestDispatcher.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		}
    }
	
    /**
	 * Fetches a new employee details
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
	private void createNewEmployee(HttpServletRequest request,
		        HttpServletResponse response) {
		request.setAttribute("operation", "addEmployee");
		RequestDispatcher requestDispatcher 
		        = request.getRequestDispatcher("employeeForm.jsp");
    	try {
			requestDispatcher.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fetches a new set of employee's details
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
	private void editEmployee(HttpServletRequest request,
		        HttpServletResponse response) {
		try {
	    	request.setAttribute("operation", "updateEmployee");
	    	request.setAttribute("employee", 
	    			employeeService.retrieveEmployee(Integer.parseInt(request.getParameter("id"))));
	    	RequestDispatcher requestDispatcher 
	    	        = request.getRequestDispatcher("employeeForm.jsp");
				requestDispatcher.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		}
	}

	/**
	 * Inserts the new address obtained
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
	private void addNewAddress(HttpServletRequest request,
		        HttpServletResponse response) {
		List<String> address = new ArrayList<String>();
		int id = Integer.parseInt(request.getParameter("id"));
		address.add(request.getParameter("doorNumber"));
		address.add(request.getParameter("street"));
		address.add(request.getParameter("city"));
		address.add(request.getParameter("pincode"));
		address.add(request.getParameter("state"));
		address.add(request.getParameter("country"));
		address.add("secondary");
		try {
			employeeService.addNewAddress(id, address);
			response.sendRedirect("employee?action=showEmployee&id=" + id);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		}
	}
	
	/**
	 * Inserts the new employee created
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void addEmployee(HttpServletRequest request,
    	        HttpServletResponse response) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	try {
    		if (employeeService.checkEmployeeIdPresenceIncludingDeleted(id)) {
    			displayMessage(request, response, "Employee Id already present");
    		} else {
		    	List<String> address = new ArrayList<String>();
		    	address.add(request.getParameter("doorNumber"));
		    	address.add(request.getParameter("street"));
		    	address.add(request.getParameter("city"));
		    	address.add(request.getParameter("pincode"));
		    	address.add(request.getParameter("state"));
		    	address.add(request.getParameter("country"));
		    	address.add("permanent");
		     	employeeService.addEmployee(id, request.getParameter("name"), 
		     			Float.parseFloat(request.getParameter("salary")),
		     			request.getParameter("mobileNumber"),
		     			new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateOfBirth")),
		     			address);
		     	response.sendRedirect("employee?action=showAllEmployees");
    		}
     	} catch (IOException e) {
     		e.printStackTrace();
     	} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		}  catch (ParseException e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * Displays a employee's details
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void showEmployee(HttpServletRequest request,
    	        HttpServletResponse response) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	try {
    		if (employeeService.checkEmployeeIdPresence(id)) {
    			request.setAttribute("employee",
    					employeeService.retrieveEmployee(id));
    			RequestDispatcher requestDispatcher 
	    	        	= request.getRequestDispatcher("viewEmployee.jsp");
				requestDispatcher.forward(request, response);
    		} else {
    			displayMessage(request, response, "Employee Id not present");
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		}
    }
    
    /**
	 * Deletes a employee
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void deleteEmployee(HttpServletRequest request,
    	        HttpServletResponse response) {
    	try {
    		employeeService.deleteEmployee(Integer.parseInt(request.getParameter("id")));
			response.sendRedirect("employee?action=showAllEmployees");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		}
    }
    
    /**
	 * Updates a employee
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void updateEmployee(HttpServletRequest request,
    	        HttpServletResponse response) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	try {
    		employeeService.updateEmployee(id, request.getParameter("name"),
    				Float.parseFloat(request.getParameter("salary")),
    				request.getParameter("mobileNumber"),
    				new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateOfBirth")));
   			response.sendRedirect("employee?action=showEmployee&id=" + id);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * Updates a existing address
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void updateAddress(HttpServletRequest request,
    	        HttpServletResponse response) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	try {
    		employeeService.updateExistingAddress(id,
	    			Integer.parseInt(request.getParameter("option")), 
	    			request.getParameter("doorNumber"),
	    			request.getParameter("street"),
	    			request.getParameter("city"),
	    			request.getParameter("pincode"),
	    			request.getParameter("state"),
	    			request.getParameter("country"), "permanent");
			response.sendRedirect("employee?action=showEmployee&id=" + id);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EmployeeManagementException e) {
			displayMessage(request, response, e.getMessage());
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










