package com.phoenixbet.ejb;

import com.phoenixbet.entity.DoubleChance;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB for Double Chance Entity
 * 
 * @author Ogundipe Segun David
 */
@Stateless
public class DoubleChanceBean extends AbstractBean<DoubleChance> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DoubleChanceBean() {
        super(DoubleChance.class);
    }
    
}
