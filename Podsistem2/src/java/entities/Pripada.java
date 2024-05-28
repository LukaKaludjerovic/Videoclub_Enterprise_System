/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kalud
 */
@Entity
@Table(name = "pripada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pripada.findAll", query = "SELECT p FROM Pripada p"),
    @NamedQuery(name = "Pripada.findByIDPri", query = "SELECT p FROM Pripada p WHERE p.iDPri = :iDPri")})
public class Pripada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDPri")
    private Integer iDPri;
    @JoinColumn(name = "IDKat", referencedColumnName = "IDKat")
    @ManyToOne(optional = false)
    private Kategorija iDKat;
    @JoinColumn(name = "IDVid", referencedColumnName = "IDVid")
    @ManyToOne(optional = false)
    private Videosnimak iDVid;

    public Pripada() {
    }

    public Pripada(Integer iDPri) {
        this.iDPri = iDPri;
    }

    public Integer getIDPri() {
        return iDPri;
    }

    public void setIDPri(Integer iDPri) {
        this.iDPri = iDPri;
    }

    public Kategorija getIDKat() {
        return iDKat;
    }

    public void setIDKat(Kategorija iDKat) {
        this.iDKat = iDKat;
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
        hash += (iDPri != null ? iDPri.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pripada)) {
            return false;
        }
        Pripada other = (Pripada) object;
        if ((this.iDPri == null && other.iDPri != null) || (this.iDPri != null && !this.iDPri.equals(other.iDPri))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pripada[ iDPri=" + iDPri + " ]";
    }
    
}
