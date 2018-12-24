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
 * Entity for Both Teams To Score prediction
 * Stores and retrieve btts data from the database
 * @author Ogundipe Segun David
 */
@Entity
@Table(name = "btts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Btts.findAll", query = "SELECT b FROM Btts b")
    , @NamedQuery(name = "Btts.findById", query = "SELECT b FROM Btts b WHERE b.id = :id")
    , @NamedQuery(name = "Btts.findByTip", query = "SELECT b FROM Btts b WHERE b.tip = :tip")})
public class Btts implements Serializable {

    private static final long serialVersionUID = -6986951495342211926L;
    private Integer id;
    private Double yes;
    private Double no;
    private String tip;
    private Double odds;
    private Matches match;

    public Btts() {
    }

    public Btts(Integer id) {
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

    @Column(name = "GG")
    public Double getYes() {
        return yes;
    }

    public void setYes(Double yes) {
        this.yes = yes;
    }

    @Column(name = "NG")
    public Double getNo() {
        return no;
    }

    public void setNo(Double no) {
        this.no = no;
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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "btts")
    public Matches getMatch() {
        return match;
    }

    public void setMatch(Matches match) {
        this.match = match;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        final Btts other = (Btts) obj;
        return Objects.equals(this.id, other.id);
    }


    @Override
    public String toString() {
        return id.toString();
    }
    
}
