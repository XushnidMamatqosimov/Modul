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
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("xmamatqosimov@gmail.com"));


        message.setSubject("This is the subject for the message;");
        Multipart multipart = new MimeMultipart();
        // message.setText("Body of email here;");

        BodyPart attachment1 = new MimeBodyPart();
        attachment1.setFileName("MyCV.txt");
        FileDataSource ds = new FileDataSource("src/cv.txt");
        DataHandler dataHandler = new DataHandler(ds);
        attachment1.setDataHandler(dataHandler);

        BodyPart attachment2 = new MimeBodyPart();
        attachment2.setFileName("CodeSamples2.txt");
        FileDataSource ds2 = new FileDataSource("src/Mailing/samples.txt");
        DataHandler dataHandler2 = new DataHandler(ds2);
        attachment2.setDataHandler(dataHandler2);


        BodyPart contentMessage = new MimeBodyPart();
        String body = """
                ‹div>
                <h1 style="color:red;">Body of mail here</h1>
                <img src="data:image/jpg;base64,%s" width = 400>
                </div>
                """.formatted(getImageAsBase64());
        contentMessage.setContent(body, "text/html");



        multipart.addBodyPart(attachment1);
        multipart.addBodyPart(attachment2);
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

