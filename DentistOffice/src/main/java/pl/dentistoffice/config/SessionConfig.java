package pl.dentistoffice.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionConfig implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
//		se.getSession().setMaxInactiveInterval(1200);
		
//		for Heroku cloud
		se.getSession().setMaxInactiveInterval(Integer.valueOf(System.getenv("SESSION_INTERVAL")));

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
	}

}
