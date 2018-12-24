package com.phoenixbet.ejb;

import com.phoenixbet.entity.SecondHalf;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB for Second Half Entity
 * 
 * @author Ogundipe Segun David
 */
@Stateless
public class SecondHalfBean extends AbstractBean<SecondHalf> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecondHalfBean() {
        super(SecondHalf.class);
    }
    
}
