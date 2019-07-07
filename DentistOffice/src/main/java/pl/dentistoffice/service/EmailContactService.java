package pl.dentistoffice.service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Service;

@Service
public class EmailContactService {

	@Size(min=2, max=30)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String subject;
	
	@Size(min=2, max=200)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String message;
	
	@Email
	@NotEmpty
	private String replyMail;
	
	@Size(min=0, max=200000)
	private byte[] attachment;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReplyMail() {
		return replyMail;
	}
	public void setReplyMail(String replyMail) {
		this.replyMail = replyMail;
	}
	public byte[] getAttachment() {
		return attachment;
	}
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

}
