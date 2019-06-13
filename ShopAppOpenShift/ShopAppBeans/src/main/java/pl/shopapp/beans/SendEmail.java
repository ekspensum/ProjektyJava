package pl.shopapp.beans;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class SendEmail {

    private String mailFrom = "testjava55@gmail.com";

    private Properties props;
    private Session session;
    private MimeMessage msg;
    private String username = "testjava55";
    private String password = "javatest@";

    public SendEmail() {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
	    session = Session.getInstance(props, new Authenticator() {
	    	@Override
	    	protected PasswordAuthentication getPasswordAuthentication() {
		    	// TODO Auto-generated method stub
		    	return new PasswordAuthentication(username, password);
	    }
		});
	    msg = new MimeMessage(session);
	}

    public boolean sendEmail(String mailTo, String mailSubject, String mailText) {		
    	try {
			msg.setFrom(new InternetAddress(mailFrom));
			msg.setRecipients(Message.RecipientType.TO, mailTo);
			msg.setSubject(mailSubject);
			msg.setText(mailText, "UTF-8", "html");
			Transport.send(msg);
			return true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;   	
    }
    
    public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

} 
