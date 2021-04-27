//                                                                             ;
package com.ideas2it.projectManagement.model;

import java.util.List;

import com.ideas2it.employeeManagement.model.Employee;

/**
 * Pojo Class Containing Project Details
 *
 * @version 2.1 18 March 2021
 * @author Sembiyan
 */
public class Project {

    private int id;
    private String name;
    private String manager;
    private String department;
    private boolean isDeleted;
    private List<Employee> employees;

    public Project() {}

    public Project(int id, String name, String manager,
            String department, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.department = department;
        this.isDeleted = isDeleted;
    }

    public String toString() {
        return ("\nProject Id: " + id + "\n\nProject name: " + name
                + "\n\nProject manager: " + manager + "\n\nDepartment: "
                + department);
    }

    public int getId() { 
        return id;
    }

    public void setId(int id) {   
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {	
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
