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
@Path("paket")
public class PaketResource {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "RequestQueue3")
    private Queue RequestQueue3;
    
    @Resource(lookup = "ResponseQueue")
    private Queue ResponseQueue;
    
    @GET
    @Path("svipaketi")
    public Response getSviPaketi(){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage textMsg = context.createTextMessage("Dohvatanje svih paketa");
            producer.send(RequestQueue3, textMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("22")){
                TextMessage txtMsg = (TextMessage)msg;
                return Response.status(Response.Status.OK).entity(txtMsg.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
    
    @POST
    @Path("kreiranjepaketa/{Cena}")
    public Response createPaket(@PathParam("Cena") int cena){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage txtMsg = context.createTextMessage("Kreiranje paketa");
            txtMsg.setIntProperty("Cena", cena);
            producer.send(RequestQueue3, txtMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("9")){
                TextMessage txtResponse = (TextMessage)msg;
                return Response.status(Response.Status.OK).entity(txtResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
    
    @PUT
    @Path("{IDPak}/{Cena}")
    public Response updateCena(@PathParam("IDPak") int idpak, @PathParam("Cena") int cena){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage textMsg = context.createTextMessage("Promena mesecne cene za paket");
            textMsg.setIntProperty("IDPak", idpak);
            textMsg.setIntProperty("Cena", cena);
            producer.send(RequestQueue3, textMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("10")){
                TextMessage txtResponse = (TextMessage)msg;
                return Response.status(Response.Status.OK).entity(txtResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
}
