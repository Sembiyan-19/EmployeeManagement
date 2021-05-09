//                                                                             ;
package com.ideas2it.employeeManagement.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.ideas2it.employeeManagement.dao.EmployeeDao;
import com.ideas2it.employeeManagement.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeeManagement.model.Address;
import com.ideas2it.employeeManagement.model.Employee;
import com.ideas2it.employeeManagement.service.EmployeeService;
import com.ideas2it.employeeManagementException.EmployeeManagementException;
import com.ideas2it.projectManagement.model.Project;
import com.ideas2it.projectManagement.service.impl.ProjectServiceImpl;
import com.ideas2it.projectManagement.service.ProjectService;

/**
 * Class implementing service interface
 *
 * @version 1.1 21 April 2021
 * @author Sembiyan
 */
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao = new EmployeeDaoImpl(); 

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEmployee(int id, String name, float salary,
            String mobileNumber, Date dateOfBirth, List<String> addressDetails) 
            throws EmployeeManagementException {
        List<Address> addresses = new ArrayList<Address>();
        addresses.add(new Address(addressDetails.get(0),
                addressDetails.get(1), addressDetails.get(2),
                addressDetails.get(3), addressDetails.get(4),
                addressDetails.get(5), addressDetails.get(6), false));
        boolean isAdded = employeeDao.insertEmployee(new Employee(id, name, 
                salary, mobileNumber, dateOfBirth, false, addresses));
        return isAdded;
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public Employee retrieveEmployee(int id) 
            throws EmployeeManagementException {
    	Employee employee = employeeDao.retrieveEmployee(id);
    	List<Address> addresses = new ArrayList<Address>();
    	for (Address address : employee.getAddresses()) {
    		if (false == address.getIsDeleted()) {
    			addresses.add(address);
    		}
    	}
    	employee.setAddresses(addresses);
		return employee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteEmployee(int id) throws EmployeeManagementException {
    	try {
	        Employee employee = employeeDao.retrieveEmployee(id);
	        employee.setIsDeleted(true);
			employee.setProjects(null);
	        return employeeDao.updateEmployee(employee);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to delete employee");
    	}
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean restoreEmployee(int id) throws EmployeeManagementException {
    	try {
	        Employee employee = employeeDao.retrieveEmployee(id);
	        employee.setIsDeleted(false);
	        return employeeDao.updateEmployee(employee);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to restore employee");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateEmployee(int id, String name, 
            float salary, String mobileNumber, Date dateOfBirth) 
            throws EmployeeManagementException {
    	try {
	        Employee employee = employeeDao.retrieveEmployee(id);
	        employee.setName(name);
	        employee.setSalary(salary);
	        employee.setMobileNumber(mobileNumber);
	        employee.setDateOfBirth(dateOfBirth);
	        return employeeDao.updateEmployee(employee);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to update employee");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addNewAddress(int id, List<String> addressDetails) 
            throws EmployeeManagementException {
    	try {
	        Employee employee = employeeDao.retrieveEmployee(id);
	        List<Address> listOfAddress = employee.getAddresses();
	        listOfAddress.add(new Address(addressDetails.get(0),
	                addressDetails.get(1), addressDetails.get(2), 
	                addressDetails.get(3), addressDetails.get(4),
	                addressDetails.get(5), addressDetails.get(6), false));
			employee.setAddresses(listOfAddress);
	        return employeeDao.updateEmployee(employee);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to add address");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteExistingAddress(int id, int selectedAddressOption) 
            throws EmployeeManagementException {
    	try {
	        Employee employee = employeeDao.retrieveEmployee(id);
			int idToBeDeleted 
			        = retrieveEmployee(id).getAddresses()
			        .get(selectedAddressOption).getId();
			for (Address address : employee.getAddresses()) {
				if (idToBeDeleted == address.getId()) {
					address.setIsDeleted(true);
				}
			}
			return employeeDao.updateEmployee(employee);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to delete address");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateExistingAddress(int id, int selectedAddressOption, 
            String doorNumber, String street, String city, String pincode,
            String state, String country, String addressType) 
            throws EmployeeManagementException {
    	try {
			Employee employee = employeeDao.retrieveEmployee(id);
			int idToBeUpdated
					= retrieveEmployee(id).getAddresses()
					.get(selectedAddressOption).getId();
			for (Address address : employee.getAddresses()) {
				if (idToBeUpdated == address.getId()) {
					address.setDoorNumber(doorNumber);
					address.setStreet(street);
					address.setCity(city);
	            	address.setPincode(pincode);
	            	address.setState(state);
	            	address.setCountry(country);
				}
			}
	        return employeeDao.updateEmployee(employee);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to update address");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignAProject(int projectId, int employeeId) 
            throws EmployeeManagementException {
        ProjectService projectService = new ProjectServiceImpl();
        try {
	        Employee employee = employeeDao.retrieveEmployee(employeeId);
	        List<Project> projects = employee.getProjects();
			projects.add(projectService.retrieveProject(projectId));
	        employee.setProjects(projects);
	        employeeDao.updateEmployee(employee);
        } catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to assign project for employee");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unassignAProject(int projectId, int employeeId) 
            throws EmployeeManagementException {
    	try {
	        Employee employee = employeeDao.retrieveEmployee(employeeId);
	        List<Project> projects = employee.getProjects();
	        int indexOfProject = 0;
	        int count=0;
	        for (Project project : projects) {
	            count++;
	            if (projectId == project.getId()) {
	                indexOfProject = count - 1;
	            }
	        }
	        projects.remove(indexOfProject);
	        employee.setProjects(projects);
	        employeeDao.updateEmployee(employee);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to unassign project for employee");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getAvailableProjects(int employeeId) 
            throws EmployeeManagementException {
        boolean isPresent;
        List<Project> availableProjects = new ArrayList<Project>();
        ProjectService projectService = new ProjectServiceImpl();
        try {
        	Employee employee = employeeDao.retrieveEmployee(employeeId);
			for (Project project : projectService.getAllProjects()) {
			    isPresent = false;
			    for (Project projectsInEmployee : employee.getProjects()) {
			        if (project.getId() == projectsInEmployee.getId()) {
			            isPresent = true;
			        }
			    }
			    if (false == isPresent) {
			        availableProjects.add(project);
			    }
			}
			return availableProjects;
        } catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to get available projects");
    	}
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Employee> getAllEmployees() 
            throws EmployeeManagementException {
		return employeeDao.getAllEmployees(false);
	}
	
    /**
     * {@inheritDoc}
     */
	@Override
	public List<Employee> getDeletedEmployees() 
            throws EmployeeManagementException {
		try {
			return employeeDao.getAllEmployees(true);
		} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to get deleted employees");
    	}
	}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkEmployeeIdPresence(int id) 
            throws EmployeeManagementException {
        boolean isPresent = false;
        try {
	        Employee employee = employeeDao.retrieveEmployee(id);
	        if (null != employee) {
	            if (false == employee.getIsDeleted()) {
	                isPresent = true;
	            }
	        }
	        return isPresent;
	    } catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to retrieve employee");
    	}
    }

    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */
    @Override
    public boolean checkEmployeeIdPresenceIncludingDeleted(int id) 
            throws EmployeeManagementException {
        boolean isPresent = false;
        try {
	        if (null != employeeDao.retrieveEmployee(id)) {
	            isPresent = true;
	        }
	        return isPresent;
        } catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to add employee");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateMobileNumber(String mobileNumber) {
        return (mobileNumber.matches("[6789]{1}[0-9]{9}")) ? true : false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getDateOfBirth(String dateOfBirth) 
            throws EmployeeManagementException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        dateFormat.setLenient(false);
        try {
			date = dateFormat.parse(dateOfBirth);
		} catch (ParseException e) {
			throw new EmployeeManagementException("Invalid date");
		}
        return date;
    }
}
