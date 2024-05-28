/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.centralniserver.resources;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author kalud
 */
@Path("korisnik")
public class KorisnikResource {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "RequestQueue")
    private Queue RequestQueue;
    
    @Resource(lookup = "ResponseQueue")
    private Queue ResponseQueue;
    
    @GET
    @Path("svikorisnici")
    public Response getSviKorisnici(){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);

            TextMessage textMsg = context.createTextMessage("Dohvatanje svih korisnika");
            producer.send(RequestQueue, textMsg);

            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("18")){
                TextMessage txtResponse = (TextMessage)msg;
                return Response.status(Response.Status.CREATED).entity(txtResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
    
    @POST
    @Path("{Ime}/{Email}/{Godiste}/{Pol}/{IDMes}")
    public Response createKorisnik(@PathParam("Ime") String ime, @PathParam("Email") String email, @PathParam("Godiste") String godiste, @PathParam("Pol") String pol, @PathParam("IDMes") int idmes){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage txtMsg = context.createTextMessage("Kreiranje korisnika");
            txtMsg.setStringProperty("Ime", ime);
            txtMsg.setStringProperty("Email", email);
            txtMsg.setStringProperty("Godiste", godiste);
            txtMsg.setStringProperty("Pol", pol);
            txtMsg.setIntProperty("IDMes", idmes);
            producer.send(RequestQueue, txtMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("2")){
                TextMessage txtResponse = (TextMessage)msg;
                return Response.status(Response.Status.CREATED).entity(txtResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
    
    @PUT
    @Path("{IDKor}/{Email}")
    public Response updateEmail(@PathParam("IDKor") int idkor, @PathParam("Email") String email){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage txtMsg = context.createTextMessage("Promena email adrese za korisnika");
            txtMsg.setStringProperty("Email", email);
            txtMsg.setIntProperty("IDKor", idkor);
            producer.send(RequestQueue, txtMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("3")){
                TextMessage txtResponse = (TextMessage)msg;
                return Response.status(Response.Status.CREATED).entity(txtResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
    
    @PUT
    @Path("promenamesta/{IDKor}/{IDMes}")
    public Response updateMesto(@PathParam("IDKor") int idkor, @PathParam("IDMes") int idmes){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage txtMsg = context.createTextMessage("Promena mesta za korisnika");
            txtMsg.setIntProperty("IDKor", idkor);
            txtMsg.setIntProperty("IDMes", idmes);
            producer.send(RequestQueue, txtMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("4")){
                TextMessage txtResponse = (TextMessage)msg;
                return Response.status(Response.Status.CREATED).entity(txtResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
}
