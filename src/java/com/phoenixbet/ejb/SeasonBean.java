package com.phoenixbet.ejb;

import com.phoenixbet.entity.Season;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB for Season Entity
 * 
 * @author Ogundipe Segun David
 */
@Stateless
public class SeasonBean extends AbstractBean<Season> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SeasonBean() {
        super(Season.class);
    }
    
}
