package com.phoenixbet.ejb;

import com.phoenixbet.entity.Btts;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB for Both Teams To Score Entity
 * 
 * @author Ogundipe Segun David
 */
@Stateless
public class BttsBean extends AbstractBean<Btts> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BttsBean() {
        super(Btts.class);
    }
    
}
