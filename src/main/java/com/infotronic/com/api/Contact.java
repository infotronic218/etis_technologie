package com.infotronic.com.api;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
public class Contact {
	  @Value("${app.email}")
	  String appMail ;
      @Autowired
	  private JavaMailSender javaMailSender;
      
      
	  @PostMapping("/api/contact/message")
      public boolean sendToAdmin(@RequestBody ContactMessage message) {
		  MimeMessage msg = javaMailSender.createMimeMessage();
		  MimeMessageHelper helper = new MimeMessageHelper(msg);
		  //System.out.println(message.toString());
		  try {
			  helper.setFrom(new InternetAddress(message.email));
			  helper.setTo(appMail);
			  helper.setReplyTo(message.email);
			  helper.setSubject(message.title);
			  Document doc = Jsoup.parse("<div></div>")	;	
			  doc.appendElement("h4").text(""+message.title);
			  doc.appendElement("p").text(""+message.body);
			  doc.appendElement("label").text("Message de  "+message.author);
			  doc.appendElement("a").attr("href", "#").text(""+message.email);
					  helper.setText(doc.toString(), true);
			  javaMailSender.send(msg);
			  return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    	  return false;
    	  
      }
 
}

class ContactMessage{
	  public ContactMessage() {
		  
	  }
	 public String author ;
	 public String email ;
	 public String title ;
	 public String body ;
	 
	@Override
	public String toString() {
		return "ContactMessage [author=" + author + ", email=" + email + ", title=" + title + ", body=" + body + "]";
	}
	 
}
