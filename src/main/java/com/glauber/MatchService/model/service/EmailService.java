package com.glauber.MatchService.model.service;

import com.glauber.MatchService.model.entity.PriceAlert;
import com.glauber.MatchService.model.entity.Product;
import com.glauber.MatchService.model.exception.EmailSendException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
//TODO NÃO ESTÁ SENDO ENVIADO O EMAIL AVISANDO DO RANGE DE PREÇO
@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private MatchService matchService;


    public void sendAlertConfirmation(PriceAlert priceAlert) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("noreply@mylowprice.com");
            message.setTo(priceAlert.getEmail());
            message.setSubject("Alerta de Preco Cadastrado");
            message.setText("Seu alerta de preco foi cadastrado com sucesso!");

            emailSender.send(message);

        } catch (MailException ex) {
            throw new EmailSendException("Erro ao enviar e-mail de confirmação do alerta. ");
        }
    }

    public void sendMatchEmail(Product product) {
        try {
            Optional<PriceAlert> priceAlert = matchService.verifyMatches(product);

            if (priceAlert.isPresent()) {
                PriceAlert priceAlertFounded = priceAlert.get();
                SimpleMailMessage message = new SimpleMailMessage();

                message.setFrom("noreply@mylowprice.com");
                message.setTo(priceAlertFounded.getEmail());
                message.setSubject("Alerta de Preco: Match Encontrado");
                message.setText("O produto " + product.getName() +
                        " está no preco desejado abaixo de  " + priceAlertFounded.getPriceRange());
                emailSender.send(message);
            }
        } catch (MailException ex) {
            throw new EmailSendException("Erro ao enviar e-mail de notificação de match. ");
        }
    }
}
