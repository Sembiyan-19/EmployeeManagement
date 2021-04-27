//                                                                             ;
package com.ideas2it.employeeManagement.dao;

import java.util.List;

import com.ideas2it.employeeManagement.model.Employee;

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
     */
    public boolean insertEmployee(Employee employee);

    /**
     * Retrieves a employee's details
     * @param id          employee id whose details should be retrieved
     * @return            returns employee's details
     */
    public Employee retrieveEmployee(int id);

    /**
     * Updates the personal details of the employee such as name, salary,
     * mobile number, date of birth and address details
     * @param employee         Employee object containing employee's details
     * @return
     *     true if details are successfully updated else returns false
     */
    public boolean updateEmployee(Employee employee);

    /**
     * Fetches all the employees adds and returns the list
     * @return         complete list of employees
     */
    public List<Employee> getAllEmployees();
}