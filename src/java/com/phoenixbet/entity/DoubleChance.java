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
 * Entity for Double Chance prediction
 * Stores and retrieve double chance data from the database
 * @author Ogundipe Segun David
 */
@Entity
@Table(name = "double_chance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DoubleChance.findAll", query = "SELECT d FROM DoubleChance d")
    , @NamedQuery(name = "DoubleChance.findById", query = "SELECT d FROM DoubleChance d WHERE d.id = :id")})
public class DoubleChance implements Serializable {

    private static final long serialVersionUID = 6312260736245656243L;
    private Integer id;
    private Double oneX;
    private Double oneTwo;
    private Double xTwo;
    private String tip;
    private Double odds;
    private Matches match;

    public DoubleChance() {
    }

    public DoubleChance(Integer id) {
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

    @Column(name = "ONE_X")
    public Double getOneX() {
        return oneX;
    }

    public void setOneX(Double oneX) {
        this.oneX = oneX;
    }

    @Column(name = "ONE_TWO")
    public Double getOneTwo() {
        return oneTwo;
    }

    public void setOneTwo(Double oneTwo) {
        this.oneTwo = oneTwo;
    }

    @Column(name = "X_TWO")
    public Double getXTwo() {
        return xTwo;
    }

    public void setXTwo(Double xTwo) {
        this.xTwo = xTwo;
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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "doubleChance")
    public Matches getMatch() {
        return match;
    }

    public void setMatch(Matches match) {
        this.match = match;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final DoubleChance other = (DoubleChance) obj;
        return Objects.equals(this.id, other.id);
    }
    

    @Override
    public String toString() {
        return id.toString();
    }
    
}
