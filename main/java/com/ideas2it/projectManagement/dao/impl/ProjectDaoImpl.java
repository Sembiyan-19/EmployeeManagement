//                                                                             ;
package com.ideas2it.projectManagement.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.ideas2it.employeeManagementException.EmployeeManagementException;
import com.ideas2it.logger.LoggerClass;
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

    private HibernateSessionFactory singleton 
            = HibernateSessionFactory.getInstance();
    private LoggerClass logger = new LoggerClass(ProjectDaoImpl.class);

    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insertProject(Project project) 
            throws EmployeeManagementException {
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(project);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        	logger.errorLogger(e.getMessage());
            throw new EmployeeManagementException("Failed to add project");
        } finally {
            session.close();
        }
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public Project retrieveProject(int projectId) 
            throws EmployeeManagementException {
        Project project = null;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            project = (Project) session.get(Project.class, projectId);
            if (null != project) {
            	project.getEmployees().size();
            }
        } catch (HibernateException e) {
        	logger.errorLogger(e.getMessage());
        	throw new EmployeeManagementException("Failed to retrieve project");
        } finally {
            session.close();
        }
        return project;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProject(Project project) 
            throws EmployeeManagementException {
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(project);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.errorLogger(e.getMessage());
            throw new EmployeeManagementException("Failed to update project");
        } finally {
            session.close();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getAllProjects(boolean isDeleted) 
            throws EmployeeManagementException {
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
        	logger.errorLogger(e.getMessage());
        	throw new EmployeeManagementException("Failed to load projects");
        } finally {
            session.close();
        }
        return projects;
    }
}
