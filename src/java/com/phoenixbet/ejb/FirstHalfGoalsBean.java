package com.phoenixbet.ejb;

import com.phoenixbet.entity.FirstHalfGoals;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * First Half Goals Entity
 * 
 * @author Ogundipe Segun David
 */
@Stateless
public class FirstHalfGoalsBean extends AbstractBean<FirstHalfGoals> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FirstHalfGoalsBean() {
        super(FirstHalfGoals.class);
    }
    
}
