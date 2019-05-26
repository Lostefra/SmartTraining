import javax.activation.FileDataSource;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

public class Main {

	public static void main(String[] args) {
		Email email = EmailBuilder.startingBlank()
						.from("provamailz25@gmail.com")
						.to("gfy00634@cndps.com")
						.withSubject("ProvaMeiiil")
						.withPlainText("Pierogaaaayyy")
						//.withAttachment("fotoprova", new FileDataSource("C:\\Users\\Davide\\eclipse-workspace\\ProvaMail\\eskere.jfif"))
						.buildEmail();
		
		MailerBuilder
		  .withSMTPServer("smtp.gmail.com", 587, "provamailz25@gmail.com", "lorenzomario")
		  .withTransportStrategy(TransportStrategy.SMTP_TLS)
		  .buildMailer()
		  .sendMail(email);

	}

}
