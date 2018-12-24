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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity for Team
 * Stores and retrieve team data from the database
 * @author Ogundipe Segun David
 */
@Entity
@Table(name = "team")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t")
    , @NamedQuery(name = "Team.findById", query = "SELECT t FROM Team t WHERE t.id = :id")
    , @NamedQuery(name = "Team.findByName", query = "SELECT t FROM Team t WHERE t.name = :name")})
public class Team implements Serializable {

    private static final long serialVersionUID = 8290403388910300000L;
    private Integer id;
    private String name;
    private String img;
    private byte[] imgSrc;
    private List<League> leagues;
    private List<Matches> homeMatches;
    private List<Matches> AwayMatches;

    public Team() {
    }

    public Team(Integer id) {
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

    @Column(name = "IMG")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Lob
    @Column(name = "IMG_SRC")
    public byte[] getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(byte[] imgSrc) {
        this.imgSrc = imgSrc;
    }

    @ManyToMany(mappedBy = "teams")
    @XmlTransient
    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }
    
    public void addToLeague(League league){
        if (leagues == null) {
            leagues = new ArrayList<>();
        }
        
        if (leagues != null && !getLeagues().contains(league)) {
            getLeagues().add(league);
        }
    }
    
    public void removeFromLeague(League league){
        if (leagues != null && !leagues.isEmpty() && getLeagues().contains(league)) {
            getLeagues().remove(league);
        }
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "homeTeam")
    @XmlTransient
    public List<Matches> getHomeMatches() {
        return homeMatches;
    }

    public void setHomeMatches(List<Matches> homeMatches) {
        this.homeMatches = homeMatches;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "awayTeam")
    @XmlTransient
    public List<Matches> getAwayMatches() {
        return AwayMatches;
    }

    public void setAwayMatches(List<Matches> awayMatches) {
        this.AwayMatches = awayMatches;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Team other = (Team) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
