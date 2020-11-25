package com.revature.catCollector;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import com.revature.catCollector.servlet.CatServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class App {
    public static void main(String[] args) throws Exception {
//        Tomcat tomcat = new Tomcat();
//        tomcat.setPort(8080);
//
//        // Set context path and root folder
//        String contextPath = "/";
//        String docBase = new File(".").getAbsolutePath();
//
//        Context context = tomcat.addContext(contextPath, docBase);
//
//        // Declare, define, and map servlets
//        CatServlet catServlet = new CatServlet();
//
//        String servletName = "CatServlet";
//        String urlPattern = "/cat";
//
//        // Register servlets with Tomcat
//        Tomcat.addServlet(context, servletName, catServlet);
//        context.addServletMappingDecoded(urlPattern, servletName);
//
//        // Start the server
//        tomcat.start();
//        tomcat.getServer().await();
    	
    	
    }
}