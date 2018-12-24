package com.phoenixbet.ejb;

import com.phoenixbet.entity.OverUnder;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB for Over Under Entity
 * 
 * @author Ogundipe Segun David
 */
@Stateless
public class OverUnderBean extends AbstractBean<OverUnder> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OverUnderBean() {
        super(OverUnder.class);
    }
    
}
