package com.github.phillipkruger.javaeeserversparent.example;

import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * JAX-RS Entrypoint
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@Path("/")
@Produces(MediaType.TEXT_PLAIN) @Consumes(MediaType.TEXT_PLAIN)
public class PingApi {
    
    @Context
    private HttpServletRequest request;
    
    @EJB
    private PingEJB pingEJB;
    
    @Inject
    private PingCDI pingCDI;
    
    @Inject
    @ConfigProperty(name = "ping", defaultValue = "pong")
    private String ping;
    
    @GET
    @Path("/ping")
    @Consumes(MediaType.TEXT_PLAIN)
    public String ping() {
        try(StringWriter sw = new StringWriter()){
            
            sw.write("=== Example ===\n\n");
                    
            sw.write("JAX-RS Ping [" + ping +"]\n");
            
            String ejbping = pingEJB.getPing();
            sw.write("EJB Ping [" + ejbping + "]\n");
        
            String cdiping = pingCDI.getPing();
            sw.write("CDI Ping [" + cdiping + "]\n");
            
            sw.write("\n");
            sw.write("Running on " + request.getServletContext().getServerInfo() + "\n");
            
            String response = sw.toString();
            log.log(Level.SEVERE, ASCII_ART + "{0}", response);
            return response;   
        } catch (IOException ex) {
            return ex.getMessage();
        }
    }
 
    
    private static final String ASCII_ART = "\n\n" + 
"     ██╗ █████╗ ██╗   ██╗ █████╗ ███████╗███████╗\n" +
"     ██║██╔══██╗██║   ██║██╔══██╗██╔════╝██╔════╝\n" +
"     ██║███████║██║   ██║███████║█████╗  █████╗  \n" +
"██   ██║██╔══██║╚██╗ ██╔╝██╔══██║██╔══╝  ██╔══╝  \n" +
"╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║███████╗███████╗\n" +
" ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝╚══════╝╚══════╝\n" +
"                                                 \n" +
"\n .... with MicroProfile ....\n\n";
    
}