package entities;

import entities.Korisnik;
import entities.Pripada;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-04T02:32:46")
@StaticMetamodel(Videosnimak.class)
public class Videosnimak_ { 

    public static volatile ListAttribute<Videosnimak, Pripada> pripadaList;
    public static volatile SingularAttribute<Videosnimak, Integer> iDVid;
    public static volatile SingularAttribute<Videosnimak, Date> datumVreme;
    public static volatile SingularAttribute<Videosnimak, Integer> trajanje;
    public static volatile SingularAttribute<Videosnimak, String> naziv;
    public static volatile SingularAttribute<Videosnimak, Korisnik> vlasnik;

}