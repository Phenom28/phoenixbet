package com.phoenixbet.ejb;

import com.phoenixbet.entity.SecondHalfGoals;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB for Second Half Goals Entity
 * 
 * @author Ogundipe Segun David
 */
@Stateless
public class SecondHalfGoalsBean extends AbstractBean<SecondHalfGoals> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecondHalfGoalsBean() {
        super(SecondHalfGoals.class);
    }
    
}
