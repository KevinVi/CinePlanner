package com.example.cinemaplanner.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 * Created by Kevin on 09/07/2017 for ZKY.
 */
@Service
public class MailService {

    //TODO: choisir tous les subjects
    private JavaMailSender mailSender;
    private MailContentBuilder mailContentBuilder;

    @Autowired
    public MailService(JavaMailSender mailSender, MailContentBuilder mailContentBuilder) {
        this.mailSender = mailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    public void restorePasswordConfirmation(String to, String text) {
        try {

            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setTo(to);
                messageHelper.setSubject("Demande de restauration de mot de passe");
                String content = mailContentBuilder.buildRestorePasswordConfirmation(text);
                messageHelper.setText(content, true);
            };

            mailSender.send(messagePreparator);
        } catch (MailException e) {
            e.printStackTrace();
            // runtime exception; compiler will not force you to handle it
        }
    }

    public void addToTeam(String to, String text) {
        try {

            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setTo(to);
                messageHelper.setSubject("Nouvelle Team Cineplanner");
                String content = mailContentBuilder.buildAddToTeam(text);
                messageHelper.setText(content, true);
            };

            mailSender.send(messagePreparator);
        } catch (MailException e) {
            e.printStackTrace();
            // runtime exception; compiler will not force you to handle it
        }
    }


    public void addToTeamNewAccount(String to, String text) {
        try {

            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setTo(to);
                messageHelper.setSubject("Rejoignez Cineplanner ! ");
                String content = mailContentBuilder.buildAddToTeamNewAccount(text);
                messageHelper.setText(content, true);
            };

            mailSender.send(messagePreparator);
        } catch (MailException e) {
            e.printStackTrace();
            // runtime exception; compiler will not force you to handle it
        }
    }

    public void restorePasswordSend(String to, String text) {
        try {

            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setTo(to);
                messageHelper.setSubject("Nouveau mot de passe");
                String content = mailContentBuilder.buildRestorePasswordSend(text);
                messageHelper.setText(content, true);
            };

            mailSender.send(messagePreparator);
        } catch (MailException e) {
            e.printStackTrace();
            // runtime exception; compiler will not force you to handle it
        }
    }

    public void accountCreated(String to, String login, String password) {
        try {

            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setTo(to);
                messageHelper.setSubject("Nouveau compte cineplanner");
                String content = mailContentBuilder.buildAccountCreated(login, password);
                messageHelper.setText(content, true);
            };

            mailSender.send(messagePreparator);
        } catch (MailException e) {
            e.printStackTrace();
            // runtime exception; compiler will not force you to handle it
        }
    }

}