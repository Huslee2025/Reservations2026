package be.iccbxl.pid.reservationsspringboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.MimeMessageHelper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

  private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

  private final JavaMailSender mailSender;

  @Value("${spring.application.frontend-url}")
  private String frontendUrl;

  public void sendPasswordResetMail(String to, String token) {

    String url = frontendUrl + "/reset-password?token=" + token;

    String texte = "Vous avez demandé la réinitialisation du mot de passe.\n\n"
        + "Cliquez sur le lien suivant :\n" + url
        + "\n\nCe lien expire dans 1 heure."
        + "\nSi vous n'avez pas demandé cela, ignorez cet e-mail.";

    String html = """
        <h2>Réinitialisation du mot de passe</h2>

        <p>Vous avez demandé la réinitialisation de votre mot de passe.</p>

        <p>
            <a href="%s"
               style="display:inline-block; padding:12px 20px;
                      background-color:#0d6efd; color:white;
                      text-decoration:none; border-radius:5px;">
                Réinitialiser mon mot de passe
            </a>
        </p>

        <p>Ce lien expire dans 1 heure.</p>
        <p>Si vous n'avez pas demandé cela, ignorez cet e-mail.</p>
        """.formatted(url);

    MimeMessage message = mailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      helper.setFrom("no-reply@example.com"); // Adresse de test Mailtrap
      helper.setTo(to);
      helper.setSubject("Réinitialisation du mot de passe");
      helper.setText(texte, html);

      mailSender.send(message);

    } catch (MessagingException e) {
      logger.error("Erreur de préparation de l'e-mail", e);
      throw new MailSendException("Erreur de préparation de l'e-mail", e);

    } catch (MailException e) {
      logger.error("Échec de l'envoi de l'e-mail", e);
      throw e;
    }
  }
}
