package controller;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

import model.dao.KoszykDaneWalut;
import model.dao.Kursy;
import model.dao.ObslugaBD;

/**
 * Application Lifecycle Listener implementation class Sluchacz
 *
 */
@WebListener
public class Sluchacz implements ServletContextListener, ServletContextAttributeListener, ServletRequestListener {

    /**
     * Default constructor. 
     */
    public Sluchacz() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    public void attributeAdded(ServletContextAttributeEvent scae)  { 

    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    public void attributeRemoved(ServletContextAttributeEvent scae)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    public void attributeReplaced(ServletContextAttributeEvent scae)  { 

    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 

    }
    
    public void requestInitialized(ServletRequestEvent sre)  { 
    	Runnable kursy = new Kursy();
    	Thread t = new Thread(kursy);
    	t.start();
		ObslugaBD bd = new ObslugaBD();
		KoszykDaneWalut kdw = bd.daneBidAskWalut();
		ServletContext sc = sre.getServletContext();
		sc.setAttribute("mnoznik", kdw);
		try {
			t.join();
			sc.setAttribute("kurs", kursy);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   } 
	
}
