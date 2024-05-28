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
@Path("ocena")
public class OcenaResource {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "RequestQueue3")
    private Queue RequestQueue3;
    
    @Resource(lookup = "ResponseQueue")
    private Queue ResponseQueue;
    
    @GET
    @Path("sveocene/{IDVid}")
    public Response getSveOcene(@PathParam("IDVid") int idvid){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage textMsg = context.createTextMessage("Dohvatanje svih ocena za video snimak");
            textMsg.setIntProperty("IDVid", idvid);
            producer.send(RequestQueue3, textMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("25")){
                TextMessage txtMsg = (TextMessage)msg;
                return Response.status(Response.Status.OK).entity(txtMsg.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
    
    @POST
    @Path("{IDVid}/{IDKor}/{Vrednost}")
    public Response createOcena(@PathParam("IDVid") int idvid, @PathParam("IDKor") int idkor, @PathParam("Vrednost") int vrednost){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            if(vrednost < 1 || vrednost > 5){
                return Response.status(Response.Status.BAD_REQUEST).entity("Ocena mora biti izmedju 1 i 5").build();
            }
            
            TextMessage txtMsg = context.createTextMessage("Kreiranje ocene korisnika za video snimak");
            txtMsg.setIntProperty("IDVid", idvid);
            txtMsg.setIntProperty("IDKor", idkor);
            txtMsg.setIntProperty("Vrednost", vrednost);
            producer.send(RequestQueue3, txtMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("13")){
                TextMessage txtResponse = (TextMessage)msg;
                return Response.status(Response.Status.OK).entity(txtResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
    
    @PUT
    @Path("menjanje/{IDVid}/{IDKor}/{Vrednost}")
    public Response updateOcena(@PathParam("IDVid") int idvid, @PathParam("IDKor") int idkor, @PathParam("Vrednost") int vrednost){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            if(vrednost < 1 || vrednost > 5){
                return Response.status(Response.Status.BAD_REQUEST).entity("Ocena mora biti izmedju 1 i 5").build();
            }
            
            TextMessage textMsg = context.createTextMessage("Menjanje ocene korisnika za video snimak");
            textMsg.setIntProperty("IDVid", idvid);
            textMsg.setIntProperty("IDKor", idkor);
            textMsg.setIntProperty("Vrednost", vrednost);
            producer.send(RequestQueue3, textMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("14")){
                TextMessage txtResponse = (TextMessage)msg;
                return Response.status(Response.Status.OK).entity(txtResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
    
    @DELETE
    @Path("brisanje/{IDVid}/{IDKor}")
    public Response deleteOcena(@PathParam("IDVid") int idvid, @PathParam("IDKor") int idkor){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage textMsg = context.createTextMessage("Brisanje ocene korisnika za video snimak");
            textMsg.setIntProperty("IDVid", idvid);
            textMsg.setIntProperty("IDKor", idkor);
            producer.send(RequestQueue3, textMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("15")){
                TextMessage txtResponse = (TextMessage)msg;
                return Response.status(Response.Status.OK).entity(txtResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
}
