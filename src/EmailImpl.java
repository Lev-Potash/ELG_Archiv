import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Properties;

public class EmailImpl {

    private static EmailImpl instance;

    public static EmailImpl getInstance() {
        if(instance == null){
            instance = new EmailImpl();
        }
        return instance;
    }


    public void sendEmail(String filename) throws IOException, MessagingException {
        String to = "l***********v@rambler.ru";
        String from = "l*****4@gmail.com";
        //String host = "127.0.0.1";

        //Properties properties = System.getProperties();
        //properties.setProperty("mail.smtp.host", host);
        Properties properties = new Properties();
        properties.load(Main.class/*.getClassLoader()*/.getResourceAsStream("mail.properties"));


        Session session = Session.getDefaultInstance(properties); // default session


        MimeMessage message = new MimeMessage(session); // email message

        message.setFrom(new InternetAddress(from)); // setting header fields

        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        message.setSubject("ELG_SYS. Отправка письма по электронной почте");

        // actual mail body
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Вам пришло письмо с архивом проекта!\n " +
                "Отправка письма по электронной почте на языке Java");

        // Create a multipar message
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();

        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);

        // Send the complete message parts
        message.setContent(multipart);

        // Send message
        Transport tr = session.getTransport();
        tr.connect(null, "******");
        tr.sendMessage(message, message.getAllRecipients());
        tr.close();
        System.out.println("Email Sent successfully....");


    }
}
