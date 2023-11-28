package com.glauber.MatchService.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class mailConfiguration {
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Estou utilizando o https://mailtrap.io/signin
        mailSender.setHost("sandbox.smtp.mailtrap.io");
        mailSender.setPort(2525);

        mailSender.setUsername("bc4bfed446613a");
        mailSender.setPassword("f38b9be7a3f1d0");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
