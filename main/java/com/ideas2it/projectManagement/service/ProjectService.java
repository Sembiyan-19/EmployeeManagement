//                                                                             ;
package com.ideas2it.projectManagement.service;

import java.util.List;

import com.ideas2it.employeeManagement.model.Employee;
import com.ideas2it.employeeManagementException.EmployeeManagementException;
import com.ideas2it.projectManagement.model.Project;

/**
 * Interface to provide services for employee management
 *
 * @version 1.1 25 March 2021
 * @author Sembiyan
 */
public interface ProjectService {

    /**
     * Adds the employee details such as project Id, name, manager & department
     * @param projectId           Project Id
     * @param projectName          name of the project
     * @param projectManager          manager of the project
     * @param department          department to which the project is associated
     * @return         
     *     true if details are successfully added else returns false
     * @throws EmployeeManagementException 
     */
    public void addProject(int projectId, String projectName,
            String projectManager, String department) 
            throws EmployeeManagementException;

    /**
     * Retrieves the project details such as project Id, name, manager,
     * department, and list of employees
     * @param projectId          Id of the project which has to be retrieved
     * @return         the project object
     * @throws EmployeeManagementException 
     */
    public Project retrieveProject(int projectId) 
            throws EmployeeManagementException;

   /**
     * Deletes a project details
     * @param projectId          Id of the project which has to be deleted
     * @return        
     *     true if details are successfully deleted else returns false
     * @throws EmployeeManagementException 
     */
    public void deleteProject(int projectId) 
            throws EmployeeManagementException;

    /**
     * Restores a project which is deleted
     * @param projectId          Id of the project which has to be restored
     * @return        
     *     true if project is successfully restored else returns false
     * @throws EmployeeManagementException 
     */
    public void restoreProject(int projectId) 
            throws EmployeeManagementException;
    
    /**
     * Updates the project details such as project name, manager,
     * and department
     * @param projectId
     *      project Id to which the details have to be updated
     * @param projectName        updated name for the project
     * @param projectManager         updated manager for the project
     * @param department         updated department for the project
     * @return
     *     true if details are successfully updated else returns false
     * @throws EmployeeManagementException 
     */
    public void updateProject(int projectId, String projectName,
            String projectManager, String department) 
            throws EmployeeManagementException;

    /**
     * Assigns employee for a project
     * @param projectId
     *     Id of project to which the employee has to be assigned
     * @param employeeId
     *     Id of the employee who has to be assigned for the project
     * @return         
     *     true if employee is successfully assigned for the project,
     * else returns false
     * @throws EmployeeManagementException 
     */
    public void assignAEmployee(int projectId, int employeeId) 
            throws EmployeeManagementException;

    /**
     * Unssigns employee for a project
     * @param projectId
     *     Id of project from which the employee has to be unassigned
     * @param employeeId
     *     Id of the employee who has to be unassigned for the project
     * @return         
     *     true if employee is successfully unassigned from the project,
     * else returns false
     * @throws EmployeeManagementException 
     */
    public void unassignAEmployee(int projectId, int employeeId) 
            throws EmployeeManagementException;

    /**
     * Fetches the list of projects, converts them to string format,
     * adds them to a list and returns the list
     * @return         complete list of projects
     * @throws EmployeeManagementException 
     */
    public List<Project> getAllProjects() 
            throws EmployeeManagementException;
    
    /**
     * Fetches the list of projects which are deleted
     * @return         complete list of projects which are deleted
     * @throws EmployeeManagementException 
     */
    public List<Project> getDeletedProjects() 
            throws EmployeeManagementException;

    /**
     * Gets the employees who can be assigned for a project
     * @param projectId
     *     Id of project
     * @return         
     *     list of employees who can be assigned for a project
     * @throws EmployeeManagementException 
     */
    public List<Employee> getAvailableEmployees(int projectId) 
            throws EmployeeManagementException;
    
    /**
     * Checks the presence of project id
     * @param projectId          ID of project which has to be checked
     * @return
     *     true if project id is present else returns false
     * @throws EmployeeManagementException 
     */
    public boolean checkProjectIdPresence(int projectId) 
            throws EmployeeManagementException;

    /**
     * Checks the presence of project id including deleted projects
     * @param projectId          ID of project which has to be checked
     * @return
     *     true if project id is present else returns false
     * @throws EmployeeManagementException 
     */
    public boolean checkProjectIdPresenceWithDeleted(int projectId) 
            throws EmployeeManagementException;
}