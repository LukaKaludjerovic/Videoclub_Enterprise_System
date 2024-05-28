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
@Table(name = "ocena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocena.findAll", query = "SELECT o FROM Ocena o"),
    @NamedQuery(name = "Ocena.findByIDOce", query = "SELECT o FROM Ocena o WHERE o.iDOce = :iDOce"),
    @NamedQuery(name = "Ocena.findByOcena", query = "SELECT o FROM Ocena o WHERE o.ocena = :ocena"),
    @NamedQuery(name = "Ocena.findByDatumVreme", query = "SELECT o FROM Ocena o WHERE o.datumVreme = :datumVreme")})
public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDOce")
    private Integer iDOce;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Ocena")
    private int ocena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;
    @JoinColumn(name = "IDKor", referencedColumnName = "IDKor")
    @ManyToOne(optional = false)
    private Korisnik iDKor;
    @JoinColumn(name = "IDVid", referencedColumnName = "IDVid")
    @ManyToOne(optional = false)
    private Videosnimak iDVid;

    public Ocena() {
    }

    public Ocena(Integer iDOce) {
        this.iDOce = iDOce;
    }

    public Ocena(Integer iDOce, int ocena, Date datumVreme) {
        this.iDOce = iDOce;
        this.ocena = ocena;
        this.datumVreme = datumVreme;
    }

    public Integer getIDOce() {
        return iDOce;
    }

    public void setIDOce(Integer iDOce) {
        this.iDOce = iDOce;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
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
        hash += (iDOce != null ? iDOce.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocena)) {
            return false;
        }
        Ocena other = (Ocena) object;
        if ((this.iDOce == null && other.iDOce != null) || (this.iDOce != null && !this.iDOce.equals(other.iDOce))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ocena[ iDOce=" + iDOce + " ]";
    }
    
}
