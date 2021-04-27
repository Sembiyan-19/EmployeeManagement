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
    public String retrieveProjectDetails(int projectId) {
        String employeesAssociated = "\n\nEmployees associated: ";
        Project project = projectDao.retrieveProject(projectId);
        for (Employee employee : project.getEmployees()) {
            employeesAssociated = employeesAssociated + employee.getId() 
                    + "  --->  " + employee.getName() 
                    + "\n                      ";
        }
        return (getSpacing() + project.toString() + employeesAssociated
                + getSpacing());
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
    public boolean deleteProjectDetails(int projectId) {
        Project project = projectDao.retrieveProject(projectId);
        project.setIsDeleted(true);
        project.setEmployees(null);
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
    public boolean assignEmployees(int projectId, List<Integer> employeeIds) {
        EmployeeService employeeService = new EmployeeServiceImpl();
        Project project = projectDao.retrieveProject(projectId);
		List<Employee> employees = project.getEmployees();
        for (Integer employeeId : employeeIds) {
            employees.add(employeeService.retrieveEmployee(employeeId));
        }
		project.setEmployees(employees);
        return projectDao.updateProject(project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean unassignEmployees(int projectId,
            List<Integer> employeeIds) {
        Project project = projectDao.retrieveProject(projectId);
        List<Employee> employees = project.getEmployees();
        for (Integer employeeId : employeeIds) {
            int indexOfEmployee = 0;
            int count=0;
            for (Employee employee : employees) {
                count++;
                if (employee.getId() == employeeId) {
                    indexOfEmployee = count - 1;
                }
            }
            employees.remove(indexOfEmployee);
        }
		project.setEmployees(employees);
        return projectDao.updateProject(project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllProjects() {
        List<String> projects = new ArrayList<String>();
        for (Project project : projectDao.getAllProjects()) {
            projects.add(project.toString() + getSpacing());
        }
        return projects;
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
    public List<Integer> checkEmployeesPresence(List<Integer> employeeIds) {
        List<Integer> invalidIds = new ArrayList<Integer>();
        EmployeeService employeeService = new EmployeeServiceImpl();
        for (Integer employeeId : employeeIds) {
            if (false == employeeService.checkEmployeeIdPresence(employeeId)) {
                invalidIds.add(employeeId);
            }
        }
        return invalidIds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<List<Integer>> checkEmployeesInProject(int projectId,
            List<Integer> employeeIds) {
        List<List<Integer>> listOfIds = new ArrayList<List<Integer>>(2);
        List<Integer> idsPresent = new ArrayList<Integer>();
        List<Integer> useableIds = new ArrayList<Integer>();
        Project project = projectDao.retrieveProject(projectId);
        List<Employee> employees = project.getEmployees();
        for (Integer employeeId : employeeIds) {
            boolean isPresent = false;
            for (Employee employee : employees) {
                if (employee.getId() == employeeId) {
                    isPresent = true;
                }
            }
            if (isPresent) {
                idsPresent.add(employeeId);
            } else {
                useableIds.add(employeeId);
            }
        }
        listOfIds.add(idsPresent);
        listOfIds.add(useableIds);
        return listOfIds;
    }

    private String getSpacing() {
        return ("\n\n--------------------------------------------" 
                + "------------------------------------------\n\n");
    }
}