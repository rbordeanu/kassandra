import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailTest
{
    public static void main(String[] args)
    {
        String to = "madalina.toia@misys.com";
        final String from = "hr.misys@gmail.com";
        Properties properties = System.getProperties();

        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.user", "hr.misys@gmail.com");
        properties.put("mail.smtp.password", "Misys123");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "Misys123");
            }
        });

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Come to us!");
            message.setText("Job opportunity...");

            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}