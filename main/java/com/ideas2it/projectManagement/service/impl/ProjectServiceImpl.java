//                                                                             ;
package com.ideas2it.projectManagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeeManagement.model.Employee;
import com.ideas2it.employeeManagement.service.EmployeeService;
import com.ideas2it.employeeManagement.service.impl.EmployeeServiceImpl;
import com.ideas2it.projectManagement.dao.impl.ProjectDaoImpl;
import com.ideas2it.projectManagement.dao.ProjectDao;
import com.ideas2it.projectManagement.model.Project;
import com.ideas2it.projectManagement.service.ProjectService;

/**
 * Class implementing service interface
 *
 * @version 2.1 18 March 2021
 * @author Sembiyan
 */
public class ProjectServiceImpl implements ProjectService {

    private ProjectDao projectDao = new ProjectDaoImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addProject(int projectId, String projectName,
                String projectManager, String department) {
        return projectDao.insertProject(new Project(projectId, 
                projectName, projectManager, department, false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project retrieveProject(int projectId) {
        return projectDao.retrieveProject(projectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteProject(int projectId) {
        Project project = projectDao.retrieveProject(projectId);
        project.setIsDeleted(true);
        project.setEmployees(null);
        return projectDao.updateProject(project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean restoreProject(int projectId) {
        Project project = projectDao.retrieveProject(projectId);
        project.setIsDeleted(false);
        return projectDao.updateProject(project);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateProject(int projectId, String projectName,
                String projectManager, String department) {
        Project project = projectDao.retrieveProject(projectId);
        if (null != projectName) {
            project.setName(projectName);
        }
        if (null != projectManager) {
            project.setManager(projectManager);
        }
        if (null != department) {
            project.setDepartment(department);
        }
        return projectDao.updateProject(project);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void assignAEmployee(int projectId, int employeeId) {
    	EmployeeService employeeService = new EmployeeServiceImpl();
        Project project = projectDao.retrieveProject(projectId);
        List<Employee> employees = project.getEmployees();
        employees.add(employeeService.retrieveEmployee(employeeId));
        project.setEmployees(employees);
        projectDao.updateProject(project);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unassignAEmployee(int projectId, int employeeId) {
    	Project project = projectDao.retrieveProject(projectId);
    	List<Employee> employees = project.getEmployees();
    	int indexOfEmployee = 0;
        int count=0;
    	for (Employee employee : employees) {
    		count++;
    		if (employeeId == employee.getId()) {
    			indexOfEmployee = count - 1;
    		}
    	}
    	employees.remove(indexOfEmployee);
    	project.setEmployees(employees);
        projectDao.updateProject(project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getAllProjects() {
        return projectDao.getAllProjects(false);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getDeletedProjects() {
        return projectDao.getAllProjects(true);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkProjectIdPresence(int projectId) {
        boolean isPresent = false;
        Project project = projectDao.retrieveProject(projectId);
        if (null != project) {
            if (false == project.getIsDeleted()) {
                isPresent = true;
            }
        }
        return isPresent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkProjectIdPresenceWithDeleted(int projectId) {
        boolean isPresent = false;
        if (null != projectDao.retrieveProject(projectId)) {
            isPresent = true;
        }
        return isPresent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getAvailableEmployees(int projectId) {
    	boolean isPresent;
    	List<Employee> availableEmployees = new ArrayList<Employee>();
    	EmployeeService employeeService = new EmployeeServiceImpl();
    	Project project = projectDao.retrieveProject(projectId);
    	for (Employee employee : employeeService.getAllEmployees()) {
    		isPresent = false;
    		for (Employee employeeInProject : project.getEmployees()) {
    			if (employee.getId() == employeeInProject.getId()) {
    				isPresent = true;
    			}
    		}
    		if (false == isPresent) {
    			availableEmployees.add(employee);
    		}
    	}
    	return availableEmployees;
    }
}