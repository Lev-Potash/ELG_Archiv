/*import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;*/

public class Main {
    public static void main(String[] args) throws Exception {
        Archiving.getInstance().Zip(
                "D:\\Курсы Spring Framework\\Новая папка\\Программы\\lab 1\\demo\\TEST_TASK\\archiving_and_send_to_email",
                "archiving_and_send_to_email.tar");

    }

}

