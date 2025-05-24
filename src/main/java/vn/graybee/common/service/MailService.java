package vn.graybee.common.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vn.graybee.common.config.MailProperties;
import vn.graybee.record.MailBody;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    private final MailProperties mailProperties;

    public MailService(JavaMailSender javaMailSender, MailProperties mailProperties) {
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
    }

    public void sendMail(MailBody mailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.to());
        message.setSubject(mailBody.subject());
        message.setText(mailBody.text());

        message.setFrom(mailProperties.getUsername());

        javaMailSender.send(message);
    }

}
