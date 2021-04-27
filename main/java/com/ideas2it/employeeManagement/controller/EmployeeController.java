//                                                                             ;
package com.ideas2it.employeeManagement.controller;

import java.util.Date;
import java.util.List;

import com.ideas2it.employeeManagement.service.EmployeeService;
import com.ideas2it.employeeManagement.service.impl.EmployeeServiceImpl;

/**
 * Class that acts as a controller
 *
 * @version 2.1 18 March 2021
 * @author Sembiyan
 */
public class EmployeeController {

    private EmployeeService employeeService = new EmployeeServiceImpl();

    /**
     * Adds the address details such as door number, street, city, pincode,
     * state, country and address list
     * @param id          id of the employee
     * @param name         name of the employee
     * @param salary          salary of the employee
     * @param mobileNumber          mobile number of the employee
     * @param dateOfBirth          date of birth of the employee
     * @param addresses         list containing addresses
     * @return         true is details are successfully added else returns false
     */
    public boolean addEmployee(int id, String name, float salary,
            String mobileNumber, Date dateOfBirth, List<List<String>> addresses) {
        return employeeService.addEmployee(id, name, salary,
                mobileNumber, dateOfBirth, addresses);
    }

    /**
     * Retrieves the employee's details in string format
     * @param id          employee id whose details should be retrieved
     * @return            returns employee's details in string format
     */
    public String retrieveEmployee(int id) {
        return employeeService.retrieveEmployeeDetails(id);
    }

     /**
     * Removes a employee's employees
     * @param id          ID of employee whose details should be deleted
     * @return
     *     true if employee's details are successfully deleted
     * else returns false
     */
    public boolean deleteEmployee(int id) {
        return employeeService.deleteEmployee(id);
    }

    /**
     * Updates the employee details such as  name, salary, mobile number,
     * date of birth
     * @param id           Id of the employee whose details should be updated
     * @param name          name of the employee
     * @param salary          salary of the employee
     * @param mobileNumber          mobile number of the employee
     * @param dateOfBirth          date of birth of the employee
     * @return         
     *     true if details are successfully updated else returns false
     */
    public boolean updateEmployee(int id, String name, float salary,
                String mobileNumber, Date dateOfBirth) {
        return employeeService.updateEmployee(id, name, salary,
                mobileNumber, dateOfBirth);
    }

    /**
     * Adds new address to the employee
     * @param id       ID of the employee to whom the address should be added
     * @param addressDetails        list containg an address' details
     * @return
     *     true if a new address is successfully added else returns false
     */
    public boolean addNewAddress(int id, List<String> addressDetails) {
        return employeeService.addNewAddress(id, addressDetails);
    }

    /**
     * Deletes a particular address of a employee
     * @param id         ID of the employee whose address should be deleted
     * @param selectedAddressOption 
     *         Option that points the address which has to be deleted
     * @return
     *     true if a address is successfully deleted else returns false
     */
    public boolean deleteExistingAddress(int id, int selectedAddressOption) {
        return employeeService.deleteExistingAddress(id,
                selectedAddressOption);
    }

    /**
     * Updates the mobile number of the employee
     * @param id
     *         ID of the employee to whom the details has to be updated
     * @param selectedAddressOption
     *         Option that points the address to which the details
     * has to be updated
     * @param doorNumber          door number of the employee
     * @param street         street of the employee
     * @param city          city of the employee
     * @param pincode          pincode of the employee
     * @param state          state of the employee
     * @param country          country of the employee
     * @param addressType        type of address
     */
    public boolean updateExistingAddress(int id, int selectedAddressOption, 
            String doorNumber, String street, String city, String pincode,
            String state, String country, String addressType) {
        return employeeService.updateExistingAddress(id, 
                selectedAddressOption, doorNumber, street,
                city, pincode, state, country, addressType);
    }

    /**
     * Fetches the list of employees, converts them to string format,
     * adds them to a list and returns the list
     * @return         complete list of employees
     */
    public List<String> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    /**
     * Assigns projects for a employee
     * @param projectIds
     *     Ids of projects which has to be assigned for a employee
     * @param employeeId
     *     Id of the employee to whom the projects should be assigned
     * @return         
     *     true if projects are successfully assigned for a employee,
     * else returns false
     */
    public boolean assignProjects(List<Integer> projectIds, int employeeId) {
        return employeeService.assignProjects(projectIds, employeeId);
    }

    /**
     * Unassigns projects for a employee
     * @param projectIds
     *     Ids of projects which has to be unassigned
     * @param employeeId
     *     Id of the employee to whom the projects should be unassigned
     * @return         
     *     true if projects are successfully unassigned for a employee,
     * else returns false
     */
    public boolean unassignProjects(List<Integer> projectIds, int employeeId) {
        return employeeService.unassignProjects(projectIds, employeeId);
    }

    /**
     * Retrieves all the addresses of an employee
     * @param id         ID of the employee whose addresses should be retrieved
     * @return           list of address
     */
    public List<String> getAllAddress(int id) {
        return employeeService.getAllAddress(id);
    }

    /**
     * Checks the presence of employee
     * @param id          ID of employee which has to be checked
     * @return
     *     true if Id is present else returns false
     */
    public boolean checkEmployeeIdPresence(int id) {
        return employeeService.checkEmployeeIdPresence(id);
    }

    /**
     * Checks the presence of employee icluding the ids which are 
     * deleted
     * @param id          ID of employee which has to be checked
     * @return
     *     true if Id is present else returns false
     */
    public boolean checkEmployeeIdPresenceIncludingDeleted(int id) {
        return employeeService.checkEmployeeIdPresenceIncludingDeleted(id);
    }

    /**
     * Checks the presence of projects in a employee
     * @param projectIds          list of project Ids which has to be checked
     * @return
     *     project Ids which are not present
     */
    public List<Integer> checkProjectIdPresence(List<Integer> projectIds) {
        return employeeService.checkProjectIdPresence(projectIds);
    }

    /**
     * Checks the presence of projects in a employee
     * @param projectIds          list of project Ids which has to be checked
     * @param employeeId           
     *     Id of the employee to which from which the project Ids
     * has to checked
     * @return
     *    Two lists; one containing Ids which are present and another
     * containing Ids which are not present
     */
    public List<List<Integer>> checkProjectInEmployee(List<Integer> projectIds,
            int employeeId) {
        return employeeService.checkProjectInEmployee(projectIds, employeeId);
    }

    /**
     * Checks whether the mobile number is valid
     * @param mobileNumber        mobile number that has to be validated
     * @return         true if the mobile number is valid, else returns false
     */
    public boolean validateMobileNumber(String mobileNumber) {
        return employeeService.validateMobileNumber(mobileNumber);
    }

    /**
     * Checks the date format and returns the date if it is valid
     * @param dateOfBirth        date of birth that has to be validated
     * @return                  validated date of birth
     */
    public Date getDateOfBirth(String dateOfBirth) {
        return employeeService.getDateOfBirth(dateOfBirth);
    }
}

