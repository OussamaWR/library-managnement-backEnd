package com.inn.store.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to , String subject , String text, List<String> list ){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("teslawr07@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if(list !=null && list.size()>0)
        message.setCc(getCCArray(list));

        emailSender.send(message);

    }

    private String[] getCCArray(List<String> ccList){
        String[] cc=new String[ccList.size()];
        for(int i=0;i<ccList.size();i++){
            cc[i]=ccList.get(i);
        }
        return cc;
    }

    public void forgotMail(String to , String subject , String password) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom("teslawr07@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);


        String htmlMsg = "<p>" +
                    "<b><h2>Your Login details for Store Management System</h2></b><br>" +
                                "<h5>Email: </h5> "
                    + to + " <br><h5>Password: </h5> " + password + "<br>" +
                    "<a href=\"http://localhost:4200/\">Click here to login</a>" +
                "</p>";
        message.setContent(htmlMsg,"text/html");
        emailSender.send(message);
    }

}
