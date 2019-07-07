package pl.dentistoffice.service;

import java.util.Properties;

import javax.mail.internet.*;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@PropertySource(value="/static/properties/mail.properties")
@Primary
public class SendEmailGoogleService implements SendEmail {

	@Override
	public boolean sendEmail(Environment env, String mailTo, String mailSubject, String mailText, String replyMail, byte[] attachment, String fileName) {
		
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(env.getProperty("mail.smtp.host"));
		javaMailSender.setPort(Integer.valueOf(env.getProperty("mail.smtp.port")));
		javaMailSender.setUsername(env.getProperty("mail_user"));
		javaMailSender.setPassword(env.getProperty("mail_password"));

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
		javaMailProperties.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
		javaMailProperties.put("mail.transport.protocol", env.getProperty("mail.transport.protocol"));
//		javaMailProperties.put("mail.debug", env.getProperty("mail.debug"));

		javaMailSender.setJavaMailProperties(javaMailProperties);
		
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setSubject(mailSubject);
                helper.setFrom(replyMail);
                helper.setTo(mailTo);
                helper.setText(mailText, true);
                helper.addAttachment(fileName, new ByteArrayResource(attachment));
			}
		};
		try {
			javaMailSender.send(messagePreparator);
		} catch (MailException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
