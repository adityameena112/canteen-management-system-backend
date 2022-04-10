package com.cms.controller;

import com.cms.utility.CustomMailSenderUtility;
import com.cms.utility.Mail;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    CustomMailSenderUtility customMailSenderUtility;

    @GetMapping("/sent")
    public Boolean sentMail(@RequestParam("email") String email) {
        customMailSenderUtility.sendMail(email, "Testing", "<h1>This is test email</h1>", new HashMap<>(), null, null);

        //        Mail mail = new Mail();
        //        mail.set
        //        customMailSenderUtility.send(null);
        return true;
    }
}
