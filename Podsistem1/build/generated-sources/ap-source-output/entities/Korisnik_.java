package entities;

import entities.Mesto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-02T22:01:06")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, String> ime;
    public static volatile SingularAttribute<Korisnik, Integer> iDKor;
    public static volatile SingularAttribute<Korisnik, Mesto> iDMes;
    public static volatile SingularAttribute<Korisnik, String> godiste;
    public static volatile SingularAttribute<Korisnik, String> pol;
    public static volatile SingularAttribute<Korisnik, String> email;

}