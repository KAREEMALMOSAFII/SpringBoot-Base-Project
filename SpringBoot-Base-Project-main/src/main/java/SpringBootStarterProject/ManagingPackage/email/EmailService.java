package SpringBootStarterProject.ManagingPackage.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static SpringBootStarterProject.ManagingPackage.email.EmailUtils.*;

@Service
@RequiredArgsConstructor

public class EmailService {

    public static final String UTF_8_ENCODING = "UTF-8";
    private final JavaMailSender javaMailSender;
@Value("${spring.mail.username}")
private String fromMail;
private String host="http://localhost:8070";

    @Async
    public void sendMailcode(String name, String to,String code)
    {

        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();

        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject("NEW USER ACCOUNT VERIFICATION");
        simpleMailMessage.setText(getEmailMessageWithCode(name,host,code));
        simpleMailMessage.setTo(to);
        javaMailSender.send(simpleMailMessage);


    }

    @Async
    public void sendManagerMail(String name, String to,String ManagerEmail)
    {
        SimpleMailMessage simpleMailMessage= new SimpleMailMessage();

        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject("Promoting To Manager ");
        simpleMailMessage.setText(getPromotingMessage(name,ManagerEmail));
        simpleMailMessage.setTo(to);
        javaMailSender.send(simpleMailMessage);
    }



@Async
    public void sendMailtoken(String name, String to,String token)
    {

        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();

        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject("NEW USER ACCOUNT VERIFICATION");
     simpleMailMessage.setText(getEmailMessage(name,host,token));
        simpleMailMessage.setTo(to);
        javaMailSender.send(simpleMailMessage);


    }
    @Async
    public void sendMail(String mail, EmailStructure emailStructure)
    {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();

        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(emailStructure.getSubject());
        simpleMailMessage.setText(emailStructure.getMessage());
        simpleMailMessage.setTo(mail);
        javaMailSender.send(simpleMailMessage);


    }

    public void sendMimeMessageWithAttachments(String name, String to, String token) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject("NEW_USER_ACCOUNT_VERIFICATION");
            helper.setFrom(fromMail);
            helper.setTo(to);
            helper.setText(getEmailMessage(name, host, token));
            //Add attachments
            FileSystemResource fort = new FileSystemResource("C:\\Users\\karee\\Downloads\\photo_2023-08-12_02-03-58.jpg");

           // FileSystemResource fort = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/photo_2023-08-12_02-03-58.jpg"));
         //   FileSystemResource dog = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/dog.jpg"));
           // FileSystemResource homework = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/homework.docx"));
            helper.addAttachment(fort.getFilename(), fort);
            //helper.addAttachment(dog.getFilename(), dog);
           // helper.addAttachment(homework.getFilename(), homework);
            javaMailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }
    private MimeMessage getMimeMessage() {
        return javaMailSender.createMimeMessage();
    }

    private String getContentId(String filename) {
        return "<" + filename + ">";
    }
}
