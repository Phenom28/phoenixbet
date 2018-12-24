package com.phoenixbet.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity for Second Half prediction
 * Stores and retrieve second half data from the database
 * @author Ogundipe Segun David
 */
@Entity
@Table(name = "second_half")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecondHalf.findAll", query = "SELECT s FROM SecondHalf s")
    , @NamedQuery(name = "SecondHalf.findById", query = "SELECT s FROM SecondHalf s WHERE s.id = :id")})
public class SecondHalf implements Serializable {

    private static final long serialVersionUID = -3610551935229031497L;
    private Integer id;
    private Double home;
    private Double draw;
    private Double away;
    private String score;
    private String tip;
    private Double odds;
    private Matches match;

    public SecondHalf() {
    }

    public SecondHalf(Integer id) {
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

    @Column(name = "HOME")
    public Double getHome() {
        return home;
    }

    public void setHome(Double home) {
        this.home = home;
    }

    @Column(name = "DRAW")
    public Double getDraw() {
        return draw;
    }

    public void setDraw(Double draw) {
        this.draw = draw;
    }

    @Column(name = "AWAY")
    public Double getAway() {
        return away;
    }

    public void setAway(Double away) {
        this.away = away;
    }

    @Column(name = "SCORE")
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Column(name = "TIP")
    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Column(name = "ODDS")
    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "secondHalf")
    public Matches getMatch() {
        return match;
    }

    public void setMatch(Matches match) {
        this.match = match;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final SecondHalf other = (SecondHalf) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
