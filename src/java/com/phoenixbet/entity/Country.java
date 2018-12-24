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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity for Country
 * Stores and retrieve country data from the database
 * @author Ogundipe Segun David
 */
@Entity
@Table(name = "country")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c")
    , @NamedQuery(name = "Country.findById", query = "SELECT c FROM Country c WHERE c.id = :id")
    , @NamedQuery(name = "Country.findByName", query = "SELECT c FROM Country c WHERE c.name = :name")})
public class Country implements Serializable {

    private static final long serialVersionUID = 625694140982272471L;
    private Integer id;
    private String name;
    private String img;
    private byte[] imgSrc;
    private List<League> leagues;

    public Country() {
    }

    public Country(Integer id) {
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    @XmlTransient
    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }
    
    public void addLeague(League league) {
        if (leagues == null) {
            leagues = new ArrayList<>();
        }
        if (leagues != null && !getLeagues().contains(league)) {
            this.getLeagues().add(league);
        }
    }

    public void removeLeague(League league) {
        if (leagues != null && !leagues.isEmpty() && getLeagues().contains(league)) {
            this.getLeagues().remove(league);
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Country other = (Country) obj;
        return Objects.equals(this.id, other.id);
    }


    @Override
    public String toString() {
        return id.toString();
    }
    
}
