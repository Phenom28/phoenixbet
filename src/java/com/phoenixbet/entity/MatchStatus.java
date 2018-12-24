package com.phoenixbet.entity;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity for Match Status
 * Stores and retrieve league data from the database
 * @author Ogundipe Segun David
 */
@Entity
@Table(name = "match_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MatchStatus.findAll", query = "SELECT m FROM MatchStatus m")
    , @NamedQuery(name = "MatchStatus.findById", query = "SELECT m FROM MatchStatus m WHERE m.id = :id")
    , @NamedQuery(name = "MatchStatus.findByStatus", query = "SELECT m FROM MatchStatus m WHERE m.status = :status")})
public class MatchStatus implements Serializable {

    private static final long serialVersionUID = 1974064210910437107L;
    private Integer id;
    private String status;
    private List<Matches> matches;

    public MatchStatus() {
    }

    public MatchStatus(Integer id) {
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

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matchStatus")
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
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final MatchStatus other = (MatchStatus) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
