package com.cms.utility.impl;

import com.cms.utility.CustomMailSenderUtility;
import com.cms.utility.Mail;
//import com.cms.utility.TemplateUtility;
import com.cms.utility.ValidationUtility;
import java.io.File;
import java.util.Map;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class CustomMailSenderUtilityImpl implements CustomMailSenderUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomMailSenderUtilityImpl.class);

    @Value("${spring.mail.username}")
    private String username; // = "teampranzo19@gmail.com";

    @Value("${spring.mail.password}")
    private String password; // = "mqkblwzixqpefnmn";

    @Autowired
    private JavaMailSender mailSender;

    //    @Autowired
    //    private TemplateUtility templateUtility;

    @Override
    public void send(final Mail mail) {
        try {
            sendMail(mail);
            LOGGER.info("Mail send to {} is successfully done", mail.getTo());
        } catch (Exception mex) {
            LOGGER.error("Error in custom mail sender utility ", mex);
        }
    }

    public void sendMail(final Mail mail) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.getTo()));
                mimeMessage.setFrom(new InternetAddress(mail.getFrom()));
                mimeMessage.setSubject(mail.getSubject());
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setText(mail.getBody(), true);
                if (!ValidationUtility.isEmpty(mail.getAttach())) helper.addAttachment(
                    mail.getAttach().getName(),
                    new FileSystemResource(mail.getAttach().getAbsolutePath())
                );
            }
        };

        try {
            mailSender.send(preparator);
        } catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void sendMail(String sendTo, String subject, String body, Map<String, Object> params, String templateName, File attach) {
        String mailBody = null;
        if (!ValidationUtility.isEmpty(templateName)) {
            mailBody = body;
        } else if (!ValidationUtility.isEmpty(body)) {
            mailBody = body;
        }

        final Mail mail = new Mail();
        mail.setTo(sendTo);
        mail.setFrom(username);
        mail.setPassword(password);
        mail.setSubject(subject);
        mail.setBody(mailBody);
        mail.setUsername(username);
        if (!ValidationUtility.isEmpty(attach)) {
            mail.setAttach(attach);
        }
        send(mail);
    }
}
