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
        //addresses.clear();
        return isAdded;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String retrieveEmployeeDetails(int id) {
        String projectsAssociated = "\nProjects associated: ";
        Employee employee = employeeDao.retrieveEmployee(id);
        for (Project project : employee.getProjects()) {
            projectsAssociated = projectsAssociated + project.getId() 
                    + "  --->  " + project.getName() 
                    + "\n                     ";
        }
        return (getSpacing() + employee.toString() + projectsAssociated
                + getSpacing());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee retrieveEmployee(int id) {
        return employeeDao.retrieveEmployee(id);
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
        List<Address> listOfAddress = employee.getAddresses(); //-----------------------------------
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
		employee.getAddresses().get(selectedAddressOption).setIsDeleted(true);
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
		Address address 
		        = employee.getAddresses().get(selectedAddressOption);
        if (null != doorNumber) {
            address.setDoorNumber(doorNumber);
        }
        if (null != street) {
            address.setStreet(street);
        }
        if (null != city) {
            address.setCity(city);
        }
        if (null != pincode) {
            address.setPincode(pincode);
        }
        if (null != state) {
            address.setState(state);
        }
        if (null != country) {
            address.setCountry(country);
        }
        return employeeDao.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllEmployees() {
        List<String> employees = new ArrayList<String>();
        for (Employee employee : employeeDao.getAllEmployees()) {
            employees.add(employee.toString() + getSpacing());
        }
        return employees;
    }

    /**
     * {@inheritDoc}
     */
    public boolean assignProjects(List<Integer> projectIds, int employeeId) {
        Employee employee = employeeDao.retrieveEmployee(employeeId);
        ProjectService projectService = new ProjectServiceImpl();
		List<Project> projects = employee.getProjects();
        for (Integer projectId : projectIds) {
            projects.add(projectService.retrieveProject(projectId));
        }
		employee.setProjects(projects);
        return employeeDao.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    public boolean unassignProjects(List<Integer> projectIds, int employeeId) {
        Employee employee = employeeDao.retrieveEmployee(employeeId);
        List<Project> projects = employee.getProjects();
        for (Integer projectId : projectIds) {
            int indexOfTheProject = 0;
            int count = 0;
            for (Project project : projects) {
                count++;
                if (project.getId() == projectId) {
                    indexOfTheProject = count - 1;
                }
            }
            projects.remove(indexOfTheProject);
        }
		employee.setProjects(projects);
        return employeeDao.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllAddress(int id) {
        List<String> listOfAddress = new ArrayList<String>();
        //List<Address> addresses 
         //       = employeeDao.retrieveEmployee(id).getAddresses();
        for (Address address : retrieveEmployee(id).getAddresses()) {
            listOfAddress.add(address.toString());
        }
        return listOfAddress;
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
        //Employee employee = employeeDao.retrieveEmployee(id);
        if (null != employeeDao.retrieveEmployee(id)) {
            isPresent = true;
        }
        return isPresent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> checkProjectIdPresence(List<Integer> projectIds) {
        List<Integer> invalidIds = new ArrayList<Integer>();
        ProjectService projectService = new ProjectServiceImpl();
        for (Integer projectId : projectIds) {
            if (false == projectService.checkProjectIdPresence(projectId)) {
                invalidIds.add(projectId);
            }
        }
        return invalidIds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<List<Integer>> checkProjectInEmployee(List<Integer> projectIds, 
                int employeeId) {
        List<List<Integer>> listOfIds = new ArrayList<List<Integer>>(2);
        List<Integer> invalidIds = new ArrayList<Integer>();
        List<Integer> useableIds = new ArrayList<Integer>();
        Employee employee = employeeDao.retrieveEmployee(employeeId);
        List<Project> projects = employee.getProjects();
        for (Integer projectId : projectIds) {
            boolean isPresent = false;
            for (Project project : projects) {
                if (project.getId() == projectId) {
                    isPresent = true;
                }
            }
            if (isPresent) {
                invalidIds.add(projectId);
            } else {
                useableIds.add(projectId);
            }
        }
        listOfIds.add(invalidIds);
        listOfIds.add(useableIds);
        return listOfIds;
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
    
    private String getSpacing() {
        return ("\n\n--------------------------------------------" 
                + "------------------------------------------\n\n");
    }
}