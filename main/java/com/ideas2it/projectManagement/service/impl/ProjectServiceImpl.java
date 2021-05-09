//                                                                             ;
package com.ideas2it.projectManagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeeManagement.model.Employee;
import com.ideas2it.employeeManagement.service.EmployeeService;
import com.ideas2it.employeeManagement.service.impl.EmployeeServiceImpl;
import com.ideas2it.employeeManagementException.EmployeeManagementException;
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
    public void addProject(int projectId, String projectName,
            String projectManager, String department)
            throws EmployeeManagementException {
        projectDao.insertProject(new Project(projectId, 
                projectName, projectManager, department, false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project retrieveProject(int projectId) 
    		throws EmployeeManagementException {
        return projectDao.retrieveProject(projectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProject(int projectId) 
            throws EmployeeManagementException {
    	try {
    		Project project = projectDao.retrieveProject(projectId);
    		project.setIsDeleted(true);
    		project.setEmployees(null);
    		projectDao.updateProject(project);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Error deleting project");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restoreProject(int projectId) 
            throws EmployeeManagementException {
    	try {
    		Project project = projectDao.retrieveProject(projectId);
    		project.setIsDeleted(false);
    		projectDao.updateProject(project);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Error restoring project");
    	}
		
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void updateProject(int projectId, String projectName,
            String projectManager, String department) 
            throws EmployeeManagementException {
    	try {
	        Project project = projectDao.retrieveProject(projectId);
	        project.setName(projectName);
	        project.setManager(projectManager);
	        project.setDepartment(department);
	        projectDao.updateProject(project);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Error updating project");
    	}
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void assignAEmployee(int projectId, int employeeId) 
            throws EmployeeManagementException {
    	EmployeeService employeeService = new EmployeeServiceImpl();
    	try {
	        Project project = projectDao.retrieveProject(projectId);
	        List<Employee> employees = project.getEmployees();
	        employees.add(employeeService.retrieveEmployee(employeeId));
	        project.setEmployees(employees);
	        projectDao.updateProject(project);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Error assigning empolyee for project");
    	}
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unassignAEmployee(int projectId, int employeeId) 
            throws EmployeeManagementException {
    	try {
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
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Error unassigning empolyee for project");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getAllProjects() 
            throws EmployeeManagementException {
        return projectDao.getAllProjects(false);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getDeletedProjects() 
            throws EmployeeManagementException {
        try {
			return projectDao.getAllProjects(true);
		} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Error retrieving deleted projects");
    	}
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkProjectIdPresence(int projectId) 
            throws EmployeeManagementException {
        try {
	    	boolean isPresent = false;
	        Project project = projectDao.retrieveProject(projectId);
	        if (null != project) {
	            if (false == project.getIsDeleted()) {
	                isPresent = true;
	            }
	        }
	        return isPresent;
        } catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Error retrieving project");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkProjectIdPresenceWithDeleted(int projectId) 
            throws EmployeeManagementException {
        try {
	    	boolean isPresent = false;
	        if (null != projectDao.retrieveProject(projectId)) {
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
    public List<Employee> getAvailableEmployees(int projectId) 
            throws EmployeeManagementException {
    	try {
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
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Error displaying available employees");
    	}
    }
}