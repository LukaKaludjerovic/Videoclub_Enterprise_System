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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author kalud
 */
@Path("kategorija")
public class KategorijaResource {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "RequestQueue2")
    private Queue RequestQueue2;
    
    @Resource(lookup = "ResponseQueue")
    private Queue ResponseQueue;
    
    @GET
    @Path("svekategorije")
    public Response getSveKategorije(){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage textMsg = context.createTextMessage("Dohvatanje svih kategorija");
            producer.send(RequestQueue2, textMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("19")){
                TextMessage txtResponse = (TextMessage)msg;
                return Response.status(Response.Status.OK).entity(txtResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
    
    @GET
    @Path("{IDVid}")
    public Response getSveKategorijeZaVideo(@PathParam("IDVid") int idvid){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage textMsg = context.createTextMessage("Dohvatanje kategorija za odredjeni video snimak");
            textMsg.setIntProperty("IDVid", idvid);
            producer.send(RequestQueue2, textMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("21")){
                TextMessage txtMsg = (TextMessage)msg;
                return Response.status(Response.Status.OK).entity(txtMsg.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
    
    @POST
    @Path("kreiranje/{Naziv}")
    public Response createKategorija(@PathParam("Naziv") String naziv){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage txtMsg = context.createTextMessage("Kreiranje kategorije");
            txtMsg.setStringProperty("Naziv", naziv);
            producer.send(RequestQueue2, txtMsg);
            
            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("5")){
                TextMessage txtResponse = (TextMessage)msg;
                return Response.status(Response.Status.CREATED).entity(txtResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
    
    @POST
    @Path("{IDVid}/{IDKat}")
    public Response createPripada(@PathParam("IDVid") int idvid, @PathParam("IDKat") int idkat){
        try{
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(ResponseQueue);
            
            TextMessage txtMsg = context.createTextMessage("Dodavanje kategorije video snimku");
            txtMsg.setIntProperty("IDVid", idvid);
            txtMsg.setIntProperty("IDKat", idkat);
            producer.send(RequestQueue2, txtMsg);

            Message msg = consumer.receive();
            consumer.close();
            context.close();
            
            if(msg instanceof TextMessage && msg.getStringProperty("Broj").equals("8")){
                TextMessage txtResponse = (TextMessage)msg;
                return Response.status(Response.Status.CREATED).entity(txtResponse.getText()).build();
            }
        }
        catch(Exception e){}
        return Response.status(Response.Status.BAD_REQUEST).entity("Greska").build();
    }
}
