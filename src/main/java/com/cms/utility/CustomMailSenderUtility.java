package com.cms.utility;

import java.io.File;
import java.util.Map;

public interface CustomMailSenderUtility {
    public void send(final Mail mail);

    public void sendMail(String sendTo, String subject, String body, Map<String, Object> params, String templateName, File attach);
}
