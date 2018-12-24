package com.phoenixbet.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity for Matches
 * Stores and retrieve matches data from the database
 * @author Ogundipe Segun David
 */
@Entity
@Table(name = "matches")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Matches.findAll", query = "SELECT m FROM Matches m")
    , @NamedQuery(name = "Matches.findById", query = "SELECT m FROM Matches m WHERE m.id = :id")
    , @NamedQuery(name = "Matches.findByMatchDate", query = "SELECT m FROM Matches m WHERE m.matchDate = :matchDate")
    , @NamedQuery(name = "Matches.findByMatchTime", query = "SELECT m FROM Matches m WHERE m.matchTime = :matchTime")
    , @NamedQuery(name = "Matches.findByMatchRound", query = "SELECT m FROM Matches m WHERE m.matchRound = :matchRound")
    , @NamedQuery(name = "Match.lastHomeMatches", query = "SELECT m FROM Matches m WHERE m.homeTeam = :homeTeam AND m.matchDate < :date ORDER BY m.matchDate DESC")
    , @NamedQuery(name = "Match.lastAwayMatches", query = "SELECT m FROM Matches m WHERE m.awayTeam = :awayTeam AND m.matchDate < :date ORDER BY m.matchDate DESC")
    , @NamedQuery(name = "Match.findMatch", query = "SELECT m FROM Matches m WHERE m.homeTeam = :homeTeam AND m.matchDate = :date")
    , @NamedQuery(name = "Match.findByStatus", query = "SELECT m FROM Matches m WHERE m.matchStatus.id = :status ORDER BY m.matchDate ASC")
    , @NamedQuery(name = "Matches.findFinishedByRound", query = "SELECT m FROM Matches m WHERE m.matchStatus.id = 2 AND m.league.id = :league ORDER BY m.matchRound DESC")
    , @NamedQuery(name = "Matches.findByLeague", query = "SELECT m FROM Matches m WHERE m.league.id = :league")
    , @NamedQuery(name = "Matches.findbyStatusLeague", query = "SELECT m FROM Matches m WHERE m.league.id = :league AND m.matchStatus.id = :status ORDER BY m.matchDate ASC")
    , @NamedQuery(name = "Matches.findByStatusRound", query = "SELECT m FROM Matches m WHERE m.league.id = :league AND m.matchStatus.id = :status AND m.matchRound = :round")})
public class Matches implements Serializable {

    private static final long serialVersionUID = -8926483098325553013L;
    private Integer id;
    private Date matchDate;
    private Date matchTime;
    private Integer matchRound;
    private Integer fullTimeHomeGoal;
    private Integer fullTimeAwayGoal;
    private Integer halfTimeHomeGoal;
    private Integer halfTimeAwayGoal;
    private String outcome;
    private Integer homeShot;
    private Integer awayShot;
    private Integer homeShotOnTarget;
    private Integer awayShotOnTarget;
    private Integer homeFoul;
    private Integer awayFoul;
    private Integer homeCorner;
    private Integer awayCorner;
    private Integer homeYellow;
    private Integer awayYellow;
    private Integer homeRed;
    private Integer awayRed;
    private FirstHalf firstHalf;
    private MatchStatus matchStatus;
    private Season season;
    private League league;
    private Team homeTeam;
    private Team awayTeam;
    private OverUnder overUnder;
    private SecondHalf secondHalf;
    private DoubleChance doubleChance;
    private SecondHalfGoals secondHalfGoals;
    private FirstHalfGoals firstHalfGoals;
    private Btts btts;

    public Matches() {
    }

    public Matches(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic(optional = false)
    @Column(name = "MATCH_DATE")
    @Temporal(TemporalType.DATE)
    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    @Basic
    @Column(name = "MATCH_TIME")
    @Temporal(TemporalType.TIME)
    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    @Basic(optional = false)
    @Column(name = "MATCH_ROUND")
    public Integer getMatchRound() {
        return matchRound;
    }

    public void setMatchRound(Integer matchRound) {
        this.matchRound = matchRound;
    }

    @Column(name = "FULL_TIME_HOME_GOAL")
    public Integer getFullTimeHomeGoal() {
        return fullTimeHomeGoal;
    }

    public void setFullTimeHomeGoal(Integer fullTimeHomeGoal) {
        this.fullTimeHomeGoal = fullTimeHomeGoal;
    }

    @Column(name = "FULL_TIME_AWAY_GOAL")
    public Integer getFullTimeAwayGoal() {
        return fullTimeAwayGoal;
    }

    public void setFullTimeAwayGoal(Integer fullTimeAwayGoal) {
        this.fullTimeAwayGoal = fullTimeAwayGoal;
    }

    @Column(name = "HALF_TIME_HOME_GOAL")
    public Integer getHalfTimeHomeGoal() {
        return halfTimeHomeGoal;
    }

    public void setHalfTimeHomeGoal(Integer halfTimeHomeGoal) {
        this.halfTimeHomeGoal = halfTimeHomeGoal;
    }

    @Column(name = "HALF_TIME_AWAY_GOAL")
    public Integer getHalfTimeAwayGoal() {
        return halfTimeAwayGoal;
    }

    public void setHalfTimeAwayGoal(Integer halfTimeAwayGoal) {
        this.halfTimeAwayGoal = halfTimeAwayGoal;
    }

    @Column(name = "OUTCOME")
    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
    

    @Column(name = "HOME_SHOT")
    public Integer getHomeShot() {
        return homeShot;
    }

    public void setHomeShot(Integer homeShot) {
        this.homeShot = homeShot;
    }

    @Column(name = "AWAY_SHOT")
    public Integer getAwayShot() {
        return awayShot;
    }

    public void setAwayShot(Integer awayShot) {
        this.awayShot = awayShot;
    }

    @Column(name = "HOME_SHOT_ON_TARGET")
    public Integer getHomeShotOnTarget() {
        return homeShotOnTarget;
    }

    public void setHomeShotOnTarget(Integer homeShotOnTarget) {
        this.homeShotOnTarget = homeShotOnTarget;
    }

    @Column(name = "AWAY_SHOT_ON_TARGET")
    public Integer getAwayShotOnTarget() {
        return awayShotOnTarget;
    }

    public void setAwayShotOnTarget(Integer awayShotOnTarget) {
        this.awayShotOnTarget = awayShotOnTarget;
    }

    @Column(name = "HOME_FOUL")
    public Integer getHomeFoul() {
        return homeFoul;
    }

    public void setHomeFoul(Integer homeFoul) {
        this.homeFoul = homeFoul;
    }

    @Column(name = "AWAY_FOUL")
    public Integer getAwayFoul() {
        return awayFoul;
    }

    public void setAwayFoul(Integer awayFoul) {
        this.awayFoul = awayFoul;
    }

    @Column(name = "HOME_CORNER")
    public Integer getHomeCorner() {
        return homeCorner;
    }

    public void setHomeCorner(Integer homeCorner) {
        this.homeCorner = homeCorner;
    }

    @Column(name = "AWAY_CORNER")
    public Integer getAwayCorner() {
        return awayCorner;
    }

    public void setAwayCorner(Integer awayCorner) {
        this.awayCorner = awayCorner;
    }

    @Column(name = "HOME_YELLOW")
    public Integer getHomeYellow() {
        return homeYellow;
    }

    public void setHomeYellow(Integer homeYellow) {
        this.homeYellow = homeYellow;
    }

    @Column(name = "AWAY_YELLOW")
    public Integer getAwayYellow() {
        return awayYellow;
    }

    public void setAwayYellow(Integer awayYellow) {
        this.awayYellow = awayYellow;
    }

    @Column(name = "HOME_RED")
    public Integer getHomeRed() {
        return homeRed;
    }

    public void setHomeRed(Integer homeRed) {
        this.homeRed = homeRed;
    }

    @Column(name = "AWAY_RED")
    public Integer getAwayRed() {
        return awayRed;
    }

    public void setAwayRed(Integer awayRed) {
        this.awayRed = awayRed;
    }

    @JoinColumn(name = "FIRST_HALF_ID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    public FirstHalf getFirstHalf() {
        return firstHalf;
    }

    public void setFirstHalf(FirstHalf firstHalf) {
        this.firstHalf = firstHalf;
    }

    @JoinColumn(name = "MATCH_STATUS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    @JoinColumn(name = "SEASON_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    @JoinColumn(name = "LEAGUE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    @JoinColumn(name = "HOME_TEAM_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    @JoinColumn(name = "AWAY_TEAM_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    @JoinColumn(name = "OVER_UNDER_ID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    public OverUnder getOverUnder() {
        return overUnder;
    }

    public void setOverUnder(OverUnder overUnder) {
        this.overUnder = overUnder;
    }

    @JoinColumn(name = "SECOND_HALF_ID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    public SecondHalf getSecondHalf() {
        return secondHalf;
    }

    public void setSecondHalf(SecondHalf secondHalf) {
        this.secondHalf = secondHalf;
    }

    @JoinColumn(name = "DOUBLE_CHANCE_ID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    public DoubleChance getDoubleChance() {
        return doubleChance;
    }

    public void setDoubleChance(DoubleChance doubleChance) {
        this.doubleChance = doubleChance;
    }

    @JoinColumn(name = "SECOND_HALF_GOALS_ID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    public SecondHalfGoals getSecondHalfGoals() {
        return secondHalfGoals;
    }

    public void setSecondHalfGoals(SecondHalfGoals secondHalfGoals) {
        this.secondHalfGoals = secondHalfGoals;
    }

    @JoinColumn(name = "FIRST_HALF_GOALS_ID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    public FirstHalfGoals getFirstHalfGoals() {
        return firstHalfGoals;
    }

    public void setFirstHalfGoals(FirstHalfGoals firstHalfGoals) {
        this.firstHalfGoals = firstHalfGoals;
    }

    @JoinColumn(name = "BTTS_ID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    public Btts getBtts() {
        return btts;
    }

    public void setBtts(Btts btts) {
        this.btts = btts;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Matches other = (Matches) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
