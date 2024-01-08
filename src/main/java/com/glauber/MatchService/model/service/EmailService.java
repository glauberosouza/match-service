package com.glauber.MatchService.model.service;

import com.glauber.MatchService.controller.exception.EmailSendException;
import com.glauber.MatchService.model.entity.PriceAlert;
import com.glauber.MatchService.model.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
            message.setText(
                    String.format(" Olá seu alerta de preco foi cadastrado com sucesso " +
                                    "\n SEU NOME: %s " +
                                    "\n NOME DO PRODUTO: %s " +
                                    "\n PRECO: %s " +
                                    "\n E-MAIL CADASTRADO: %s",
                            priceAlert.getName(),
                            priceAlert.getProductName(),
                            priceAlert.getPriceRange().toString(),
                            priceAlert.getEmail())
            );
            emailSender.send(message);

        } catch (MailException ex) {
            throw new EmailSendException("Erro ao enviar e-mail de confirmação do alerta. ");
        }
    }

    public void sendMatchEmail(Product product) {
        try {
            var priceAlert = matchService.verifyMatches(product);
            var priceAlertFounded = priceAlert.get();
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("noreply@mylowprice.com");
            message.setTo(priceAlertFounded.getEmail());
            message.setSubject("Alerta de Preco: Match Encontrado");
            message.setText(String.format(" Olá possuimos no estoque o produto dentro do preco desejado! " +
                            "\n NOME DO PRODUTO: %s " +
                            "\n LINK DO PRODUTO: %s " +
                            "\n PRECO ATUAL: %s",
                    product.getName(),
                    product.getLink(),
                    product.getPrice().toString()));
            emailSender.send(message);

        } catch (MailException ex) {
            throw new EmailSendException("Erro ao enviar e-mail de notificação de match. ");
        }
    }
}
