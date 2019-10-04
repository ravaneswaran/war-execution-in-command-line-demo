package demo.rc.web;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import demo.rc.web.servlets.BlockingIOServlet;
import demo.rc.web.servlets.NonBlockingIOServlet;

public class WebApplication{

	private static final Logger logger = Logger.getLogger(WebApplication.class.getName());

	private Server server;
	 
    public void start() throws Exception {
        this.server = new Server();
        ServerConnector connector = new ServerConnector(this.server);
        connector.setPort(8080);
        this.server.setConnectors(new Connector[] {connector});
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        this.server.setHandler(context);
        
        context.addServlet(new ServletHolder(new BlockingIOServlet()),"/greet");
        context.addServlet(new ServletHolder(new NonBlockingIOServlet()),"/non-blocking");
        
        this.server.start();
        this.server.join();
    }
    
    public static void main(String[] args) {
		WebApplication webApplication = new WebApplication();
		try {
			webApplication.start();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
