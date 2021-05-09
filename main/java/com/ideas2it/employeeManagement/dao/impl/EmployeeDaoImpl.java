//                                                                             ;
package com.ideas2it.employeeManagement.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.ideas2it.employeeManagement.dao.EmployeeDao;
import com.ideas2it.employeeManagement.model.Employee;
import com.ideas2it.employeeManagementException.EmployeeManagementException;
import com.ideas2it.logger.LoggerClass;
import com.ideas2it.projectManagement.dao.impl.ProjectDaoImpl;
import com.ideas2it.sessionFactory.HibernateSessionFactory;

/**
 * Class which implements Dao interface
 *
 * @version 2.1 21 April 2021
 * @author Sembiyan
 */
public class EmployeeDaoImpl implements EmployeeDao {

    private HibernateSessionFactory singleton 
            = HibernateSessionFactory.getInstance();
    private LoggerClass logger = new LoggerClass(EmployeeDaoImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertEmployee(Employee employee) 
            throws EmployeeManagementException {
        boolean isAdded = true;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            isAdded = false;
            logger.errorLogger(e.getMessage());
            throw new EmployeeManagementException("Failed to add employee");
        } finally {
            session.close();
        }
        return isAdded;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee retrieveEmployee(int id) 
            throws EmployeeManagementException {
        Employee employee = null;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            employee = (Employee) session.get(Employee.class, id);
            if (null != employee) {
            	employee.getAddresses().size();
            	employee.getProjects().size();	
            }
        } catch (HibernateException e) {
        	logger.errorLogger(e.getMessage());
        	throw new EmployeeManagementException("Failed to retrieve employee");
        } finally {
            session.close();
        }
        return employee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateEmployee(Employee employee) 
            throws EmployeeManagementException {
        boolean isUpdated = true;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(employee);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            isUpdated = false;
            logger.errorLogger(e.getMessage());
        	throw new EmployeeManagementException("Failed to update employee");
        } finally {
            session.close();
        }
        return isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getAllEmployees(boolean isDeleted) 
            throws EmployeeManagementException {
        List<Employee> employees = null;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Employee.class);
            criteria.add(Restrictions.eq("isDeleted", isDeleted));
            employees = criteria.list();
        } catch (Exception e) {
        	logger.errorLogger(e.getMessage());
        	throw new EmployeeManagementException("Failed to retrieve employees");
        } finally {
            session.close();
        }
        return employees;
    }
}