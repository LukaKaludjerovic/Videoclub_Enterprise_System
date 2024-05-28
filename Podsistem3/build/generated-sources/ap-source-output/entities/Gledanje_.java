package entities;

import entities.Korisnik;
import entities.Videosnimak;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-04T17:25:14")
@StaticMetamodel(Gledanje.class)
public class Gledanje_ { 

    public static volatile SingularAttribute<Gledanje, Videosnimak> iDVid;
    public static volatile SingularAttribute<Gledanje, Integer> iDGle;
    public static volatile SingularAttribute<Gledanje, Date> datumVreme;
    public static volatile SingularAttribute<Gledanje, Integer> odgledano;
    public static volatile SingularAttribute<Gledanje, Korisnik> iDKor;
    public static volatile SingularAttribute<Gledanje, Integer> pocetak;

}