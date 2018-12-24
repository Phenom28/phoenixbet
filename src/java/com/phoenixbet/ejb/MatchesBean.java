package com.phoenixbet.ejb;

import com.phoenixbet.entity.Matches;
import com.phoenixbet.entity.Team;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

/**
 * EJB for Matches Entity
 * 
 * @author Ogundipe Segun David
 */
@Stateless
public class MatchesBean extends AbstractBean<Matches> {

    @PersistenceContext(unitName = "PhoenixPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MatchesBean() {
        super(Matches.class);
    }

    public List<Matches> lastTenHomeMatches(Team team, Date date) {
        List<Matches> homeMatches = em.createNamedQuery("Match.lastHomeMatches")
                .setParameter("homeTeam", team).setParameter("date", date, TemporalType.DATE)
                .setMaxResults(10).getResultList();
        return homeMatches;
    }

    public List<Matches> lastTenAwayMatches(Team team, Date date) {
        List<Matches> awayMatches = em.createNamedQuery("Match.lastAwayMatches")
                .setParameter("awayTeam", team).setParameter("date", date, TemporalType.DATE)
                .setMaxResults(10).getResultList();
        return awayMatches;
    }

    public Matches matchId(Team team, Date date) {
        Matches match = (Matches) em.createNamedQuery("Match.findMatch")
                .setParameter("homeTeam", team).setParameter("date", date, TemporalType.DATE)
                .getSingleResult();
        return match;
    }

    public List<Matches> findByStatus(Integer status) {
        List<Matches> matches = em.createNamedQuery("Match.findByStatus")
                .setParameter("status", status).getResultList();
        return matches;
    }

    public List<Matches> findByLeague(Integer league) {
        List<Matches> matches = em.createNamedQuery("Matches.findByLeague")
                .setParameter("league", league).getResultList();
        return matches;
    }

    public List<Matches> findByStatusLeague(Integer league, Integer status) {
        List<Matches> matches = em.createNamedQuery("Matches.findbyStatusLeague")
                .setParameter("league", league).setParameter("status", status)
                .getResultList();
        return matches;
    }

    public List<Matches> findByStatusRound(Integer league, Integer status, Integer round) {
        List<Matches> matches = em.createNamedQuery("Matches.findByStatusRound")
                .setParameter("league", league).setParameter("status", status)
                .setParameter("round", round).getResultList();
        return matches;
    }

    public List<Matches> findFinishedByRound(Integer leagueId) {
        List<Matches> matches = em.createNamedQuery("Matches.findFinishedByRound")
                .setParameter("league", leagueId).getResultList();
        return matches;
    }
}
