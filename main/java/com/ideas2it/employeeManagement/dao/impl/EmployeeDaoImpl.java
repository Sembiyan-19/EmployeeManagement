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
import com.ideas2it.sessionFactory.SessionFactoryImpl;

/**
 * Class which implements Dao interface
 *
 * @version 2.1 21 April 2021
 * @author Sembiyan
 */
public class EmployeeDaoImpl implements EmployeeDao {

    private SessionFactoryImpl singleton = SessionFactoryImpl.getInstance();

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
    public List<Employee> getAllEmployees() {
        List<Employee> employees = null;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Employee.class);
            criteria.add(Restrictions.eq("isDeleted", false));
            employees = criteria.list();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return employees;
    }

        public Employee re(int id) {
        List<Employee> employees = null;
        List<Address> addr = null;
        List<String> s = null;
        Employee employee = null;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            //String hql = "select e from Employee as e left join e.addresses as a where e.id=7 and a.city = 'madurai'";
            String hql = "select e, a from Employee as e left join e.addresses as a where e.id=7 and a.city = 'madurai'";
            Query query = session.createQuery(hql);
            List li = query.list();
            System.out.println(li);
            for (Object o : li) {
                employees.add((Employee)o);
            }
            System.out.println("\n\n\n"+employees.get(0).toString()+"\n\n\n");
            //Employee i = (Employee)query.list().get(0);
            //System.out.println("\n\n\n"+i+"\n\n\n");
            //int s = query.list().size();
            //for (int i=0; i<s; i++) {
               //System.out.println("\n\n\n\n"+query.list().get(i).toString()+"\n\n\n\n");
            //}
            //addr = query.list();
            //String d= "";
            //for (Address e : addr) {
            //for (String e : s) {
            //for (Employee e : employees) {
                //d = d + e.toString();
            //}
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return employee;
    }
}