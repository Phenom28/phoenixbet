package com.phoenixbet.ejb;

import com.phoenixbet.entity.League;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB for League Entity
 * 
 * @author Ogundipe Segun David
 */
@Stateless
public class LeagueBean extends AbstractBean<League> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LeagueBean() {
        super(League.class);
    }
    
}
