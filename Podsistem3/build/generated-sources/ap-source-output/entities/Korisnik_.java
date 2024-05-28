package entities;

import entities.Gledanje;
import entities.Ocena;
import entities.Pretplata;
import entities.Videosnimak;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-04T17:25:14")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, String> ime;
    public static volatile SingularAttribute<Korisnik, Integer> iDKor;
    public static volatile ListAttribute<Korisnik, Gledanje> gledanjeList;
    public static volatile ListAttribute<Korisnik, Pretplata> pretplataList;
    public static volatile SingularAttribute<Korisnik, Integer> iDMes;
    public static volatile ListAttribute<Korisnik, Ocena> ocenaList;
    public static volatile SingularAttribute<Korisnik, String> godiste;
    public static volatile SingularAttribute<Korisnik, String> pol;
    public static volatile ListAttribute<Korisnik, Videosnimak> videosnimakList;
    public static volatile SingularAttribute<Korisnik, String> email;

}