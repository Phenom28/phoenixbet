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
 * Entity for First half Goals prediction
 * Stores and retrieve first half goals data from the database
 * @author Ogundipe Segun David
 */
@Entity
@Table(name = "first_half_goals")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FirstHalfGoals.findAll", query = "SELECT f FROM FirstHalfGoals f")
    , @NamedQuery(name = "FirstHalfGoals.findById", query = "SELECT f FROM FirstHalfGoals f WHERE f.id = :id")})
public class FirstHalfGoals implements Serializable {

    private static final long serialVersionUID = 8822780017183044137L;
    private Integer id;
    private Double over;
    private Double under;
    private String tip;
    private Double odds;
    private Matches match;

    public FirstHalfGoals() {
    }

    public FirstHalfGoals(Integer id) {
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

    @Column(name = "OVER")
    public Double getOver() {
        return over;
    }

    public void setOver(Double over) {
        this.over = over;
    }

    @Column(name = "UNDER")
    public Double getUnder() {
        return under;
    }

    public void setUnder(Double under) {
        this.under = under;
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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "firstHalfGoals")
    public Matches getMatch() {
        return match;
    }

    public void setMatch(Matches match) {
        this.match = match;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final FirstHalfGoals other = (FirstHalfGoals) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
