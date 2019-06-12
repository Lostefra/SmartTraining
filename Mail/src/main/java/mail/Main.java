package mail;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

public class Main {

	public static void mandaMail(String indirizzoMail, String header, String prodotti) {
		Email email = EmailBuilder.startingBlank()
						.from("smart.training.noreply@gmail.com")
						.to(indirizzoMail)
						.withSubject("Ricevuta acquisto del " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
						.withPlainText(header + "\n\n" + prodotti)
						//.withAttachment("fotoprova", new FileDataSource("C:\\Users\\Davide\\eclipse-workspace\\ProvaMail\\eskere.jfif"))
						.buildEmail();
		
		MailerBuilder
		  .withSMTPServer("smtp.gmail.com", 587, "smart.training.noreply@gmail.com", "lorenzomario")
		  .withTransportStrategy(TransportStrategy.SMTP_TLS)
		  .buildMailer()
		  .sendMail(email);

	}

}
