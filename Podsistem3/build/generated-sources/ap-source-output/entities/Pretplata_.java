package entities;

import entities.Korisnik;
import entities.Paket;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-04T17:25:14")
@StaticMetamodel(Pretplata.class)
public class Pretplata_ { 

    public static volatile SingularAttribute<Pretplata, Date> datumVreme;
    public static volatile SingularAttribute<Pretplata, Korisnik> iDKor;
    public static volatile SingularAttribute<Pretplata, Paket> iDPak;
    public static volatile SingularAttribute<Pretplata, Integer> cena;
    public static volatile SingularAttribute<Pretplata, Integer> iDPre;

}