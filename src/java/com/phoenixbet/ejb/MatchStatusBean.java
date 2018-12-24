package com.phoenixbet.ejb;

import com.phoenixbet.entity.MatchStatus;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB for Match Status Entity
 * 
 * @author Ogundipe Segun David
 */
@Stateless
public class MatchStatusBean extends AbstractBean<MatchStatus> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MatchStatusBean() {
        super(MatchStatus.class);
    }
    
}
