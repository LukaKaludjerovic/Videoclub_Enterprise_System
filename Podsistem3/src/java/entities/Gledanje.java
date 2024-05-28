/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kalud
 */
@Entity
@Table(name = "gledanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gledanje.findAll", query = "SELECT g FROM Gledanje g"),
    @NamedQuery(name = "Gledanje.findByIDGle", query = "SELECT g FROM Gledanje g WHERE g.iDGle = :iDGle"),
    @NamedQuery(name = "Gledanje.findByDatumVreme", query = "SELECT g FROM Gledanje g WHERE g.datumVreme = :datumVreme"),
    @NamedQuery(name = "Gledanje.findByPocetak", query = "SELECT g FROM Gledanje g WHERE g.pocetak = :pocetak"),
    @NamedQuery(name = "Gledanje.findByOdgledano", query = "SELECT g FROM Gledanje g WHERE g.odgledano = :odgledano")})
public class Gledanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDGle")
    private Integer iDGle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pocetak")
    private int pocetak;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Odgledano")
    private int odgledano;
    @JoinColumn(name = "IDKor", referencedColumnName = "IDKor")
    @ManyToOne(optional = false)
    private Korisnik iDKor;
    @JoinColumn(name = "IDVid", referencedColumnName = "IDVid")
    @ManyToOne(optional = false)
    private Videosnimak iDVid;

    public Gledanje() {
    }

    public Gledanje(Integer iDGle) {
        this.iDGle = iDGle;
    }

    public Gledanje(Integer iDGle, Date datumVreme, int pocetak, int odgledano) {
        this.iDGle = iDGle;
        this.datumVreme = datumVreme;
        this.pocetak = pocetak;
        this.odgledano = odgledano;
    }

    public Integer getIDGle() {
        return iDGle;
    }

    public void setIDGle(Integer iDGle) {
        this.iDGle = iDGle;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
    }

    public int getPocetak() {
        return pocetak;
    }

    public void setPocetak(int pocetak) {
        this.pocetak = pocetak;
    }

    public int getOdgledano() {
        return odgledano;
    }

    public void setOdgledano(int odgledano) {
        this.odgledano = odgledano;
    }

    public Korisnik getIDKor() {
        return iDKor;
    }

    public void setIDKor(Korisnik iDKor) {
        this.iDKor = iDKor;
    }

    public Videosnimak getIDVid() {
        return iDVid;
    }

    public void setIDVid(Videosnimak iDVid) {
        this.iDVid = iDVid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDGle != null ? iDGle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gledanje)) {
            return false;
        }
        Gledanje other = (Gledanje) object;
        if ((this.iDGle == null && other.iDGle != null) || (this.iDGle != null && !this.iDGle.equals(other.iDGle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Gledanje[ iDGle=" + iDGle + " ]";
    }
    
}
