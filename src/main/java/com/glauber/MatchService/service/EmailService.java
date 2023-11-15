package com.glauber.MatchService.service;

import com.glauber.MatchService.domain.entity.PriceAlertEvent;
import com.glauber.MatchService.domain.entity.ProductEvent;
import com.glauber.MatchService.domain.exception.EmailSendException;
import com.glauber.MatchService.listeners.PriceAlertListener;
import com.glauber.MatchService.listeners.ProductListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
// TODO: PRECISO VALIDAR ATRAVÉS DE UM EMAIL PARA O USUÁRIO QUE O ALERTA FOI CRIADO
// TODO: PRECISO VERIFICAR NO MATCHSERVICE SE DEU MATCH NOS PREÇOS DAS ENTIDADES E ATRIBUIR O MÉTODO AO ENVIO DO EMAIL COM A INFO.
@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private PriceAlertListener priceAlertListener;
    @Autowired
    private ProductListener productListener;

    public void sendAlertConfirmation(String email) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("noreply@mylowprice.com");
            message.setTo(email);
            message.setSubject("Alerta de Preço Cadastrado");
            message.setText("Seu alerta de preço foi cadastrado com sucesso!");

            emailSender.send(message);

        } catch (MailException ex) {
            throw new EmailSendException("Erro ao enviar e-mail de confirmação do alerta. " + email);
        }
    }

    public void sendMatchEmail(PriceAlertEvent priceAlertEvent, ProductEvent productEvent) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("noreply@mylowprice.com");
            message.setTo(priceAlertEvent.getEmail());
            message.setSubject("Alerta de Preço - Match Encontrado");
            message.setText("O produto " + productEvent.getName() +
                    " está no preço desejado (abaixo de  " + priceAlertEvent.getPriceRange());

            emailSender.send(message);

        } catch (MailException ex) {
            throw new EmailSendException("Erro ao enviar e-mail de notificação de match. " + priceAlertEvent.getEmail());
        }
    }
}
