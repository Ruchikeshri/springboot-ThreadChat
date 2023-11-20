package com.example.springbootredditclone.service;


import com.example.springbootredditclone.exceptions.SpringRedditException;
import com.example.springbootredditclone.model.NotificationEmail;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;
    private static Logger logger = LoggerFactory.getLogger(MailService.class);

    @Async
    public void sendMail(NotificationEmail email) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("application.tool1@gmail.com");
            messageHelper.setTo(email.getReciptent());
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(mailContentBuilder.build(email.getBody()));

        };
        try {
            long start = System.currentTimeMillis();
            mailSender.send(messagePreparator);
            logger.info("Activation Email Sent!!! {} duration ms", System.currentTimeMillis() - start);
        } catch (MailException e) {
            logger.error("Exception occurred while sending mail", e);
            throw new SpringRedditException("Exception occured while sending email!!" + email.getReciptent(), e);
        }
    }
}
