package com.freemarker.service;

import com.freemarker.dto.MailRequest;
import com.freemarker.dto.MailResponse;

public interface EmailService {
    MailResponse sendMail(MailRequest request);
}
