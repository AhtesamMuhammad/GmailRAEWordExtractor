package org.example.Examen;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

public class EmailSender {

    private final String email;
    private final String password;
    private final String smtpHost;
    private final String smtpPort;

    public EmailSender(String email, String password, String smtpHost, String smtpPort) {
        this.email = email;
        this.password = password;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    public void sendEmail(Map<String, String> credentials, String subject, String content) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", smtpHost);
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        // Creation the message
        sendEmailMessage(session, credentials.get("addressee"), subject, content);
    }

    private void sendEmailMessage(Session session, String addressee, String subject, String content) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email)); // We are using the same email for send and receive
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressee));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
            System.out.println("Email sent to: " + addressee);
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
