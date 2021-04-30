package com.ideas2it.employeeManagement.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ideas2it.employeeManagement.service.impl.EmployeeServiceImpl;
import com.ideas2it.projectManagement.model.Project;
import com.ideas2it.employeeManagement.model.Address;
import com.ideas2it.employeeManagement.model.Employee;
import com.ideas2it.employeeManagement.service.EmployeeService;

/**
 * Servlet implementation class projectController
 */
public class EmployeeController extends HttpServlet {
	
	private EmployeeService employeeService = new EmployeeServiceImpl();
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
		 switch (action) {
		     case "showAll":
	    	     viewAllEmployees(request, response);
	    	     break;
		     case "new":
		    	 createNewEmployee(request, response);
		    	 break;
		     case "insert":
			     try {
				     insertEmployee(request, response);
			     } catch (IOException | ParseException e) {
				     e.printStackTrace();
			     }
		    	 break;
		     case "view":
		    	 viewEmployee(request, response);
		    	 break;
		     case "update":
			try {
				updateEmployee(request, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	 break;
		     case "delete":
		    	 deleteEmployee(request, response);
		    	 break;
		     case "edit":
		    	 showEditForm(request, response);
		    	 break;
		     case "assign":
	             assignAProject(request, response);
	             break;
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
	        	 showAddressForm(request, response);
	        	 break;
	         case "newAddress":
	        	 createNewAddress(request, response);
	        	 break;
	         case "insertAddress":
	        	 insertNewAddress(request, response);
	        	 break;
	         case "updateAddress":
	        	 updateAddress(request, response);
	        	 break;
		    default:
		    	viewAllEmployees(request, response);
		    	break;
		 }
	}
	
    private void deleteExistingAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int employeeId = Integer.parseInt(request.getParameter("id"));
		int selectedOption = Integer.parseInt(request.getParameter("option"));
		employeeService.deleteExistingAddress(employeeId, selectedOption);
		response.sendRedirect("employee?action=view&id=" + employeeId);
	}

	private void assignAProject(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	int projectId = Integer.parseInt(request.getParameter("projectId"));
    	int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		employeeService.assignAProject(projectId, employeeId);
		response.sendRedirect("employee?action=view&id=" + employeeId);
	}

	private void showAvailableProjects(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("obj", employeeService.retrieveEmployee(id));
		request.setAttribute("availableProjects", employeeService.getAvailableProjects(id));
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewEmployee.jsp");
        requestDispatcher.forward(request, response);
	}
	
	private void unnassignAProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int projectId = Integer.parseInt(request.getParameter("projectId"));
    	int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		employeeService.unassignAProject(projectId, employeeId);
		//Project project = projectService.retrieveProject(projectId);
		//request.setAttribute("obj", project);
    	//RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewProject.jsp");
        //requestDispatcher.forward(request, response);
		response.sendRedirect("employee?action=view&id=" + employeeId);
	}
	
	private void createNewAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("idTo", id);
		request.setAttribute("obj", employeeService.retrieveEmployee(id));
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewEmployee.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showAddressForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int selectedOption = Integer.parseInt(request.getParameter("option"));
        Address address = employeeService.retrieveEmployee(id).getAddresses().get(selectedOption);
        request.setAttribute("obj", address);
        request.setAttribute("id", id);
        request.setAttribute("option", selectedOption);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("editAddress.jsp");
        requestDispatcher.forward(request, response);
    }
	
	private void createNewEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("editEmployee.jsp");
    	requestDispatcher.forward(request, response);
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	int id = Integer.parseInt(request.getParameter("id"));
    	Employee employee = employeeService.retrieveEmployee(id);
    	request.setAttribute("obj", employee);
    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("editEmployee.jsp");
    	requestDispatcher.forward(request, response);
	}

	private void insertNewAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
		response.sendRedirect("employee?action=view&id=" + id);
	}
	
    public void insertEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
    	int id = Integer.parseInt(request.getParameter("id"));
    	String name = request.getParameter("name");
    	float salary =Float.parseFloat(request.getParameter("salary"));
    	String mobileNumber = request.getParameter("mobileNumber");
    	System.out.println(request.getParameter("dateOfBirth")+"-----------------------");
    	Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateOfBirth"));
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
     	employeeService.addEmployee(id, name, salary, mobileNumber, dateOfBirth, addresses);
     	response.sendRedirect("employee?action=showAll");
    }
    
    public void viewEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	int id = Integer.parseInt(request.getParameter("id"));
    	Employee employee = employeeService.retrieveEmployee(id);
    	request.setAttribute("obj", employee);
    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewEmployee.jsp");
    	requestDispatcher.forward(request, response);
    }
    
    
    public void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	int id = Integer.parseInt(request.getParameter("id"));
    	employeeService.deleteEmployee(id);
    	response.sendRedirect("employee?action=showAll");
    }
    
    public void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
    	int id = Integer.parseInt(request.getParameter("id"));
    	String name = request.getParameter("name");
    	float salary =Float.parseFloat(request.getParameter("salary"));
    	String mobileNumber = request.getParameter("mobileNumber");
    	Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateOfBirth"));
    	employeeService.updateEmployee(id, name, salary, mobileNumber, dateOfBirth);
    	response.sendRedirect("employee?action=view&id=" + id);
    }
    
    private void updateAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	int id = Integer.parseInt(request.getParameter("id"));
    	int selectedOption = Integer.parseInt(request.getParameter("option"));
    	String doorNumber = request.getParameter("doorNumber");
    	String street = request.getParameter("street");
    	String city = request.getParameter("city");
    	String pincode = request.getParameter("pincode");
    	String state = request.getParameter("state");
    	String country = request.getParameter("country");
    	String type = "permanent";
    	employeeService.updateExistingAddress(id, selectedOption, doorNumber, street, city, pincode, state, country, type);
    	response.sendRedirect("employee?action=view&id=" + id);
    }
    
    public void viewAllEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	List<Employee> employees = employeeService.getAll();
    	request.setAttribute("obj", employees);
    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("employeeHome.jsp");
    	requestDispatcher.forward(request, response);
    }
    
    
}
