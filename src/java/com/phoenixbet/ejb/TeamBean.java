package com.phoenixbet.ejb;

import com.phoenixbet.entity.League;
import com.phoenixbet.entity.Team;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB for Team Entity
 * 
 * @author Ogundipe Segun David
 */
@Stateless
public class TeamBean extends AbstractBean<Team> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeamBean() {
        super(Team.class);
    }
 
    public void create(Team team, League league){
        League l = em.find(League.class, league.getId());
        team.addToLeague(l);
        l.addTeam(team);
        em.persist(team);
        em.merge(l);
    }
    
    public void create(Team team, List<League> leagues){
        for (League league : leagues) {
            League l = em.find(League.class, league.getId());
            team.addToLeague(l);
            l.addTeam(team);
            em.persist(team);
            em.merge(l);
        }
    }
    
    public Team findByName(String teamName){
        Team team = (Team) em.createNamedQuery("Team.findByName").setParameter("name", teamName)
                .getSingleResult();
        return team;
    }
    
    public List<Team> findByLeague(League league){
        List<Team>  teams = em.createNamedQuery("Team.findByLeague").setParameter("league", league)
                .getResultList();
        return teams;
    }
}
