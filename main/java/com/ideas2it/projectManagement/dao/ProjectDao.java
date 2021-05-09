//                                                                             ;
package com.ideas2it.projectManagement.dao;

import java.util.List;

import com.ideas2it.employeeManagementException.EmployeeManagementException;
import com.ideas2it.projectManagement.model.Project;

public interface ProjectDao {

    /**
     * Adds the project details such as project Id, name, manager & department
     * @param project    Project object containing project details
     * @return        
     *     true if details are successfully added else returns false
     * @throws EmployeeManagementException 
     */
    public void insertProject(Project project) 
            throws EmployeeManagementException;

    /**
     * Retrieves the project details such as project Id, name, manager,
     * department, and list of employees
     * @param projectId          Id of the project which has to be retrieved
     * @return         Project object containing project details
     * @throws EmployeeManagementException 
     */
    public Project retrieveProject(int projectId) 
            throws EmployeeManagementException;

    /**
     * Updates the project details such as project name, manager and department
     * @param project    Project object containing project details
     * @return       
     *     true if details are successfully updated else returns false
     * @throws EmployeeManagementException 
     */
    public void updateProject(Project project) 
            throws EmployeeManagementException;

    /**
     * Fetches all the projects and returns the list
     * @return         complete list of projects
     * @throws EmployeeManagementException 
     */
    public List<Project> getAllProjects(boolean isDeleted) 
            throws EmployeeManagementException;
}
