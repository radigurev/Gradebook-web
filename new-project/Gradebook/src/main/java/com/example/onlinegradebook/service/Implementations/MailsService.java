package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.service.MailService;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
public class MailsService implements MailService {

    public static final int PORT = 465;
    public static final String SMTP = "web.jumphosting03.com";
    public static final String EMAIL = "noreply@rado-development.eu";
    public static final String PASSWORD = "B@nsko69";

    @Override
    public void sendMail(String email,String title, String body) {
        Properties props = new Properties();

        props.put("mail.smtp.host", SMTP); //SMTP Host
        props.put("mail.smtp.socketFactory.port", PORT); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", PORT);

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        };
        Session session = Session.getDefaultInstance(props, auth);
       sendEmail(session, email, title, body);
    }

    private void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(EMAIL, "NoReply"));

            msg.setReplyTo(InternetAddress.parse(toEmail, false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(msg);

        }
        catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
