package Mailing;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws MessagingException {
        try {


            Properties properties = new Properties();
            properties.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
            properties.put("smtp.mail.port", "587");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.auth", "true");
            String username = "c6d4ba37ac0c1e";
            String password = "a76f805154f9b0";


            Session session = getSession(properties, username, password);
            Message message = new MimeMessage(session);
            message.setSubject("This is the subject for the message;");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static Session getSession(Properties properties, String username, String password) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }


   /* private static Session getSession(Properties properties, String username, String password) {
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }*/
}
