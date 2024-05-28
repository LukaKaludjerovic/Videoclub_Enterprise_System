/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.klijent;

import java.util.Scanner;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * @author kalud
 */
public class Main {
    private static final String serverURI = "http://localhost:8080/CentralniServer/resources/";
    
    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverURI).addConverterFactory(ScalarsConverterFactory.create()).build();
        Methods methods = retrofit.create(Methods.class);
        
        Scanner input = new Scanner(System.in);
        
        try{
            while(true){
                System.out.println("Odaberite funkcionalnost (1-25), za kraj unesite 0: ");
                int choice = input.nextInt();
                input.nextLine();
                
                switch(choice){
                    case 0: 
                        input.close();
                        return;
                        
                    case 1:
                        System.out.println("Unesite naziv grada: ");
                        String naziv1 = input.nextLine();
                        Call<String> method1 = methods.createMesto(naziv1);
                        Response<String> response1 = method1.execute();
                        String result1 = response1.body();
                        System.out.println(result1);
                        break;
                        
                    case 2:
                        System.out.println("Unesite ime korisnika: ");
                        String ime2 = input.nextLine();
                        System.out.println("Unesite email korisnika: ");
                        String email2 = input.nextLine();
                        System.out.println("Unesite godiste korisnika: ");
                        String godiste2 = input.nextLine();
                        System.out.println("Unesite pol korisnika (M/Z): ");
                        String pol2 = input.nextLine();
                        System.out.println("Unesite ID mesta korisnika: ");
                        int idmes2 = input.nextInt();
                        input.nextLine();
                        Call<String> method2 = methods.createKorisnik(ime2, email2, godiste2, pol2, idmes2);
                        Response<String> response2 = method2.execute();
                        String result2 = response2.body();
                        System.out.println(result2);
                        break;
                    
                    case 3: 
                        System.out.println("Unesite ID korisnika: ");
                        int idkor3 = input.nextInt();
                        input.nextLine();
                        System.out.println("Unesite novi email korisnika: ");
                        String email3 = input.nextLine();
                        Call<String> method3 = methods.updateEmail(idkor3, email3);
                        Response<String> response3 = method3.execute();
                        String result3 = response3.body();
                        System.out.println(result3);
                        break;
                        
                    case 4: 
                        System.out.println("Unesite ID korisnika: ");
                        int idkor4 = input.nextInt();
                        input.nextLine();
                        System.out.println("Unesite novi ID mesta: ");
                        int idmes4 = input.nextInt();
                        input.nextLine();
                        Call<String> method4 = methods.updateMesto(idkor4, idmes4);
                        Response<String> response4 = method4.execute();
                        String result4 = response4.body();
                        System.out.println(result4);
                        break;
                        
                    case 5: 
                        System.out.println("Unesite naziv kategorije: ");
                        String naziv5 = input.nextLine();
                        Call<String> method5 = methods.createKategorija(naziv5);
                        Response<String> response5 = method5.execute();
                        String result5 = response5.body();
                        System.out.println(result5);
                        break;
                        
                    case 6: 
                        System.out.println("Unesite naziv video snimka: ");
                        String naziv6 = input.nextLine();
                        System.out.println("Unesite trajanje video snimka: ");
                        int trajanje6 = input.nextInt();
                        input.nextLine();
                        System.out.println("Unesite ID vlasnika video snimka: ");
                        int vlasnik6 = input.nextInt();
                        input.nextLine();
                        Call<String> method6 = methods.createSnimak(naziv6, trajanje6, vlasnik6);
                        Response<String> response6 = method6.execute();
                        String result6 = response6.body();
                        System.out.println(result6);
                        break;
                        
                    case 7:
                        System.out.println("Unesite ID video snimka: ");
                        int idvid7 = input.nextInt();
                        input.nextLine();
                        System.out.println("Unesite novi naziv video snimka: ");
                        String naziv7 = input.nextLine();
                        Call<String> method7 = methods.updateNaziv(idvid7, naziv7);
                        Response<String> response7 = method7.execute();
                        String result7 = response7.body();
                        System.out.println(result7);
                        break;
                        
                    case 8:
                        System.out.println("Unesite ID video snimka: ");
                        int idvid8 = input.nextInt();
                        input.nextLine();
                        System.out.println("Unesite ID kategorije: ");
                        int idkat8 = input.nextInt();
                        input.nextLine();
                        Call<String> method8 = methods.createPripada(idvid8, idkat8);
                        Response<String> response8 = method8.execute();
                        String result8 = response8.body();
                        System.out.println(result8);
                        break;
                        
                    case 9: 
                        System.out.println("Unesite cenu paketa: ");
                        int cena9 = input.nextInt();
                        input.nextLine();
                        Call<String> method9 = methods.createPaket(cena9);
                        Response<String> response9 = method9.execute();
                        String result9 = response9.body();
                        System.out.println(result9);
                        break;
                        
                    case 10: 
                        System.out.println("Unesite ID paketa: ");
                        int idpak10 = input.nextInt();
                        input.nextLine();
                        System.out.println("Unesite novu cenu paketa: ");
                        int cena10 = input.nextInt();
                        input.nextLine();
                        Call<String> method10 = methods.updateCena(idpak10, cena10);
                        Response<String> response10 = method10.execute();
                        String result10 = response10.body();
                        System.out.println(result10);
                        break;
                        
                    case 11:
                        System.out.println("Unesite ID korisnika: ");
                        int idkor11 = input.nextInt();
                        input.nextLine();
                        System.out.println("Unesite ID paketa: ");
                        int idpak11 = input.nextInt();
                        input.nextLine();
                        Call<String> method11 = methods.createPretplata(idkor11, idpak11);
                        Response<String> response11 = method11.execute();
                        String result11 = response11.body();
                        System.out.println(result11);
                        break;
                        
                    case 12:
                        System.out.println("Unesite ID video snimka: ");
                        int idvid12 = input.nextInt();
                        input.nextLine();
                        System.out.println("Unesite ID korisnika: ");
                        int idkor12 = input.nextInt();
                        input.nextLine();
                        Call<String> method12 = methods.createGledanje(idvid12, idkor12);
                        Response<String> response12 = method12.execute();
                        String result12 = response12.body();
                        System.out.println(result12);
                        break;
                        
                    case 13: 
                        System.out.println("Unesite ID video snimka: ");
                        int idvid13 = input.nextInt();
                        input.nextLine();
                        System.out.println("Unesite ID korisnika: ");
                        int idkor13 = input.nextInt();
                        input.nextLine();
                        System.out.println("Unesite vrednost ocene: ");
                        int ocena13 = input.nextInt();
                        input.nextLine();
                        Call<String> method13 = methods.createOcena(idvid13, idkor13, ocena13);
                        Response<String> response13 = method13.execute();
                        String result13 = response13.body();
                        System.out.println(result13);
                        break;
                        
                    case 14:
                        System.out.println("Unesite ID video snimka: ");
                        int idvid14 = input.nextInt();
                        input.nextLine();
                        System.out.println("Unesite ID korisnika: ");
                        int idkor14 = input.nextInt();
                        input.nextLine();
                        System.out.println("Unesite novu vrednost ocene: ");
                        int ocena14 = input.nextInt();
                        input.nextLine();
                        Call<String> method14 = methods.updateOcena(idvid14, idkor14, ocena14);
                        Response<String> response14 = method14.execute();
                        String result14 = response14.body();
                        System.out.println(result14);
                        break;
                        
                    case 15:
                        System.out.println("Unesite ID video snimka: ");
                        int idvid15 = input.nextInt();
                        input.nextLine();
                        System.out.println("Unesite ID korisnika: ");
                        int idkor15 = input.nextInt();
                        input.nextLine();
                        Call<String> method15 = methods.deleteOcena(idvid15, idkor15);
                        Response<String> response15 = method15.execute();
                        String result15 = response15.body();
                        System.out.println(result15);
                        break;
                        
                    case 16: 
                        System.out.println("Unesite Vas ID: ");
                        int idkor16 = input.nextInt();
                        input.nextLine();
                        System.out.println("Unesite ID video snimka: ");
                        int idvid16 = input.nextInt();
                        input.nextLine();
                        Call<String> method16 = methods.deleteVideo(idkor16, idvid16);
                        Response<String> response16 = method16.execute();
                        String result16 = response16.body();
                        System.out.println(result16);
                        break;
                        
                    case 17:
                        Call<String> method17 = methods.getSvaMesta();
                        Response<String> response17 = method17.execute();
                        String result17 = response17.body();
                        System.out.println(result17);
                        break;
                        
                    case 18: 
                        Call<String> method18 = methods.getSviKorisnici();
                        Response<String> response18 = method18.execute();
                        String result18 = response18.body();
                        System.out.println(result18);
                        break;
                        
                    case 19:
                        Call<String> method19 = methods.getSveKategorije();
                        Response<String> response19 = method19.execute();
                        String result19 = response19.body();
                        System.out.println(result19);
                        break;
                        
                    case 20: 
                        Call<String> method20 = methods.getSviSnimci();
                        Response<String> response20 = method20.execute();
                        String result20 = response20.body();
                        System.out.println(result20);
                        break;
                        
                    case 21:
                        System.out.println("Unesite ID snimka: ");
                        int idvid21 = input.nextInt();
                        input.nextLine();
                        Call<String> method21 = methods.getSveKategorijeZaVideo(idvid21);
                        Response<String> response21 = method21.execute();
                        String result21 = response21.body();
                        System.out.println(result21);
                        break;
                        
                    case 22: 
                        Call<String> method22 = methods.getSviPaketi();
                        Response<String> response22 = method22.execute();
                        String result22 = response22.body();
                        System.out.println(result22);
                        break;
                        
                    case 23: 
                        System.out.println("Unesite ID korisnika: ");
                        int idkor23 = input.nextInt();
                        input.nextLine();
                        Call<String> method23 = methods.getSvePretplate(idkor23);
                        Response<String> response23 = method23.execute();
                        String result23 = response23.body();
                        System.out.println(result23);
                        break;
                        
                    case 24: 
                        System.out.println("Unesite ID snimka: ");
                        int idvid24 = input.nextInt();
                        input.nextLine();
                        Call<String> method24 = methods.getSvaGledanja(idvid24);
                        Response<String> response24 = method24.execute();
                        String result24 = response24.body();
                        System.out.println(result24);
                        break;
                        
                    case 25: 
                        System.out.println("Unesite ID snimka: ");
                        int idvid25 = input.nextInt();
                        input.nextLine();
                        Call<String> method25 = methods.getSveOcene(idvid25);
                        Response<String> response25 = method25.execute();
                        String result25 = response25.body();
                        System.out.println(result25);
                        break;
                        
                    default: 
                        System.out.println("Neispravan izbor, pokusajte ponovo");
                        break;
                }
            }
        }
        catch(Exception e){}
    }
}
