/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kalud
 */
@Entity
@Table(name = "videosnimak")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Videosnimak.findAll", query = "SELECT v FROM Videosnimak v"),
    @NamedQuery(name = "Videosnimak.findByIDVid", query = "SELECT v FROM Videosnimak v WHERE v.iDVid = :iDVid"),
    @NamedQuery(name = "Videosnimak.findByNaziv", query = "SELECT v FROM Videosnimak v WHERE v.naziv = :naziv"),
    @NamedQuery(name = "Videosnimak.findByTrajanje", query = "SELECT v FROM Videosnimak v WHERE v.trajanje = :trajanje"),
    @NamedQuery(name = "Videosnimak.findByDatumVreme", query = "SELECT v FROM Videosnimak v WHERE v.datumVreme = :datumVreme")})
public class Videosnimak implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDVid")
    private Integer iDVid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Trajanje")
    private int trajanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;
    @JoinColumn(name = "Vlasnik", referencedColumnName = "IDKor")
    @ManyToOne(optional = false)
    private Korisnik vlasnik;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDVid")
    private List<Gledanje> gledanjeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDVid")
    private List<Ocena> ocenaList;

    public Videosnimak() {
    }

    public Videosnimak(Integer iDVid) {
        this.iDVid = iDVid;
    }

    public Videosnimak(Integer iDVid, String naziv, int trajanje, Date datumVreme) {
        this.iDVid = iDVid;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.datumVreme = datumVreme;
    }

    public Integer getIDVid() {
        return iDVid;
    }

    public void setIDVid(Integer iDVid) {
        this.iDVid = iDVid;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
    }

    public Korisnik getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Korisnik vlasnik) {
        this.vlasnik = vlasnik;
    }

    @XmlTransient
    public List<Gledanje> getGledanjeList() {
        return gledanjeList;
    }

    public void setGledanjeList(List<Gledanje> gledanjeList) {
        this.gledanjeList = gledanjeList;
    }

    @XmlTransient
    public List<Ocena> getOcenaList() {
        return ocenaList;
    }

    public void setOcenaList(List<Ocena> ocenaList) {
        this.ocenaList = ocenaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDVid != null ? iDVid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Videosnimak)) {
            return false;
        }
        Videosnimak other = (Videosnimak) object;
        if ((this.iDVid == null && other.iDVid != null) || (this.iDVid != null && !this.iDVid.equals(other.iDVid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Videosnimak[ iDVid=" + iDVid + " ]";
    }
    
}
