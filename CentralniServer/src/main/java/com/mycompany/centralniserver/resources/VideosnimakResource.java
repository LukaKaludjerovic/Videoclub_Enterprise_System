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
import javax.ws.rs.DELETE;
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
@Path("videosnimak")
public class VideosnimakResource {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "RequestQueue2")
    private Queue RequestQueue2;
    
    @Resource(lookup = "ResponseQueue")
    private Queue ResponseQueue;
    
    @GET
    @Path("svisnimci")
    public Response getSviSnimci(){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage textMsg = context.createTextMessage("Dohvatanje svih video snimaka");
            producer.send(RequestQueue2, textMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("20")){
                TextMessage txtMsg = (TextMessage)msg;
                return Response.status(Response.Status.OK).entity(txtMsg.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
    
    @POST
    @Path("{Naziv}/{Trajanje}/{Vlasnik}")
    public Response createSnimak(@PathParam("Naziv") String naziv, @PathParam("Trajanje") int trajanje, @PathParam("Vlasnik") int vlasnik){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage txtMsg = context.createTextMessage("Kreiranje video snimka");
            txtMsg.setStringProperty("Naziv", naziv);
            txtMsg.setIntProperty("Trajanje", trajanje);
            txtMsg.setIntProperty("Vlasnik", vlasnik);
            producer.send(RequestQueue2, txtMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("6")){
                TextMessage txtResponse = (TextMessage)msg;
                return Response.status(Response.Status.CREATED).entity(txtResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
    
    @PUT
    @Path("{IDVid}/{Naziv}")
    public Response updateNaziv(@PathParam("IDVid") int idvid, @PathParam("Naziv") String naziv){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage txtMsg = context.createTextMessage("Promena naziva video snimka");
            txtMsg.setStringProperty("Naziv", naziv);
            txtMsg.setIntProperty("IDVid", idvid);
            producer.send(RequestQueue2, txtMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("7")){
                TextMessage txtResponse = (TextMessage)msg;
                return Response.status(Response.Status.CREATED).entity(txtResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
    
    @DELETE
    @Path("brisanje/{IDKor}/{IDVid}")
    public Response deleteVideo(@PathParam("IDKor") int idkor, @PathParam("IDVid") int idvid){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage txtMsg = context.createTextMessage("Brisanje video snimka od strane korisnika koji ga je kreirao");
            txtMsg.setIntProperty("IDKor", idkor);
            txtMsg.setIntProperty("IDVid", idvid);
            producer.send(RequestQueue2, txtMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("16")){
                TextMessage txtMsgResponse = (TextMessage)msg;
                return Response.status(Response.Status.CREATED).entity(txtMsgResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
}
