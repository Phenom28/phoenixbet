package com.phoenixbet.ejb;

import com.phoenixbet.entity.Administrator;
import com.phoenixbet.entity.Groups;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * EJB for Administrator Entity
 *
 * @author Ogundipe Segun David
 */
@Stateless
public class AdministratorBean extends AbstractBean<Administrator> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdministratorBean() {
        super(Administrator.class);
    }

    @Override
    public void create(Administrator admin) {
        Groups group = em.find(Groups.class, 1);
        admin.addToGroup(group);
        group.addAdministrator(admin);
        em.persist(admin);
        em.merge(group);
    }

    @Override
    public void remove(Administrator admin) {
        Groups group = em.find(Groups.class, 1);
        group.removeAdministrator(admin);
        em.remove(em.merge(admin));
        em.merge(group);
    }

    public boolean findByUserName(String userName) {
        Administrator admin = null;
        try {
            admin = (Administrator) em.createNamedQuery("Administrator.findByUserName")
                    .setParameter("userName", userName).getSingleResult();
        } catch (NoResultException nre) {

        }

        return admin != null;
    }
}
