/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3;

import entities.Gledanje;
import entities.Korisnik;
import entities.Ocena;
import entities.Paket;
import entities.Pretplata;
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
    
    @Resource(lookup = "RequestQueue3")
    private static Queue RequestQueue3;
    
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
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
            EntityManager em = emf.createEntityManager();
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(RequestQueue3);
            JMSConsumer consumerP1P3 = context.createConsumer(QueueP1P3);
            JMSConsumer consumerP2P3 = context.createConsumer(QueueP2P3);
            JMSProducer producer = context.createProducer();
            
            while(true){
                Message msg = consumer.receive(100);
                Message msgP1P3 = consumerP1P3.receive(100);
                Message msgP2P3 = consumerP2P3.receive(100);
                
                if(msg != null && msgP1P3 == null && msgP2P3 == null){
                    TextMessage textMsg = (TextMessage)msg;
                    String text = textMsg.getText();

                    switch(text){
                        case "Dohvatanje svih paketa":
                            List<Paket> paketi = em.createNamedQuery("Paket.findAll", Paket.class).getResultList();
                            StringBuilder sb = new StringBuilder();
                            for(Paket p : paketi){
                                sb.append("IDPak: ");
                                sb.append(p.getIDPak());
                                sb.append(", Cena: ");
                                sb.append(p.getCena());
                                sb.append("\n");
                            }
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            txtMsg.setStringProperty("Broj", "22");
                            producer.send(ResponseQueue, txtMsg);
                            break;

                        case "Dohvatanje svih pretplata za korisnika":
                            int idkor = textMsg.getIntProperty("IDKor");
                            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIDKor", Korisnik.class).setParameter("iDKor", idkor).getResultList();
                            if(korisnici.isEmpty()){
                                TextMessage txtMsg2 = context.createTextMessage("Ne postoji korisnik sa ID " + idkor);
                                txtMsg2.setStringProperty("Broj", "23");
                                producer.send(ResponseQueue, txtMsg2);
                                continue;
                            }
                            List<Pretplata> pretplate = em.createQuery("SELECT p FROM Pretplata p WHERE p.iDKor.iDKor = :iDKor", Pretplata.class).setParameter("iDKor", idkor).getResultList();
                            sb = new StringBuilder();
                            for(Pretplata p : pretplate){
                                sb.append("IDPre: ");
                                sb.append(p.getIDPre());
                                sb.append(", IDKor: ");
                                sb.append(p.getIDKor().getIDKor());
                                sb.append(", IDPak: ");
                                sb.append(p.getIDPak().getIDPak());
                                sb.append(", DatumVreme: ");
                                sb.append(p.getDatumVreme());
                                sb.append(", Cena: ");
                                sb.append(p.getCena());
                                sb.append("\n");
                            }
                            TextMessage txtMsg2 = context.createTextMessage(sb.toString());
                            txtMsg2.setStringProperty("Broj", "23");
                            producer.send(ResponseQueue, txtMsg2);
                            break;

                        case "Dohvatanje svih gledanja za video snimak":
                            int idvid = textMsg.getIntProperty("IDVid");
                            List<Videosnimak> videi = em.createNamedQuery("Videosnimak.findByIDVid", Videosnimak.class).setParameter("iDVid", idvid).getResultList();
                            if(videi.isEmpty()){
                                TextMessage txtMsg3 = context.createTextMessage("Ne postoji video snimak sa ID " + idvid);
                                txtMsg3.setStringProperty("Broj", "24");
                                producer.send(ResponseQueue, txtMsg3);
                                continue;
                            }
                            List<Gledanje> gledanja = em.createQuery("SELECT g FROM Gledanje g WHERE g.iDVid.iDVid = :iDVid", Gledanje.class).setParameter("iDVid", idvid).getResultList();
                            sb = new StringBuilder();
                            for(Gledanje g : gledanja){
                                sb.append("IDGle: ");
                                sb.append(g.getIDGle());
                                sb.append(", IDKor: ");
                                sb.append(g.getIDKor().getIDKor());
                                sb.append(", IDVid: ");
                                sb.append(g.getIDVid().getIDVid());
                                sb.append(", DatumVreme: ");
                                sb.append(g.getDatumVreme());
                                sb.append(", Pocetak: ");
                                sb.append(g.getPocetak());
                                sb.append(", Odgledano: ");
                                sb.append(g.getOdgledano());
                                sb.append("\n");
                            }
                            TextMessage txtMsg3 = context.createTextMessage(sb.toString());
                            txtMsg3.setStringProperty("Broj", "24");
                            producer.send(ResponseQueue, txtMsg3);
                            break;

                        case "Dohvatanje svih ocena za video snimak":
                            int idvid2 = textMsg.getIntProperty("IDVid");
                            List<Videosnimak> videi2 = em.createNamedQuery("Videosnimak.findByIDVid", Videosnimak.class).setParameter("iDVid", idvid2).getResultList();
                            if(videi2.isEmpty()){
                                TextMessage txtMsg4 = context.createTextMessage("Ne postoji video snimak sa ID " + idvid2);
                                txtMsg4.setStringProperty("Broj", "25");
                                producer.send(ResponseQueue, txtMsg4);
                                continue;
                            }
                            List<Ocena> ocene = em.createQuery("SELECT o FROM Ocena o WHERE o.iDVid.iDVid = :iDVid", Ocena.class).setParameter("iDVid", idvid2).getResultList();
                            sb = new StringBuilder();
                            for(Ocena o : ocene){
                                sb.append("IDOce: ");
                                sb.append(o.getIDOce());
                                sb.append(", IDKor: ");
                                sb.append(o.getIDKor().getIDKor());
                                sb.append(", IDVid: ");
                                sb.append(o.getIDVid().getIDVid());
                                sb.append(", Ocena: ");
                                sb.append(o.getOcena());
                                sb.append(", DatumVreme: ");
                                sb.append(o.getDatumVreme());
                                sb.append("\n");
                            }
                            TextMessage txtMsg4 = context.createTextMessage(sb.toString());
                            txtMsg4.setStringProperty("Broj", "25");
                            producer.send(ResponseQueue, txtMsg4);
                            break;

                        case "Promena mesecne cene za paket":
                            int idpak = textMsg.getIntProperty("IDPak");
                            int cena = textMsg.getIntProperty("Cena");
                            List<Paket> paketi2 = em.createNamedQuery("Paket.findByIDPak", Paket.class).setParameter("iDPak", idpak).getResultList();
                            if(paketi2.isEmpty()){
                                TextMessage txtKategorija = context.createTextMessage("Ne postoji paket sa ID " + idpak);
                                txtKategorija.setStringProperty("Broj", "10");
                                producer.send(ResponseQueue, txtKategorija);
                                continue;
                            }
                            Paket paket = paketi2.get(0);
                            paket.setCena(cena);
                            try{
                                em.getTransaction().begin();
                                em.persist(paket);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            TextMessage txtKategorija = context.createTextMessage("Nova cena paketa ID " + idpak + " je " + cena);
                            txtKategorija.setStringProperty("Broj", "10");
                            producer.send(ResponseQueue, txtKategorija);
                            break;

                        case "Menjanje ocene korisnika za video snimak":
                            int idvid1 = textMsg.getIntProperty("IDVid");
                            int idkor1 = textMsg.getIntProperty("IDKor");
                            List<Videosnimak> snimci1 = em.createNamedQuery("Videosnimak.findByIDVid", Videosnimak.class).setParameter("iDVid", idvid1).getResultList();
                            if(snimci1.isEmpty()){
                                TextMessage txtOcena = context.createTextMessage("Ne postoji video snimak sa ID " + idvid1);
                                txtOcena.setStringProperty("Broj", "14");
                                producer.send(ResponseQueue, txtOcena);
                                continue;
                            }
                            List<Korisnik> korisnici1 = em.createNamedQuery("Korisnik.findByIDKor", Korisnik.class).setParameter("iDKor", idkor1).getResultList();
                            if(korisnici1.isEmpty()){
                                TextMessage txtOcena = context.createTextMessage("Ne postoji korisnik sa ID " + idkor1);
                                txtOcena.setStringProperty("Broj", "14");
                                producer.send(ResponseQueue, txtOcena);
                                continue;
                            }
                            int vrednost = textMsg.getIntProperty("Vrednost");
                            List<Ocena> ocene1 = em.createQuery("SELECT o FROM Ocena o WHERE o.iDKor.iDKor = :iDKor AND o.iDVid.iDVid = :iDVid", Ocena.class).setParameter("iDKor", idkor1).setParameter("iDVid", idvid1).getResultList();
                            if(ocene1.isEmpty()){
                                TextMessage txtOcena = context.createTextMessage("Ne postoji nijedna ocena trazenog korisnika za trazeni video snimak");
                                txtOcena.setStringProperty("Broj", "14");
                                producer.send(ResponseQueue, txtOcena);
                                continue;
                            }
                            Ocena ocena1 = ocene1.get(0);
                            ocena1.setOcena(vrednost);
                            ocena1.setDatumVreme(new Date());
                            try{
                                em.getTransaction().begin();
                                em.persist(ocena1);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            TextMessage txtOcena = context.createTextMessage("Nova ocena korisnika " + idkor1 + " za video snimak " + idvid1 + " je " + vrednost);
                            txtOcena.setStringProperty("Broj", "14");
                            producer.send(ResponseQueue, txtOcena);
                            break;

                        case "Brisanje ocene korisnika za video snimak":
                            int idvid3 = textMsg.getIntProperty("IDVid");
                            List<Videosnimak> snimci3 = em.createNamedQuery("Videosnimak.findByIDVid", Videosnimak.class).setParameter("iDVid", idvid3).getResultList();
                            if(snimci3.isEmpty()){
                                TextMessage txtBrisanje = context.createTextMessage("Ne postoji video snimak sa ID " + idvid3);
                                txtBrisanje.setStringProperty("Broj", "15");
                                producer.send(ResponseQueue, txtBrisanje);
                                continue;
                            }
                            int idkor3 = textMsg.getIntProperty("IDKor");
                            List<Korisnik> korisnici3 = em.createNamedQuery("Korisnik.findByIDKor", Korisnik.class).setParameter("iDKor", idkor3).getResultList();
                            if(korisnici3.isEmpty()){
                                TextMessage txtBrisanje = context.createTextMessage("Ne postoji korisnik sa ID " + idkor3);
                                txtBrisanje.setStringProperty("Broj", "15");
                                producer.send(ResponseQueue, txtBrisanje);
                                continue;
                            }
                            List<Ocena> ocene2 = em.createQuery("SELECT o FROM Ocena o WHERE o.iDKor.iDKor = :iDKor AND o.iDVid.iDVid = :iDVid", Ocena.class).setParameter("iDKor", idkor3).setParameter("iDVid", idvid3).getResultList();
                            if(ocene2.isEmpty()){
                                TextMessage txtBrisanje = context.createTextMessage("Ne postoji ocena trazenog korisnika za trazeni video snimak");
                                txtBrisanje.setStringProperty("Broj", "15");
                                producer.send(ResponseQueue, txtBrisanje);
                                continue;
                            }
                            Ocena ocena2 = ocene2.get(0);
                            try{
                                em.getTransaction().begin();
                                em.remove(ocena2);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            TextMessage txtOcena2 = context.createTextMessage("Obrisana ocena korisnika " + idkor3 + " za video snimak " + idvid3);
                            txtOcena2.setStringProperty("Broj", "15");
                            producer.send(ResponseQueue, txtOcena2);
                            break;

                        case "Kreiranje pretplate korisnika na paket":
                            int idpak4 = textMsg.getIntProperty("IDPak");
                            int idkor4 = textMsg.getIntProperty("IDKor");
                            List<Pretplata> pretplate4 = em.createQuery("SELECT p FROM Pretplata p WHERE p.iDKor.iDKor = :iDKor AND p.iDPak.iDPak = :iDPak ORDER BY p.datumVreme DESC", Pretplata.class).setParameter("iDKor", idkor4).setParameter("iDPak", idpak4).getResultList();
                            if(!pretplate4.isEmpty()){
                                Pretplata pretplata4 = pretplate4.get(0);
                                Date date1 = new Date();
                                Date date2 = pretplata4.getDatumVreme();
                                long miliseconds = date1.getTime() - date2.getTime();
                                long days = miliseconds / (24*60*60*1000);
                                if(days <= 30){
                                    TextMessage txtPretplata4 = context.createTextMessage("Vec postoji aktivna pretplata");
                                    txtPretplata4.setStringProperty("Broj", "11");
                                    producer.send(ResponseQueue, txtPretplata4);
                                    continue;
                                }
                            }
                            List<Korisnik> korisnici4 = em.createNamedQuery("Korisnik.findByIDKor", Korisnik.class).setParameter("iDKor", idkor4).getResultList();
                            List<Paket> paketi4 = em.createNamedQuery("Paket.findByIDPak", Paket.class).setParameter("iDPak", idpak4).getResultList();
                            if(korisnici4.isEmpty()){
                                TextMessage txtPretplata4 = context.createTextMessage("Ne postoji korisnik sa ID " + idkor4);
                                txtPretplata4.setStringProperty("Broj", "11");
                                producer.send(ResponseQueue, txtPretplata4);
                                continue;
                            }
                            if(paketi4.isEmpty()){
                                TextMessage txtPretplata4 = context.createTextMessage("Ne postoji paket sa ID " + idpak4);
                                txtPretplata4.setStringProperty("Broj", "11");
                                producer.send(ResponseQueue, txtPretplata4);
                                continue;
                            }
                            Korisnik korisnik4 = korisnici4.get(0);
                            Paket paket4 = paketi4.get(0);
                            Pretplata pretplata = new Pretplata();
                            pretplata.setIDKor(korisnik4);
                            pretplata.setIDPak(paket4);
                            pretplata.setCena(paket4.getCena());
                            pretplata.setDatumVreme(new Date());
                            try{
                                em.getTransaction().begin();
                                em.persist(pretplata);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            TextMessage txtPretplata = context.createTextMessage("Kreirana pretplata korisnika " + idkor4 + " na paket " + idpak4);
                            txtPretplata.setStringProperty("Broj", "11");
                            producer.send(ResponseQueue, txtPretplata);
                            break;

                        case "Kreiranje paketa":
                            int cena2 = textMsg.getIntProperty("Cena");
                            Paket paket2 = new Paket();
                            paket2.setCena(cena2);
                            try{
                                em.getTransaction().begin();
                                em.persist(paket2);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            TextMessage txtPaket = context.createTextMessage("Kreiran paket ID " + paket2.getIDPak() + " cene " + cena2);
                            txtPaket.setStringProperty("Broj", "9");
                            producer.send(ResponseQueue, txtPaket);
                            break;

                        case "Kreiranje gledanja video snimka od strane korisnika":
                            int idvid5 = textMsg.getIntProperty("IDVid");
                            int idkor5 = textMsg.getIntProperty("IDKor");
                            List<Videosnimak> snimci = em.createNamedQuery("Videosnimak.findByIDVid", Videosnimak.class).setParameter("iDVid", idvid5).getResultList();
                            List<Korisnik> korisnici5 = em.createNamedQuery("Korisnik.findByIDKor", Korisnik.class).setParameter("iDKor", idkor5).getResultList();
                            if(snimci.isEmpty()){
                                TextMessage txtGledanje = context.createTextMessage("Ne postoji video snimak sa ID " + idvid5);
                                txtGledanje.setStringProperty("Broj", "12");
                                producer.send(ResponseQueue, txtGledanje);
                                continue;
                            }
                            if(korisnici5.isEmpty()){
                                TextMessage txtGledanje = context.createTextMessage("Ne postoji korisnik sa ID " + idkor5);
                                txtGledanje.setStringProperty("Broj", "12");
                                producer.send(ResponseQueue, txtGledanje);
                                continue;
                            }
                            Videosnimak snimak = snimci.get(0);
                            Korisnik korisnik = korisnici5.get(0);
                            Gledanje gledanje = new Gledanje();
                            gledanje.setIDKor(korisnik);
                            gledanje.setIDVid(snimak);
                            gledanje.setDatumVreme(new Date());
                            gledanje.setOdgledano(0);
                            gledanje.setPocetak(0);
                            try{
                                em.getTransaction().begin();
                                em.persist(gledanje);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            TextMessage txtGledanje = context.createTextMessage("Kreirano gledanje video snimka " + idvid5 + " korisnika " + idkor5);
                            txtGledanje.setStringProperty("Broj", "12");
                            producer.send(ResponseQueue, txtGledanje);
                            break;

                        case "Kreiranje ocene korisnika za video snimak":
                            int idvid6 = textMsg.getIntProperty("IDVid");
                            int idkor6 = textMsg.getIntProperty("IDKor");
                            int vrednost6 = textMsg.getIntProperty("Vrednost");
                            List<Videosnimak> snimci6 = em.createNamedQuery("Videosnimak.findByIDVid", Videosnimak.class).setParameter("iDVid", idvid6).getResultList();
                            List<Korisnik> korisnici6 = em.createNamedQuery("Korisnik.findByIDKor", Korisnik.class).setParameter("iDKor", idkor6).getResultList();
                            if(snimci6.isEmpty()){
                                TextMessage txtCreateOcena = context.createTextMessage("Ne postoji video snimak sa ID " + idvid6);
                                txtCreateOcena.setStringProperty("Broj", "13");
                                producer.send(ResponseQueue, txtCreateOcena);
                                continue;
                            }
                            if(korisnici6.isEmpty()){
                                TextMessage txtCreateOcena = context.createTextMessage("Ne postoji korisnik sa ID " + idkor6);
                                txtCreateOcena.setStringProperty("Broj", "13");
                                producer.send(ResponseQueue, txtCreateOcena);
                                continue;
                            }
                            Videosnimak snimak6 = snimci6.get(0);
                            Korisnik korisnik6 = korisnici6.get(0);
                            Ocena ocena = new Ocena();
                            ocena.setIDKor(korisnik6);
                            ocena.setIDVid(snimak6);
                            ocena.setOcena(vrednost6);
                            ocena.setDatumVreme(new Date());
                            try{
                                em.getTransaction().begin();
                                em.persist(ocena);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            TextMessage txtCreateOcena = context.createTextMessage("Kreirana ocena korisnika " + idkor6 + " za video snimak " + idvid6);
                            txtCreateOcena.setStringProperty("Broj", "13");
                            producer.send(ResponseQueue, txtCreateOcena);
                            break;
                    }
                }
                else if(msgP1P3 != null && msg == null && msgP2P3 == null){
                    TextMessage textMsg = (TextMessage)msgP1P3;
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
                else if(msgP2P3 != null && msg == null && msgP1P3 == null){
                    TextMessage textMsg = (TextMessage)msgP2P3;
                    String text = textMsg.getText();

                    switch(text){
                        case "Kreiranje video snimka":
                            Videosnimak videosnimak = new Videosnimak();
                            videosnimak.setNaziv(textMsg.getStringProperty("Naziv"));
                            videosnimak.setTrajanje(textMsg.getIntProperty("Trajanje"));
                            int vlasnik = textMsg.getIntProperty("Vlasnik");
                            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIDKor", Korisnik.class).setParameter("iDKor", vlasnik).getResultList();
                            if(korisnici.isEmpty())
                                continue;
                            Korisnik korisnik = korisnici.get(0);
                            videosnimak.setVlasnik(korisnik);
                            videosnimak.setDatumVreme(new Date());
                            try{
                                em.getTransaction().begin();
                                em.persist(videosnimak);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            break;

                        case "Promena naziva video snimka":
                            int idvid2 = textMsg.getIntProperty("IDVid");
                            String naziv = textMsg.getStringProperty("Naziv");
                            List<Videosnimak> videosnimci = em.createNamedQuery("Videosnimak.findByIDVid", Videosnimak.class).setParameter("iDVid", idvid2).getResultList();
                            if(videosnimci.isEmpty())
                                continue;
                            Videosnimak videosnimak2 = videosnimci.get(0);
                            videosnimak2.setNaziv(naziv);
                            try{
                                em.getTransaction().begin();
                                em.persist(videosnimak2);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            break;

                        case "Brisanje video snimka od strane korisnika koji ga je kreirao":
                            int idkor3 = textMsg.getIntProperty("IDKor");
                            int idvid3 = textMsg.getIntProperty("IDVid");
                            List<Videosnimak> snimci3 = em.createNamedQuery("Videosnimak.findByIDVid", Videosnimak.class).setParameter("iDVid", idvid3).getResultList();
                            if(snimci3.isEmpty())
                                continue;
                            Videosnimak snimak3 = snimci3.get(0);
                            try{
                                em.getTransaction().begin();
                                em.remove(snimak3);
                                em.getTransaction().commit();
                            }
                            finally{
                                if(em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            List<Gledanje> gledanja = em.createQuery("SELECT g FROM Gledanje g WHERE g.iDVid.iDVid = :iDVid", Gledanje.class).setParameter("iDVid", idvid3).getResultList();
                            for(int i=0; i<gledanja.size(); i++){
                                Gledanje gledanje = gledanja.get(i);
                                try{
                                    em.getTransaction().begin();
                                    em.remove(gledanje);
                                    em.getTransaction().commit();
                                }
                                finally{
                                    if(em.getTransaction().isActive())
                                        em.getTransaction().rollback();
                                }
                            }
                            List<Ocena> ocene = em.createQuery("SELECT o FROM Ocena o WHERE o.iDVid.iDVid = :iDVid", Ocena.class).setParameter("iDVid", idvid3).getResultList();
                            for(int i=0; i<ocene.size(); i++){
                                Ocena ocena = ocene.get(i);
                                try{
                                    em.getTransaction().begin();
                                    em.remove(ocena);
                                    em.getTransaction().commit();
                                }
                                finally{
                                    if(em.getTransaction().isActive())
                                        em.getTransaction().rollback();
                                }
                            }
                            break;
                    }
                }
            }
        }
        catch(Exception e){}
    }
    
}
