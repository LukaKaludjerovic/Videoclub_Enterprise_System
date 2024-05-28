/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.klijent;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 *
 * @author kalud
 */
public interface Methods {
    @POST("mesto/{Naziv}")
    Call<String> createMesto(@Path("Naziv") String naziv);
    
    @POST("korisnik/{Ime}/{Email}/{Godiste}/{Pol}/{IDMes}")
    Call<String> createKorisnik(@Path("Ime") String ime, @Path("Email") String email, @Path("Godiste") String godiste, @Path("Pol") String pol, @Path("IDMes") int idmes);
    
    @PUT("korisnik/{IDKor}/{Email}")
    Call<String> updateEmail(@Path("IDKor") int idkor, @Path("Email") String email);
    
    @PUT("korisnik/promenamesta/{IDKor}/{IDMes}")
    Call<String> updateMesto(@Path("IDKor") int idkor, @Path("IDMes") int idmes);
    
    @POST("kategorija/kreiranje/{Naziv}")
    Call<String> createKategorija(@Path("Naziv") String naziv);
    
    @POST("videosnimak/{Naziv}/{Trajanje}/{Vlasnik}")
    Call<String> createSnimak(@Path("Naziv") String naziv, @Path("Trajanje") int trajanje, @Path("Vlasnik") int vlasnik);
    
    @PUT("videosnimak/{IDVid}/{Naziv}")
    Call<String> updateNaziv(@Path("IDVid") int idvid, @Path("Naziv") String naziv);
    
    @POST("kategorija/{IDVid}/{IDKat}")
    Call<String> createPripada(@Path("IDVid") int idvid, @Path("IDKat") int idkat);
    
    @POST("paket/kreiranjepaketa/{Cena}")
    Call<String> createPaket(@Path("Cena") int cena);
    
    @PUT("paket/{IDPak}/{Cena}")
    Call<String> updateCena(@Path("IDPak") int idpak, @Path("Cena") int cena);
    
    @POST("pretplata/{IDKor}/{IDPak}")
    Call<String> createPretplata(@Path("IDKor") int idkor, @Path("IDPak") int idpak);
    
    @POST("gledanje/{IDVid}/{IDKor}")
    Call<String> createGledanje(@Path("IDVid") int idvid, @Path("IDKor") int idkor);
    
    @POST("ocena/{IDVid}/{IDKor}/{Vrednost}")
    Call<String> createOcena(@Path("IDVid") int idvid, @Path("IDKor") int idkor, @Path("Vrednost") int vrednost);
    
    @PUT("ocena/menjanje/{IDVid}/{IDKor}/{Vrednost}")
    Call<String> updateOcena(@Path("IDVid") int idvid, @Path("IDKor") int idkor, @Path("Vrednost") int vrednost);
    
    @DELETE("ocena/brisanje/{IDVid}/{IDKor}")
    Call<String> deleteOcena(@Path("IDVid") int idvid, @Path("IDKor") int idkor);
    
    @DELETE("videosnimak/brisanje/{IDKor}/{IDVid}")
    Call<String> deleteVideo(@Path("IDKor") int idkor, @Path("IDVid") int idvid);
    
    @GET("mesto/svamesta")
    Call<String> getSvaMesta();
    
    @GET("korisnik/svikorisnici")
    Call<String> getSviKorisnici();
    
    @GET("kategorija/svekategorije")
    Call<String> getSveKategorije();
    
    @GET("videosnimak/svisnimci")
    Call<String> getSviSnimci();
    
    @GET("kategorija/{IDVid}")
    Call<String> getSveKategorijeZaVideo(@Path("IDVid") int idvid);
    
    @GET("paket/svipaketi")
    Call<String> getSviPaketi();
    
    @GET("pretplata/svepretplate/{IDKor}")
    Call<String> getSvePretplate(@Path("IDKor") int idkor);
    
    @GET("gledanje/svagledanja/{IDVid}")
    Call<String> getSvaGledanja(@Path("IDVid") int idvid);
    
    @GET("ocena/sveocene/{IDVid}")
    Call<String> getSveOcene(@Path("IDVid") int idvid);
}
