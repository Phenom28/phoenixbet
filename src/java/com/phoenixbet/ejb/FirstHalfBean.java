package com.phoenixbet.ejb;

import com.phoenixbet.entity.FirstHalf;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB for First Half Entity
 * 
 * @author Ogundipe Segun David
 */
@Stateless
public class FirstHalfBean extends AbstractBean<FirstHalf> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FirstHalfBean() {
        super(FirstHalf.class);
    }
    
}
