package com.cursos2ee;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;
import java.security.ProtectionDomain;

/**
 * Created by usuario on 13/05/2017.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        WebAppContext webapp = new WebAppContext();
        ProtectionDomain domain = Main.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();
        webapp.setServer(server);
        webapp.setContextPath("/");
        webapp.setWar(location.toExternalForm());
        server.setHandler(webapp);

        server.start();
        server.join();
    }

}