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
 * Entity for Season
 * Stores and retrieve season data from the database
 * @author Ogundipe Segun David
 */
@Entity
@Table(name = "season")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Season.findAll", query = "SELECT s FROM Season s")
    , @NamedQuery(name = "Season.findById", query = "SELECT s FROM Season s WHERE s.id = :id")
    , @NamedQuery(name = "Season.findByYear", query = "SELECT s FROM Season s WHERE s.year = :year")})
public class Season implements Serializable {

    private static final long serialVersionUID = -1046062169103355017L;
    private Integer id;
    private String year;
    private List<Matches> matches;

    public Season() {
    }

    public Season(Integer id) {
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

    @Column(name = "YEAR")
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    @XmlTransient
    public List<Matches> getMatches() {
        return matches;
    }

    public void setMatches(List<Matches> matches) {
        this.matches = matches;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Season other = (Season) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
