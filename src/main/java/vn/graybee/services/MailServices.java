package vn.graybee.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vn.graybee.config.PrefixMailConfig;
import vn.graybee.record.MailBody;

@Service
public class MailServices {

    private final JavaMailSender javaMailSender;

    private final PrefixMailConfig prefixMailConfig;

    public MailServices(JavaMailSender javaMailSender, PrefixMailConfig prefixMailConfig) {
        this.javaMailSender = javaMailSender;
        this.prefixMailConfig = prefixMailConfig;
    }

    public void sendMail(MailBody mailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.to());
        message.setSubject(mailBody.subject());
        message.setText(mailBody.text());

        message.setFrom(prefixMailConfig.getUsername());

        javaMailSender.send(message);
    }

}
