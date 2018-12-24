package com.phoenixbet.ejb;

import com.phoenixbet.entity.Groups;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB for Groups Entity
 * 
 * @author Ogundipe Segun David
 */
@Stateless
public class GroupsBean extends AbstractBean<Groups> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupsBean() {
        super(Groups.class);
    }
    
}
