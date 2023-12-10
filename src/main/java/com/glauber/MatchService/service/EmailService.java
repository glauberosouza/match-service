package com.glauber.MatchService.service;

import com.glauber.MatchService.domain.entity.PriceAlert;
import com.glauber.MatchService.domain.entity.Product;
import com.glauber.MatchService.domain.exception.EmailSendException;
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

    public void sendAlertConfirmation(PriceAlert priceAlert) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("noreply@mylowprice.com");
            message.setTo(priceAlert.getEmail());
            message.setSubject("Alerta de Preço Cadastrado");
            message.setText("Seu alerta de preço foi cadastrado com sucesso!");

            emailSender.send(message);

        } catch (MailException ex) {
            throw new EmailSendException("Erro ao enviar e-mail de confirmação do alerta. ");
        }
    }

    public void sendMatchEmail(PriceAlert priceAlert, Product product) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("noreply@mylowprice.com");
            message.setTo(priceAlert.getEmail());
            message.setSubject("Alerta de Preço: Match Encontrado");
            message.setText("O produto " + product.getName() +
                    " está no preço desejado abaixo de  " + priceAlert.getPriceRange());

            emailSender.send(message);

        } catch (MailException ex) {
            throw new EmailSendException("Erro ao enviar e-mail de notificação de match. " + priceAlert.getEmail());
        }
    }
}
