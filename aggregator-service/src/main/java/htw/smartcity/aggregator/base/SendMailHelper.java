package htw.smartcity.aggregator.base;

import htw.smartcity.aggregator.util.ConfigProperties;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * The type Send mail helper.
 */
public class SendMailHelper {
    private static final String mailUsername = ConfigProperties.MAIL_USERNAME;
    private static final String mailPassword = ConfigProperties.MAIL_PASSWORD;;
    private static final String mailList = ConfigProperties.MAIL_LIST;


    private SendMailHelper() {}

    /**
     * Send mail.
     *
     * @param subject     the subject
     * @param messageText the message text
     */
    public static void sendMail(String subject, String messageText) {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailUsername, mailPassword);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("smartcityaggregatorservice@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(mailList)
            );
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
