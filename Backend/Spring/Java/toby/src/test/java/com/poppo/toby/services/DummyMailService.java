package com.poppo.toby.services;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DummyMailService implements MailSender {
    // 아무고토 하지 않는다.
    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {

    }
}
