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
            String mobileNumber, Date dateOfBirth, List<List<String>> addresses) {
        List<Address> listOfAddress = new ArrayList<Address>();
        for (List<String> addressDetails : addresses) {
            listOfAddress.add(new Address(addressDetails.get(0),
                    addressDetails.get(1), addressDetails.get(2),
                    addressDetails.get(3), addressDetails.get(4),
                    addressDetails.get(5), addressDetails.get(6), false));
        }
        boolean isAdded = employeeDao.insertEmployee(new Employee(id, name, 
                salary, mobileNumber, dateOfBirth, false, listOfAddress));
        return isAdded;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee retrieveEmployee(int id) {
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
    public boolean deleteEmployee(int id) {
        Employee employee = employeeDao.retrieveEmployee(id);
        employee.setIsDeleted(true);
		employee.setProjects(null);
        return employeeDao.updateEmployee(employee);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean restoreEmployee(int id) {
        Employee employee = employeeDao.retrieveEmployee(id);
        employee.setIsDeleted(false);
        return employeeDao.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateEmployee(int id, String name, 
                float salary, String mobileNumber, Date dateOfBirth) {
        Employee employee = employeeDao.retrieveEmployee(id);
        if (null != name) {
            employee.setName(name);
        }
        if (0.0 != salary) {
            employee.setSalary(salary);
        }
        if (null != mobileNumber) {
            employee.setMobileNumber(mobileNumber);
        }
        if (null != dateOfBirth) {
            employee.setDateOfBirth(dateOfBirth);
        }
        return employeeDao.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addNewAddress(int id, List<String> addressDetails) {
        Employee employee = employeeDao.retrieveEmployee(id);
        List<Address> listOfAddress = employee.getAddresses();
        listOfAddress.add(new Address(addressDetails.get(0),
                addressDetails.get(1), addressDetails.get(2), 
                addressDetails.get(3), addressDetails.get(4),
                addressDetails.get(5), addressDetails.get(6), false));
		employee.setAddresses(listOfAddress);
        return employeeDao.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteExistingAddress(int id, int selectedAddressOption) {
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateExistingAddress(int id, int selectedAddressOption, 
            String doorNumber, String street, String city, String pincode,
            String state, String country, String addressType) {
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignAProject(int projectId, int employeeId) {
        ProjectService projectService = new ProjectServiceImpl();
        Employee employee = employeeDao.retrieveEmployee(employeeId);
        List<Project> projects = employee.getProjects();
        projects.add(projectService.retrieveProject(projectId));
        employee.setProjects(projects);
        employeeDao.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unassignAProject(int projectId, int employeeId) {
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getAvailableProjects(int employeeId) {
        boolean isPresent;
        List<Project> availableProjects = new ArrayList<Project>();
        ProjectService projectService = new ProjectServiceImpl();
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
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees(false);
	}
	
    /**
     * {@inheritDoc}
     */
	@Override
	public List<Employee> getDeletedEmployees() {
		return employeeDao.getAllEmployees(true);
	}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkEmployeeIdPresence(int id) {
        boolean isPresent = false;
        Employee employee = employeeDao.retrieveEmployee(id);
        if (null != employee) {
            if (false == employee.getIsDeleted()) {
                isPresent = true;
            }
        }
        return isPresent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkEmployeeIdPresenceIncludingDeleted(int id) {
        boolean isPresent = false;
        if (null != employeeDao.retrieveEmployee(id)) {
            isPresent = true;
        }
        return isPresent;
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
    public Date getDateOfBirth(String dateOfBirth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        dateFormat.setLenient(false);
        try {
            date = dateFormat.parse(dateOfBirth);
        } catch (Exception e) {
            date = null;
        }
        return date;
    }
}
