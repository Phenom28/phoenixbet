package com.phoenixbet.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity for League
 * Stores and retrieve league data from the database
 * @author Ogundipe Segun David
 */
@Entity
@Table(name = "league")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "League.findAll", query = "SELECT l FROM League l")
    , @NamedQuery(name = "League.findById", query = "SELECT l FROM League l WHERE l.id = :id")
    , @NamedQuery(name = "League.findByName", query = "SELECT l FROM League l WHERE l.name = :name")})
public class League implements Serializable {

    private static final long serialVersionUID = -6203570549573859284L;    
    private Integer id;
    private String name;
    private String leagueAbbre;
    private List<Team> teams;
    private Country country;
    private List<Matches> matches;

    public League() {
    }

    public League(Integer id) {
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
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JoinTable(name = "team_in_league", joinColumns = {
        @JoinColumn(name = "LEAGUE_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID")})
    @ManyToMany
    @XmlTransient
    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
    
    public void addTeam(Team team){
        if (teams == null) {
            teams = new ArrayList<>();
        }
        
        if (teams != null && !getTeams().contains(team)) {
            getTeams().add(team);
        }
    }
    
    public void removeTeam(Team team){
        if (teams != null && !teams.isEmpty() && getTeams().contains(team)) {
            getTeams().remove(team);
        }
    }

    @Column(name = "LEAGUEABBR")
    public String getLeagueAbbre() {
        return leagueAbbre;
    }

    public void setLeagueAbbre(String leagueAbbre) {
        this.leagueAbbre = leagueAbbre;
    }
    

    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "league")
    @XmlTransient
    public List<Matches> getMatches() {
        return matches;
    }

    public void setMatches(List<Matches> matches) {
        this.matches = matches;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final League other = (League) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return id.toString();
    }
    
}
