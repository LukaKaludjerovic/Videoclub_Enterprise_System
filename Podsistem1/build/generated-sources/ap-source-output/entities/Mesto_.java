package entities;

import entities.Korisnik;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-02T22:01:06")
@StaticMetamodel(Mesto.class)
public class Mesto_ { 

    public static volatile SingularAttribute<Mesto, Integer> iDMes;
    public static volatile SingularAttribute<Mesto, String> naziv;
    public static volatile ListAttribute<Mesto, Korisnik> korisnikList;

}