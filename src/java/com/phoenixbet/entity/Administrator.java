package com.phoenixbet.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity for Administrator
 * Stores and retrieve administrator data from the database
 * @author Ogundipe Segun David
 */
@Entity
@Table(name = "administrator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Administrator.findAll", query = "SELECT a FROM Administrator a")
    , @NamedQuery(name = "Administrator.findById", query = "SELECT a FROM Administrator a WHERE a.id = :id")
    , @NamedQuery(name = "Administrator.findByUserName", query = "SELECT a FROM Administrator a WHERE a.userName = :userName")})
public class Administrator implements Serializable {

    private static final long serialVersionUID = 558395280186731462L;
    private Integer id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private Date dateCreated;
    private List<Groups> groups;

    public Administrator() {
    }

    public Administrator(Integer id) {
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
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic(optional = false)
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic(optional = false)
    @Column(name = "USER_NAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic(optional = false)
    @Column(name = "PASSWORD")
    @XmlTransient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic(optional = false)
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @XmlTransient
    @JoinTable(name = "administrator_group", joinColumns = {
        @JoinColumn(name = "ADMINISTRATOR_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID")})
    @ManyToMany
    public List<Groups> getGroups() {
        return groups;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }
    
    public void addToGroup(Groups group) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        if (groups != null && !getGroups().contains(group)) {
            this.getGroups().add(group);
        }
    }

    public void removeFromGroup(Groups group) {
        if (groups != null && !groups.isEmpty() && getGroups().contains(group)) {
            this.getGroups().remove(group);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final Administrator other = (Administrator) obj;
        return Objects.equals(this.id, other.id);
    }


    @Override
    public String toString() {
        return id.toString();
    }
    
}
