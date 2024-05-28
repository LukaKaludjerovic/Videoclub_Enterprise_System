package entities;

import entities.Korisnik;
import entities.Videosnimak;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-04T17:25:14")
@StaticMetamodel(Ocena.class)
public class Ocena_ { 

    public static volatile SingularAttribute<Ocena, Integer> iDOce;
    public static volatile SingularAttribute<Ocena, Videosnimak> iDVid;
    public static volatile SingularAttribute<Ocena, Date> datumVreme;
    public static volatile SingularAttribute<Ocena, Korisnik> iDKor;
    public static volatile SingularAttribute<Ocena, Integer> ocena;

}