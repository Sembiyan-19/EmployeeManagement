//                                                                             ;
package com.ideas2it.employeeManagement.dao;

import java.util.List;

import com.ideas2it.employeeManagement.model.Employee;
import com.ideas2it.employeeManagementException.EmployeeManagementException;

/**
 * Dao interface for employee management
 *
 * @version 1.1 25 March 2021
 * @author Sembiyan
 */
public interface EmployeeDao {
    
    /**
     * Inserts the address details such as door number, street, city, pincode,
     * state, country and address list
     * @param employee    Employee object containing employee's details
     * @return
     *     true if details are successfully inserted, else returns false
     * @throws EmployeeManagementException 
     */
    public boolean insertEmployee(Employee employee) 
            throws EmployeeManagementException;

    /**
     * Retrieves a employee's details
     * @param id          employee id whose details should be retrieved
     * @return            returns employee's details
     * @throws EmployeeManagementException 
     */
    public Employee retrieveEmployee(int id) 
            throws EmployeeManagementException;

    /**
     * Updates the personal details of the employee such as name, salary,
     * mobile number, date of birth and address details
     * @param employee         Employee object containing employee's details
     * @return
     *     true if details are successfully updated else returns false
     * @throws EmployeeManagementException 
     */
    public boolean updateEmployee(Employee employee) 
            throws EmployeeManagementException;

    /**
     * Fetches all the employees adds and returns the list
     * @return         complete list of employees
     * @throws EmployeeManagementException 
     */
    public List<Employee> getAllEmployees(boolean isDeleted) 
            throws EmployeeManagementException;
}