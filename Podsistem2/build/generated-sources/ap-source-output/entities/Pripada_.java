package entities;

import entities.Kategorija;
import entities.Videosnimak;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-04T02:32:46")
@StaticMetamodel(Pripada.class)
public class Pripada_ { 

    public static volatile SingularAttribute<Pripada, Videosnimak> iDVid;
    public static volatile SingularAttribute<Pripada, Kategorija> iDKat;
    public static volatile SingularAttribute<Pripada, Integer> iDPri;

}