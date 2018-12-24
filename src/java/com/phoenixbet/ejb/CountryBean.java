package com.phoenixbet.ejb;

import com.phoenixbet.entity.Country;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB for Country Entity
 * 
 * @author Ogundipe Segun David
 */
@Stateless
public class CountryBean extends AbstractBean<Country> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CountryBean() {
        super(Country.class);
    }
    
}
