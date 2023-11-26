package com.glauber.MatchService.service;

import com.glauber.MatchService.domain.entity.PriceAlertEvent;
import com.glauber.MatchService.domain.entity.ProductEvent;
import com.glauber.MatchService.domain.exception.EmailSendException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private EmailService emailService;

    @Test
    @DisplayName("Deve enviar um email confirmando o cadastro do alerta")
    public void shouldSendEmailConfirmationOnAlertSubscription() {
        // Arrange
        // Não quero a execução real, quero que não faça nada quando o método send for chamado.
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendAlertConfirmation("glauber@email.com");

        //Assert
        // Captura argumentos passados para métodos mockados, coloquei dentro de uma variavel para conseguir o valor
        // do SimpleMailMessage
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertNotNull(sentMessage);
        assertEquals("Seu alerta de preço foi cadastrado com sucesso!", sentMessage.getText());
    }

    @Test
    @DisplayName("Deve ocorrer uma falha no envio de confirmação do cadastro do alerta")
    public void shouldFailWhenTryToSendEmailOfConfirmationOnAlertSubscription() {
        // Arrange
        Mockito.doThrow(new MailSendException("Erro ao enviar e-mail de confirmação do alerta. "))
                .when(javaMailSender)
                .send(Mockito.any(SimpleMailMessage.class));
        // Act and Assert
        assertThrows(EmailSendException.class, () -> emailService.sendAlertConfirmation("glauber@email.com"));
    }

    @Test
    @DisplayName("Deve enviar um email se for encontrado match entre o preço e o range de preço do alerta")
    public void shouldSendEmailOfConfirmationOfMatchPrice() {
        // Arrange
        Mockito.doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        PriceAlertEvent priceAlertEvent = new PriceAlertEvent();
        priceAlertEvent.setEmail("glauber@email.com");
        ProductEvent productEvent = new ProductEvent();
        productEvent.setName("Xbox");

        // Act
        emailService.sendMatchEmail(priceAlertEvent, productEvent);


        // Assert
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();
        String[] usersEmail = sentMessage.getTo();
        String body = Arrays.stream(usersEmail).findFirst().get();

        assertNotNull(sentMessage);
        assertEquals("glauber@email.com", priceAlertEvent.getEmail());
        assertEquals("glauber@email.com", body);
        assertEquals("O produto " + productEvent.getName() +
                " está no preço desejado abaixo de  " + priceAlertEvent.getPriceRange(), sentMessage.getText());
    }
    @Test
    @DisplayName("Deve falhar ao tentar enviar email de match entre o preço do produto e o range do alerta")
    public void shouldFailWhenTryToSendEmailOfConfirmationOfMatchPrice(){
        PriceAlertEvent priceAlertEvent = new PriceAlertEvent();
        priceAlertEvent.setEmail("glauber@email.com");
        ProductEvent productEvent = new ProductEvent();
        productEvent.setName("Xbox");

        Mockito.doThrow(new MailSendException("Erro ao enviar e-mail de notificação de match. "))
                .when(javaMailSender)
                .send(Mockito.any(SimpleMailMessage.class));

        assertThrows(EmailSendException.class, ()
                -> emailService.sendMatchEmail(priceAlertEvent, productEvent));

    }
}