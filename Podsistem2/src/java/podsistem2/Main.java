/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import entities.Kategorija;
import entities.Korisnik;
import entities.Pripada;
import entities.Videosnimak;
import java.util.Date;
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
    
    @Resource(lookup = "RequestQueue2")
    private static Queue RequestQueue2;
    
    @Resource(lookup = "ResponseQueue")
    private static Queue ResponseQueue;
    
    @Resource(lookup = "QueueP1P2")
    private static Queue QueueP1P2;
    
    @Resource(lookup = "QueueP1P3")
    private static Queue QueueP1P3;
    
    @Resource(lookup = "QueueP2P3")
    private static Queue QueueP2P3;
    
    public static void main(String[] args) {
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
            EntityManager em = emf.createEntityManager();
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(RequestQueue2);
            JMSConsumer consumerP1P2 = context.createConsumer(QueueP1P2);
            JMSProducer producer = context.createProducer();
            
            while(true){
                Message msg = consumer.receive(100);
                Message msgP1P2 = consumerP1P2.receive(100);
                
                if(msg != null && msgP1P2 == null){
                    TextMessage textMsg = (TextMessage)msg;
                    String text = textMsg.getText();

                    switch(text){
                        case "Dohvatanje svih kategorija":
                            List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findAll", Kategorija.class).getResultList();
                            StringBuilder sb = new StringBuilder();
                            for(Kategorija k : kategorije){
                                sb.append("IDKat: ");
                                sb.append(k.getIDKat());
                                sb.append(", Naziv: ");
                                sb.append(k.getNaziv());
                                sb.append("\n");
                            }
                            TextMessage txtMsgKategorije = context.createTextMessage(sb.toString());
                            txtMsgKategorije.setStringProperty("Broj", "19");
                            producer.send(ResponseQueue, txtMsgKategorije);
                            break;

                        case "Dohvatanje svih video snimaka":
                            List<Videosnimak> snimci = em.createNamedQuery("Videosnimak.findAll", Videosnimak.class).getResultList();
                            sb = new StringBuilder();
                            for(Videosnimak v : snimci){
                                sb.append("IDVid: ");
                                sb.append(v.getIDVid());
                                sb.append(", Naziv: ");
                                sb.append(v.getNaziv());
                                sb.append(", Trajanje: ");
                                sb.append(v.getTrajanje());
                                sb.append(", Vlasnik: ");
                                sb.append(v.getVlasnik().getIDKor());
                                sb.append(", DatumVreme: ");
                                sb.append(v.getDatumVreme());
                                sb.append("\n");
                            }
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            txtMsg.setStringProperty("Broj", "20");
                            producer.send(ResponseQueue, txtMsg);
                            break;

                        case "Dohvatanje kategorija za odredjeni video snimak":
                            int idvid = textMsg.getIntProperty("IDVid");
                            List<Videosnimak> snimci2 = em.createNamedQuery("Videosnimak.findByIDVid", Videosnimak.class).setParameter("iDVid", idvid).getResultList();
                            if(snimci2.isEmpty()){
                                TextMessage txtMsg2 = context.createTextMessage("Ne postoji video snimak sa ID " + idvid);
                                txtMsg2.setStringProperty("Broj", "21");
                                producer.send(ResponseQueue, txtMsg2);
                                continue;
                            }
                            List<Kategorija> kategorijeZaVideo = em.createQuery("SELECT p.iDKat FROM Pripada p WHERE p.iDVid.iDVid = :iDVid", Kategorija.class).setParameter("iDVid", idvid).getResultList();
                            sb = new StringBuilder();
                            for(Kategorija k : kategorijeZaVideo){
                                sb.append("IDKat: ");
                                sb.append(k.getIDKat());
                                sb.append(", Naziv: ");
                                sb.append(k.getNaziv());
                                sb.append("\n");
                            }
                            TextMessage txtMsg2 = context.createTextMessage(sb.toString());
                            txtMsg2.setStringProperty("Broj", "21");
                            producer.send(ResponseQueue, txtMsg2);
                            break;

                        case "Promena naziva video snimka":
                            int idvid2 = textMsg.getIntProperty("IDVid");
                            String naziv = textMsg.getStringProperty("Naziv");
                            List<Videosnimak> videosnimci = em.createNamedQuery("Videosnimak.findByIDVid", Videosnimak.class).setParameter("iDVid", idvid2).getResultList();
                            if(videosnimci.isEmpty()){
                                TextMessage txtNaziv = context.createTextMessage("Ne postoji video snimak sa ID " + idvid2);
                                txtNaziv.setStringProperty("Broj", "7");
                                producer.send(ResponseQueue, txtNaziv);
                                continue;
                            }
                            Videosnimak videosnimak = videosnimci.get(0);
                            videosnimak.setNaziv(naziv);
                            try{
                                em.getTransaction().begin();
                                em.persist(videosnimak);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            TextMessage txtNaziv = context.createTextMessage("Novi naziv video snimka " + idvid2 + " je " + naziv);
                            txtNaziv.setStringProperty("Broj", "7");
                            producer.send(ResponseQueue, txtNaziv);
                            producer.send(QueueP2P3, textMsg);
                            break;

                        case "Brisanje video snimka od strane korisnika koji ga je kreirao":
                            int idkor3 = textMsg.getIntProperty("IDKor");
                            List<Korisnik> korisnici3 = em.createNamedQuery("Korisnik.findByIDKor", Korisnik.class).setParameter("iDKor", idkor3).getResultList();
                            if(korisnici3.isEmpty()){
                                TextMessage txtMsg3 = context.createTextMessage("Ne postoji korisnik ID " + idkor3);
                                txtMsg3.setStringProperty("Broj", "16");
                                producer.send(ResponseQueue, txtMsg3);
                                continue;
                            }
                            int idvid3 = textMsg.getIntProperty("IDVid");
                            List<Videosnimak> snimci3 = em.createNamedQuery("Videosnimak.findByIDVid", Videosnimak.class).setParameter("iDVid", idvid3).getResultList();
                            if(snimci3.isEmpty()){
                                TextMessage txtMsg3 = context.createTextMessage("Ne postoji video snimak  ID " + idvid3);
                                txtMsg3.setStringProperty("Broj", "16");
                                producer.send(ResponseQueue, txtMsg3);
                                continue;
                            }
                            Videosnimak snimak3 = snimci3.get(0);
                            if(!snimak3.getVlasnik().getIDKor().equals(idkor3)){
                                TextMessage txtMsg3 = context.createTextMessage("Samo vlasnik moze obrisati video snimak");
                                txtMsg3.setStringProperty("Broj", "16");
                                producer.send(ResponseQueue, txtMsg3);
                                continue;
                            }
                            try{
                                em.getTransaction().begin();
                                em.remove(snimak3);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            TextMessage txtMsg3 = context.createTextMessage("Obrisan video snimak " + idvid3);
                            txtMsg3.setStringProperty("Broj", "16");
                            producer.send(ResponseQueue, txtMsg3);
                            producer.send(QueueP2P3, textMsg);
                            List<Pripada> pripadanja = em.createQuery("SELECT p FROM Pripada p WHERE p.iDVid.iDVid = :iDVid", Pripada.class).setParameter("iDVid", idvid3).getResultList();
                            for(int i=0; i<pripadanja.size(); i++){
                                Pripada pripada = pripadanja.get(i);
                                try{
                                    em.getTransaction().begin();
                                    em.remove(pripada);
                                    em.getTransaction().commit();
                                }
                                finally{
                                    if(em.getTransaction().isActive())
                                        em.getTransaction().rollback();
                                }
                            }
                            break;

                        case "Kreiranje kategorije":
                            String naziv2 = textMsg.getStringProperty("Naziv");
                            Kategorija kategorija = new Kategorija();
                            kategorija.setNaziv(naziv2);
                            try{
                                em.getTransaction().begin();
                                em.persist(kategorija);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            TextMessage txtKategorija = context.createTextMessage("Kreirana kategorija " + naziv2);
                            txtKategorija.setStringProperty("Broj", "5");
                            producer.send(ResponseQueue, txtKategorija);
                            break;

                        case "Kreiranje video snimka":
                            String naziv3 = textMsg.getStringProperty("Naziv");
                            int trajanje = textMsg.getIntProperty("Trajanje");
                            int vlasnik = textMsg.getIntProperty("Vlasnik");
                            Videosnimak videosnimak2 = new Videosnimak();
                            videosnimak2.setDatumVreme(new Date());
                            videosnimak2.setNaziv(naziv3);
                            videosnimak2.setTrajanje(trajanje);
                            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIDKor", Korisnik.class).setParameter("iDKor", vlasnik).getResultList();
                            if(korisnici.isEmpty()){
                                TextMessage txtVideosnimak = context.createTextMessage("Ne postoji korisnik sa ID " + vlasnik);
                                txtVideosnimak.setStringProperty("Broj", "6");
                                producer.send(ResponseQueue, txtVideosnimak);
                                continue;
                            }
                            Korisnik korisnik = korisnici.get(0);
                            videosnimak2.setVlasnik(korisnik);
                            try{
                                em.getTransaction().begin();
                                em.persist(videosnimak2);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            TextMessage txtVideosnimak = context.createTextMessage("Kreiranje video snimka");
                            txtVideosnimak.setStringProperty("Naziv", videosnimak2.getNaziv());
                            txtVideosnimak.setIntProperty("Trajanje", videosnimak2.getTrajanje());
                            txtVideosnimak.setIntProperty("Vlasnik", vlasnik);
                            producer.send(QueueP2P3, txtVideosnimak);
                            txtVideosnimak = context.createTextMessage("Kreiran video snimak " + naziv3);
                            txtVideosnimak.setStringProperty("Broj", "6");
                            producer.send(ResponseQueue, txtVideosnimak);
                            break;

                        case "Dodavanje kategorije video snimku":
                            int idvid4 = textMsg.getIntProperty("IDVid");
                            int idkat = textMsg.getIntProperty("IDKat");
                            List<Kategorija> kategorije2 = em.createNamedQuery("Kategorija.findByIDKat", Kategorija.class).setParameter("iDKat", idkat).getResultList();
                            List<Videosnimak> videosnimci2 = em.createNamedQuery("Videosnimak.findByIDVid", Videosnimak.class).setParameter("iDVid", idvid4).getResultList();
                            if(videosnimci2.isEmpty()){
                                TextMessage txtPripada = context.createTextMessage("Ne postoji video snimak sa ID " + idvid4);
                                txtPripada.setStringProperty("Broj", "8");
                                producer.send(ResponseQueue, txtPripada);
                                continue;
                            }
                            if(kategorije2.isEmpty()){
                                TextMessage txtPripada = context.createTextMessage("Ne postoji kategorija sa ID " + idkat);
                                txtPripada.setStringProperty("Broj", "8");
                                producer.send(ResponseQueue, txtPripada);
                                continue;
                            }
                            Pripada pripada = new Pripada();
                            Kategorija kategorija2 = kategorije2.get(0);
                            Videosnimak videosnimak3 = videosnimci2.get(0);
                            pripada.setIDKat(kategorija2);
                            pripada.setIDVid(videosnimak3);
                            try{
                                em.getTransaction().begin();
                                em.persist(pripada);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            TextMessage txtPripada = context.createTextMessage("Dodata kategorija " + idkat + " video snimku " + idvid4);
                            txtPripada.setStringProperty("Broj", "8");
                            producer.send(ResponseQueue, txtPripada);
                            break;
                    }
                }
                else if(msgP1P2 != null && msg == null){
                    TextMessage textMsg = (TextMessage)msgP1P2;
                    String text = textMsg.getText();

                    switch(text){
                        case "Kreiranje korisnika":
                            Korisnik korisnik = new Korisnik();
                            korisnik.setIme(textMsg.getStringProperty("Ime"));
                            korisnik.setEmail(textMsg.getStringProperty("Email"));
                            korisnik.setGodiste(textMsg.getStringProperty("Godiste"));
                            korisnik.setPol(textMsg.getStringProperty("Pol"));
                            korisnik.setIDMes(textMsg.getIntProperty("IDMes"));
                            try{
                                em.getTransaction().begin();
                                em.persist(korisnik);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            break;

                        case "Promena email adrese za korisnika":
                            int idkor = textMsg.getIntProperty("IDKor");
                            String email = textMsg.getStringProperty("Email");
                            List<Korisnik> korisnici2 = em.createNamedQuery("Korisnik.findByIDKor", Korisnik.class).setParameter("iDKor", idkor).getResultList();
                            if(korisnici2.isEmpty())
                                continue;
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
                            break;

                        case "Promena mesta za korisnika":
                            int idkor2 = textMsg.getIntProperty("IDKor");
                            int idmes = textMsg.getIntProperty("IDMes");
                            List<Korisnik> korisnici3 = em.createNamedQuery("Korisnik.findByIDKor", Korisnik.class).setParameter("iDKor", idkor2).getResultList();
                            if(korisnici3.isEmpty())
                                continue;
                            Korisnik korisnik3 = korisnici3.get(0);
                            korisnik3.setIDMes(idmes);
                            try{
                                em.getTransaction().begin();
                                em.persist(korisnik3);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            break;
                    }
                }
            }
        }
        catch(Exception e){}
    }
}
