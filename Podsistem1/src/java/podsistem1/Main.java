/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import entities.Korisnik;
import entities.Mesto;
import java.util.List;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author kalud
 */
public class Main {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "RequestQueue")
    private static Queue RequestQueue;
    
    @Resource(lookup = "ResponseQueue")
    private static Queue ResponseQueue;
    
    @Resource(lookup = "QueueP1P2")
    private static Queue QueueP1P2;
    
    @Resource(lookup = "QueueP1P3")
    private static Queue QueueP1P3;
    
    public static void main(String[] args) {
        
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
            EntityManager em = emf.createEntityManager();
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(RequestQueue);
            JMSProducer producer = context.createProducer();
            
            while(true){
                Message msg = consumer.receive();
                TextMessage textMsg = (TextMessage)msg;
                String text = textMsg.getText();

                switch(text){
                    case "Dohvatanje svih mesta":
                        List<Mesto> mesta = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
                        StringBuilder sb = new StringBuilder();
                        for(Mesto m : mesta){
                            sb.append("IDMes: ");
                            sb.append(m.getIDMes());
                            sb.append(", Naziv: ");
                            sb.append(m.getNaziv());
                            sb.append("\n");
                        }
                        TextMessage txtMsgMesta = context.createTextMessage(sb.toString());
                        txtMsgMesta.setStringProperty("Broj", "17");
                        producer.send(ResponseQueue, txtMsgMesta);
                        break;

                    case "Dohvatanje svih korisnika":
                        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findAll", Korisnik.class).getResultList();
                        sb = new StringBuilder();
                        for(Korisnik k : korisnici){
                            sb.append("IDKor: ");
                            sb.append(k.getIDKor());
                            sb.append(", Ime: ");
                            sb.append(k.getIme());
                            sb.append(", Email: ");
                            sb.append(k.getEmail());
                            sb.append(", Godiste: ");
                            sb.append(k.getGodiste());
                            sb.append(", Pol: ");
                            sb.append(k.getPol());
                            sb.append(", IDMes: ");
                            sb.append(k.getIDMes().getIDMes());
                            sb.append("\n");
                        }
                        TextMessage txtMsgKorisnici = context.createTextMessage(sb.toString());
                        txtMsgKorisnici.setStringProperty("Broj", "18");
                        producer.send(ResponseQueue, txtMsgKorisnici);
                        break;

                    case "Promena email adrese za korisnika":
                        int idkor = textMsg.getIntProperty("IDKor");
                        String email = textMsg.getStringProperty("Email");
                        List<Korisnik> korisnici2 = em.createNamedQuery("Korisnik.findByIDKor", Korisnik.class).setParameter("iDKor", idkor).getResultList();
                        if(korisnici2.isEmpty()){
                            TextMessage txtEmail = context.createTextMessage("Ne postoji korisnik sa ID " + idkor);
                            txtEmail.setStringProperty("Broj", "3");
                            producer.send(ResponseQueue, txtEmail);
                            continue;
                        }
                        Korisnik korisnik2 = korisnici2.get(0);
                        korisnik2.setEmail(email);
                        try{
                            em.getTransaction().begin();
                            em.persist(korisnik2);
                            em.getTransaction().commit();
                        }
                        finally{
                            if(em.getTransaction().isActive())
                                em.getTransaction().rollback();
                        }
                        TextMessage txtEmail = context.createTextMessage("Novi email korisnika " + idkor + " je " + email);
                        txtEmail.setStringProperty("Broj", "3");
                        producer.send(ResponseQueue, txtEmail);
                        TextMessage txtEmail2 = context.createTextMessage("Promena email adrese za korisnika");
                        txtEmail2.setIntProperty("IDKor", idkor);
                        txtEmail2.setStringProperty("Email", email);
                        producer.send(QueueP1P2, txtEmail2);
                        producer.send(QueueP1P3, txtEmail2);
                        break;

                    case "Promena mesta za korisnika":
                        int idkor2 = textMsg.getIntProperty("IDKor");
                        int idmes = textMsg.getIntProperty("IDMes");
                        List<Korisnik> korisnici3 = em.createNamedQuery("Korisnik.findByIDKor", Korisnik.class).setParameter("iDKor", idkor2).getResultList();
                        List<Mesto> mesta2 = em.createNamedQuery("Mesto.findByIDMes", Mesto.class).setParameter("iDMes", idmes).getResultList();
                        if(korisnici3.isEmpty()){
                            TextMessage txtPromenaMesto = context.createTextMessage("Ne postoji korisnik sa ID " + idkor2);
                            txtPromenaMesto.setStringProperty("Broj", "4");
                            producer.send(ResponseQueue, txtPromenaMesto);
                            continue;
                        }
                        if(mesta2.isEmpty()){
                            TextMessage txtPromenaMesto = context.createTextMessage("Ne postoji mesto sa ID " + idmes);
                            txtPromenaMesto.setStringProperty("Broj", "4");
                            producer.send(ResponseQueue, txtPromenaMesto);
                            continue;
                        }
                        Korisnik korisnik3 = korisnici3.get(0);
                        Mesto mesto2 = mesta2.get(0);
                        korisnik3.setIDMes(mesto2);
                        try{
                            em.getTransaction().begin();
                            em.persist(korisnik3);
                            em.getTransaction().commit();
                        }
                        finally{
                            if(em.getTransaction().isActive())
                                em.getTransaction().rollback();
                        }
                        TextMessage txtPromenaMesto = context.createTextMessage("Novo mesto korisnika " + idkor2 + " je " + idmes);
                        txtPromenaMesto.setStringProperty("Broj", "4");
                        producer.send(ResponseQueue, txtPromenaMesto);
                        producer.send(QueueP1P2, textMsg);
                        producer.send(QueueP1P3, textMsg);
                        break;

                    case "Kreiranje grada":
                        String naziv = textMsg.getStringProperty("Naziv");
                        Mesto mesto = new Mesto();
                        mesto.setNaziv(naziv);
                        try{
                            em.getTransaction().begin();
                        em.persist(mesto);
                        em.getTransaction().commit();
                        }
                        finally{
                            if(em.getTransaction().isActive())
                                em.getTransaction().rollback();
                        }
                        TextMessage txtMesto = context.createTextMessage("Kreirano mesto " + naziv);
                        txtMesto.setStringProperty("Broj", "1");
                        producer.send(ResponseQueue, txtMesto);
                        break;

                    case "Kreiranje korisnika":
                        String ime = textMsg.getStringProperty("Ime");
                        String email2 = textMsg.getStringProperty("Email");
                        String godiste = textMsg.getStringProperty("Godiste");
                        String pol = textMsg.getStringProperty("Pol");
                        int idmes2 = textMsg.getIntProperty("IDMes");
                        Korisnik korisnik = new Korisnik();
                        korisnik.setEmail(email2);
                        korisnik.setGodiste(godiste);
                        korisnik.setIme(ime);
                        korisnik.setPol(pol);
                        List<Mesto> mesta3 = em.createNamedQuery("Mesto.findByIDMes", Mesto.class).setParameter("iDMes", idmes2).getResultList();
                        if(mesta3.isEmpty()){
                            TextMessage txtKorisnik = context.createTextMessage("Ne postoji zadato mesto");
                            txtKorisnik.setStringProperty("Broj", "2");
                            producer.send(ResponseQueue, txtKorisnik);
                            continue;
                        }
                        Mesto mesto3 = mesta3.get(0);
                        korisnik.setIDMes(mesto3);
                        try{
                            em.getTransaction().begin();
                            em.persist(korisnik);
                            em.getTransaction().commit();
                        }
                        finally{
                            if(em.getTransaction().isActive())
                                em.getTransaction().rollback();
                        }
                        TextMessage txtKorisnik = context.createTextMessage("Kreiranje korisnika");
                        txtKorisnik.setStringProperty("Ime", korisnik.getIme());
                        txtKorisnik.setStringProperty("Email", korisnik.getEmail());
                        txtKorisnik.setStringProperty("Godiste", korisnik.getGodiste());
                        txtKorisnik.setStringProperty("Pol", korisnik.getPol());
                        txtKorisnik.setIntProperty("IDMes", idmes2);
                        producer.send(QueueP1P2, txtKorisnik);
                        producer.send(QueueP1P3, txtKorisnik);
                        txtKorisnik = context.createTextMessage("Kreiran korisnik " + ime);
                        txtKorisnik.setStringProperty("Broj", "2");
                        producer.send(ResponseQueue, txtKorisnik);
                        break;
                }
            }
        }
        catch(Exception e){}
    }
}
