//                                                                             ;
package com.ideas2it.projectManagement.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.ideas2it.employeeManagement.model.Employee;
import com.ideas2it.projectManagement.dao.ProjectDao;
import com.ideas2it.projectManagement.model.Project;
import com.ideas2it.sessionFactory.HibernateSessionFactory;

/**
 * Class which implements Dao interface
 *
 * @version 2.1 21 April 2021
 * @author Sembiyan
 */
public class ProjectDaoImpl implements ProjectDao {

    private HibernateSessionFactory singleton = HibernateSessionFactory.getInstance();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertProject(Project project) {
        boolean isAdded = true;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(project);
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
    public Project retrieveProject(int projectId) {
        Project project = null;
        Session session = null;
        List<Employee> employees = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            project = (Project) session.get(Project.class, projectId);
            project.getEmployees().size();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return project;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateProject(Project project) {
        boolean isUpdated = true;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(project);
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
    public List<Project> getAllProjects(boolean isDeleted) {
        List<Project> projects = null;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Project.class);
            criteria.add(Restrictions.eq("isDeleted", isDeleted));
            projects = criteria.list();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return projects;
    }
}
