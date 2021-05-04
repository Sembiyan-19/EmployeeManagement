//                                                                             ;
package com.ideas2it.employeeManagement.dao.impl;

import java.util.List;
//import org.hibernate.Criteria;
//import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.Criteria;
//import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.ideas2it.employeeManagement.dao.EmployeeDao;
import com.ideas2it.employeeManagement.model.Address;
import com.ideas2it.employeeManagement.model.Employee;
import com.ideas2it.sessionFactory.HibernateSessionFactory;

/**
 * Class which implements Dao interface
 *
 * @version 2.1 21 April 2021
 * @author Sembiyan
 */
public class EmployeeDaoImpl implements EmployeeDao {

    private HibernateSessionFactory singleton = HibernateSessionFactory.getInstance();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertEmployee(Employee employee) {
        boolean isAdded = true;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            isAdded = false;
        } finally {
            session.close();
        }
        return isAdded;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee retrieveEmployee(int id) {
        Employee employee = null;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            employee = (Employee) session.get(Employee.class, id);
            employee.getAddresses().size();
            employee.getProjects().size();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return employee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateEmployee(Employee employee) {
        boolean isUpdated = true;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            isUpdated = false;
        } finally {
            session.close();
        }
        return isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getAllEmployees(boolean isDeleted) {
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
            System.out.println(e);
        } finally {
            session.close();
        }
        return employees;
    }
}