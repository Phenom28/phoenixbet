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
 * Entity for Over Under prediction
 * Stores and retrieve over under data from the database
 * @author Ogundipe Segun David
 */
@Entity
@Table(name = "over_under")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OverUnder.findAll", query = "SELECT o FROM OverUnder o")
    , @NamedQuery(name = "OverUnder.findById", query = "SELECT o FROM OverUnder o WHERE o.id = :id")})
public class OverUnder implements Serializable {

    private static final long serialVersionUID = 4345070142355392240L;
    private Integer id;
    private Double over;
    private Double under;
    private String tip;
    private Double odds;
    private Matches match;

    public OverUnder() {
    }

    public OverUnder(Integer id) {
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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "overUnder")
    public Matches getMatch() {
        return match;
    }

    public void setMatch(Matches match) {
        this.match = match;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final OverUnder other = (OverUnder) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
