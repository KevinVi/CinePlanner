package com.example.cinemaplanner.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Created by moi on 10/07/2017 for ZKY.
 * <p>
 * Using Thymelief to create mail templates
 */
@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Template restore password confirmation
     *
     * @param message link to restore
     * @return String template
     */
    String buildRestorePasswordConfirmation(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mail-restorepassword-confirmation", context);
    }

    /**
     * Template restore password restored
     *
     * @param message new password
     * @return String template
     */
    String buildRestorePasswordSend(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mail-restorepassword-restore", context);
    }

    /**
     * Template register account
     *
     * @param login    user login
     * @param password user password
     * @return String template
     */
    String buildAccountCreated(String login, String password) {
        Context context = new Context();
        context.setVariable("login", login);
        context.setVariable("password", password);
        return templateEngine.process("mail-accountcreated", context);
    }


}