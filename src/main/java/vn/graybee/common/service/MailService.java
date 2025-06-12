package vn.graybee.common.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vn.graybee.auth.record.MailBody;
import vn.graybee.common.Constants;
import vn.graybee.common.config.MailProperties;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

@AllArgsConstructor
@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    private final MailProperties mailProperties;

    private final MessageSourceUtil messageSourceUtil;

    public void sendMail(MailBody mailBody) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailBody.to());
            message.setSubject(mailBody.subject());
            message.setText(mailBody.text());

            message.setFrom(mailProperties.getUsername());

            javaMailSender.send(message);

        } catch (RuntimeException e) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("common.error.send.mail"));
        }
    }

}
