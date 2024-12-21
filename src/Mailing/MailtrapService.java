package Mailing;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Properties;

//import static Mailing.Main.getSession;

public class MailtrapService {
    public static void main(String[] args) throws Exception {

        Properties properties = getProperties();
        String username = "c6d4ba37ac0c1e";
        String password = "a76f805154f9b0";
        Session session = getSession(properties, username, password);
        Message message = new MimeMessage (session);



       /* Session session = getSession(properties.properties(), properties.username(), result.password());
        Message message = new MimeMessage(session);*/
        message.setSubject("This is the subject for the message;");
        message.setText("Body of email here;");
        Multipart multipart = new MimeMultipart();
        BodyPart attachment = new MimeBodyPart();
        attachment.setFileName("MyCV.txt");
        FileDataSource ds = new FileDataSource("Cv.txt");
        DataHandler dataHandler = new DataHandler(ds);
        attachment.setDataHandler(dataHandler);

        BodyPart contentMessage = new MimeBodyPart();
        String body = """
                ‹div>
                <h1 style= "color:red;">Body of mail here</h1>
                 <img src="data:image/jpg;base64,%s width = 400"
                """.formatted(getImageAsBase64());


        contentMessage.setContent(body, "text/html");

        multipart.addBodyPart(attachment);
        multipart.addBodyPart(contentMessage);
        message.setContent(multipart);
        Transport.send(message);
        System.out.println("Message send successfully:");
    }

    private static String getImageAsBase64() throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        Path path = Path.of("photo_5994685623002320059_y.jpg");
        byte[] readAllBytes = Files.readAllBytes(path);
        return encoder.encodeToString(readAllBytes);
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        properties.put("smtp.mail.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }

    private static Session getSession(Properties properties, String username, String password) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}

