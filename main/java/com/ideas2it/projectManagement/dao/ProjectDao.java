//                                                                             ;
package com.ideas2it.projectManagement.dao;

import java.sql.Connection;
import java.util.List;

import com.ideas2it.projectManagement.model.Project;

public interface ProjectDao {

    /**
     * Adds the project details such as project Id, name, manager & department
     * @param project    Project object containing project details
     * @return        
     *     true if details are successfully added else returns false
     */
    public boolean insertProject(Project project);

    /**
     * Retrieves the project details such as project Id, name, manager,
     * department, and list of employees
     * @param projectId          Id of the project which has to be retrieved
     * @return         Project object containing project details
     */
    public Project retrieveProject(int projectId);

    /**
     * Updates the project details such as project name, manager and department
     * @param project    Project object containing project details
     * @return       
     *     true if details are successfully updated else returns false
     */
    public boolean updateProject(Project project);

    /**
     * Fetches all the projects and returns the list
     * @return         complete list of projects
     */
    public List<Project> getAllProjects(boolean isDeleted);
}
