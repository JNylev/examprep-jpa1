/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import com.mycompany.persistencewithjpa1.ProjectUser;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author jakob
 */
public class JpaFacade
{

    EntityManagerFactory emf;

    public JpaFacade(EntityManagerFactory emf)
    {
        this.emf = emf;

    }

    public ProjectUser CreateUser(ProjectUser p)
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return p;
        }
        finally
        {
            em.close();
        }
    }

    public ProjectUser deletePerson(int id)
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
            ProjectUser p = em.find(ProjectUser.class, id);
            em.remove(p);
            em.getTransaction().commit();
            return p;
        }
        finally
        {
            em.close();
        }

    }

    public ProjectUser FindUser(int id)
    {
        EntityManager em = emf.createEntityManager();

        ProjectUser p = null;

        try
        {
            em.getTransaction().begin();
            p = em.find(ProjectUser.class, id);
            em.getTransaction().commit();
            return p;
        }
        finally
        {
            em.close();
        }
    }

    public ProjectUser editUser(ProjectUser pers)
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
            ProjectUser p = em.find(ProjectUser.class, pers.getId());
            if (p != null)
            {
                p = pers;
                em.merge(p);
                em.getTransaction().commit();
                return p;
            }
        }
        finally
        {
            em.close();
        }

        return null;
    }

    public List<ProjectUser> getPersons()
    {
        EntityManager em = emf.createEntityManager();

        List<ProjectUser> persons = null;

        try
        {
            em.getTransaction().begin();
            persons = em.createQuery("Select p from Person p").getResultList();
            em.getTransaction().commit();
            return persons;
        }
        finally
        {
            em.close();
        }
    }
    
    public static void main(String[] args)
    {
        HashMap<String, Object> props = new HashMap();
        props.put("javax.persistence.schema-generation.database.action", "drop-and-create");
        //props.put("javax.persistence.schema-generation.database.action", "clear-db");
        
        Persistence.generateSchema("jpa_basics1", props);
    }
    
    

}
