package com.ideas2it.employeeManagement.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.ideas2it.employeeManagement.model.Address;
import com.ideas2it.employeeManagement.model.Employee;
import com.ideas2it.employeeManagement.service.EmployeeService;
import com.ideas2it.employeeManagement.service.impl.EmployeeServiceImpl;
import com.ideas2it.projectManagement.model.Project;

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
     *     if an input or output error is detected when the servlet handles the POST request
     * @throws IOException    
     *     if the request for the POST could not be handled
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		switch (request.getParameter("action")) {
			case "insert":     
			    insertEmployee(request, response);
		    	 break;
			case "update":
			     updateEmployee(request, response);
		    	 break;
			case "insertAddress":
	       	 	insertNewAddress(request, response);
	       	 	break;
	        case "updateAddress":
	       	 	updateAddress(request, response);
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
	    	     showAllEmployees(request, response);
	    	     break;
		     case "new":
		    	 createNewEmployee(request, response);
		    	 break;
		     case "view":
		    	 viewEmployee(request, response);
		    	 break;
		     case "delete":
		    	 deleteEmployee(request, response);
		    	 break;
		     case "edit":
		    	 editEmployee(request, response);
		    	 break;
		     case "assign":
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
     *     if an input or output error is detected when the servlet handles the GET request
     * @throws IOException    
     *     if the request for the GET could not be handled
     */
    private void doGetExtention(String action, HttpServletRequest request,
    		HttpServletResponse response) throws ServletException, IOException {
    	switch (action) {
    	case "unassign":
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
        case "newAddress":
       	 	createNewAddress(request, response);
       	 	break;
        case "index":
        	goHomePage(request, response);
        	break;
        case "viewDeletedEmployees":
        	showDeletedEmployees(request, response);
        	break;
        case "restore":
	    	restoreEmployee(request, response);
	    	break;
	    default:
	    	showAllEmployees(request, response);
	    	break;
    	}
    }
	
	/**
     * Displays the deleted employees
     * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     */
    private void showDeletedEmployees(HttpServletRequest request, HttpServletResponse response) {
    	List<Employee> employees = employeeService.getDeletedEmployees();
    	request.setAttribute("employees", employees);
    	RequestDispatcher requestDispatcher 
    	        = request.getRequestDispatcher("deletedEmployees.jsp");
    	try {
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     * Restores a deleted employee
     * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     */
    private void restoreEmployee(HttpServletRequest request, HttpServletResponse response) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	employeeService.restoreEmployee(id);
    	showAllEmployees(request, response);
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
     * Deletes a existing address for the employee
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
     */
	private void deleteExistingAddress(HttpServletRequest request,
		        HttpServletResponse response) throws ServletException, IOException  {
		int employeeId = Integer.parseInt(request.getParameter("id"));
		int selectedOption = Integer.parseInt(request.getParameter("option"));
		employeeService.deleteExistingAddress(employeeId, selectedOption);
		try {
			response.sendRedirect("employee?action=view&id=" + employeeId);
		} catch (Exception e) {
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
		employeeService.assignAProject(projectId, employeeId);
		try {
			response.sendRedirect("employee?action=view&id=" + employeeId);
		} catch (Exception e) {
			e.printStackTrace();
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
		request.setAttribute("employee",
			    employeeService.retrieveEmployee(id));
		request.setAttribute("availableProjects",
			    employeeService.getAvailableProjects(id));
		RequestDispatcher requestDispatcher 
		        = request.getRequestDispatcher("viewEmployee.jsp");
        try {
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
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
		employeeService.unassignAProject(projectId, employeeId);
		try {
			response.sendRedirect("employee?action=view&id=" + employeeId);
		} catch (Exception e) {
			e.printStackTrace();
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
		int id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("idTo", id);
		request.setAttribute("employee", employeeService.retrieveEmployee(id));
        RequestDispatcher requestDispatcher 
                = request.getRequestDispatcher("viewEmployee.jsp");
        try {
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
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
        Address address = employeeService.retrieveEmployee(id)
                .getAddresses().get(selectedOption);
        request.setAttribute("address", address);
        request.setAttribute("id", id);
        request.setAttribute("option", selectedOption);
        RequestDispatcher requestDispatcher 
                = request.getRequestDispatcher("addressForm.jsp");
        try {
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
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
		RequestDispatcher requestDispatcher 
		        = request.getRequestDispatcher("employeeForm.jsp");
    	try {
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
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
		int id = Integer.parseInt(request.getParameter("id"));
    	Employee employee = employeeService.retrieveEmployee(id);
    	request.setAttribute("employee", employee);
    	RequestDispatcher requestDispatcher 
    	        = request.getRequestDispatcher("employeeForm.jsp");
    	try {
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserts the new address obtained
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
	private void insertNewAddress(HttpServletRequest request,
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
		employeeService.addNewAddress(id, address);
		try {
			response.sendRedirect("employee?action=view&id=" + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inserts the new employee created
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void insertEmployee(HttpServletRequest request,
    	        HttpServletResponse response) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	 if (employeeService.checkEmployeeIdPresenceIncludingDeleted(id)) {
         	request.setAttribute("usedEmployeeId", id);
             RequestDispatcher requestDispatcher 
                     = request.getRequestDispatcher("errorMessage.jsp");
             try {
     			requestDispatcher.forward(request, response);
     		} catch (Exception e) {
     			e.printStackTrace();
     		}
     	} else {
	    	String name = request.getParameter("name");
	    	float salary =Float.parseFloat(request.getParameter("salary"));
	    	String mobileNumber = request.getParameter("mobileNumber");
	    	try {
	    	    Date dateOfBirth;
				dateOfBirth = new SimpleDateFormat("yyyy-MM-dd")
				        .parse(request.getParameter("dateOfBirth"));
		    	List<List<String>> addresses = new ArrayList<List<String>>();
		    	List<String> address = new ArrayList<String>();
		    	address.add(request.getParameter("doorNumber"));
		    	address.add(request.getParameter("street"));
		    	address.add(request.getParameter("city"));
		    	address.add(request.getParameter("pincode"));
		    	address.add(request.getParameter("state"));
		    	address.add(request.getParameter("country"));
		    	address.add("permanent");
		    	addresses.add(address);
		    	List<String> optionalAddress = new ArrayList<String>();
		    	optionalAddress.add(request.getParameter("doorNumberO"));
		    	optionalAddress.add(request.getParameter("streetO"));
		    	optionalAddress.add(request.getParameter("cityO"));
		    	optionalAddress.add(request.getParameter("pincodeO"));
		    	optionalAddress.add(request.getParameter("stateO"));
		    	optionalAddress.add(request.getParameter("countryO"));
		    	optionalAddress.add("secondary");
		    	addresses.add(optionalAddress);
		     	employeeService.addEmployee(id, name, salary, mobileNumber,
		     		    dateOfBirth, addresses);
		     	response.sendRedirect("employee?action=showAll");
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
     	}
    }
    
    /**
	 * Displays a employee's details
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void viewEmployee(HttpServletRequest request,
    	        HttpServletResponse response) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	if (employeeService.checkEmployeeIdPresence(id)) {
    		Employee employee = employeeService.retrieveEmployee(id);
	    	request.setAttribute("employee", employee);
	    	RequestDispatcher requestDispatcher 
	    	        = request.getRequestDispatcher("viewEmployee.jsp");
	    	try {
				requestDispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	} else {
			request.setAttribute("invalidEmployeeId", id);
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
	 * Deletes a employee
	 * @param request
     *     object that contains the request the client has made of the servlet
     * @param response    
     *     object that contains the response the servlet sends to the client
	 */
    private void deleteEmployee(HttpServletRequest request,
    	        HttpServletResponse response) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	employeeService.deleteEmployee(id);
    	try {
			response.sendRedirect("employee?action=showAll");
		} catch (IOException e) {
			e.printStackTrace();
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
    	String name = request.getParameter("name");
    	float salary =Float.parseFloat(request.getParameter("salary"));
    	String mobileNumber = request.getParameter("mobileNumber");
    	try {
    		Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd")
    		        .parse(request.getParameter("dateOfBirth"));
    		employeeService.updateEmployee(id, name, salary,
    			    mobileNumber, dateOfBirth);
   			response.sendRedirect("employee?action=view&id=" + id);
		} catch (Exception e) {
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
    	int selectedOption = Integer.parseInt(request.getParameter("option"));
    	String doorNumber = request.getParameter("doorNumber");
    	String street = request.getParameter("street");
    	String city = request.getParameter("city");
    	String pincode = request.getParameter("pincode");
    	String state = request.getParameter("state");
    	String country = request.getParameter("country");
    	String type = "permanent";
    	employeeService.updateExistingAddress(id, selectedOption, doorNumber,
    		    street, city, pincode, state, country, type);
    	try {
			response.sendRedirect("employee?action=view&id=" + id);
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
    	List<Employee> employees = employeeService.getAllEmployees();
    	request.setAttribute("employees", employees);
    	RequestDispatcher requestDispatcher 
    	        = request.getRequestDispatcher("employeeHome.jsp");
    	try {
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}