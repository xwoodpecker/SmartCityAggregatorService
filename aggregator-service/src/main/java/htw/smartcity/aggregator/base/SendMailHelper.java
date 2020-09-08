package htw.smartcity.aggregator.base;

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
    private static final String mailPassword = ConfigProperties.MAIL_PASSWORD;
    private static final String mailList = ConfigProperties.MAIL_LIST;
    private static final String mailHost = ConfigProperties.MAIL_HOST;
    private static final String mailPort = ConfigProperties.MAIL_PORT;
    private static final String mailSender =  ConfigProperties.MAIL_SENDER;



    private SendMailHelper() {}

    /**
     * Send mail.
     *
     * @param subject     the subject
     * @param messageText the message text
     * @param adminList   the admin list
     */
    public static void sendMail(String subject, String messageText, String adminList) {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", mailHost);
        prop.put("mail.smtp.port", mailPort);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailUsername, mailPassword);
                    }
                });

        String finalMailList = mailList;
        if(adminList != null)
            finalMailList += "," + adminList;
        if(!finalMailList.isEmpty()) {
            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(mailSender));
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(finalMailList)
                );
                message.setSubject(subject);
                message.setText(messageText);

                Transport.send(message);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

}
