package com.glauber.MatchService.model.service;

import com.glauber.MatchService.controller.exception.EmailSendException;
import com.glauber.MatchService.model.entity.PriceAlert;
import com.glauber.MatchService.model.entity.Product;
import com.glauber.MatchService.templates.priceAlertTemplate.PriceAlertRequestTemplate;
import com.glauber.MatchService.templates.productTemplate.ProductRequestTemplate;
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

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private MatchService matchService;

    @InjectMocks
    private EmailService emailService;
    private PriceAlert createPriceAlert() {
        var creation = PriceAlertRequestTemplate.creation();
        var priceAlert = new PriceAlert();
        priceAlert.setName(creation.getName());
        priceAlert.setProductName(creation.getProductName());
        priceAlert.setPriceRange(creation.getPriceRange());
        priceAlert.setEmail(creation.getEmail());
        return priceAlert;
    }


    @Test
    @DisplayName("Deve enviar um email confirmando o cadastro do alerta")
    public void shouldSendEmailConfirmationOnAlertSubscription() {
        var priceAlertTest = PriceAlertRequestTemplate.creation();

        var priceAlert = new PriceAlert();
        priceAlert.setName(priceAlertTest.getName());
        priceAlert.setProductName(priceAlertTest.getProductName());
        priceAlert.setPriceRange(priceAlertTest.getPriceRange());
        priceAlert.setEmail(priceAlertTest.getEmail());

        // Arrange
        // Não quero a execução real, quero que não faça nada quando o método send for chamado.
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendAlertConfirmation(priceAlert);

        //Assert
        // Captura argumentos passados para métodos mockados, coloquei dentro de uma variavel para conseguir o valor
        // do SimpleMailMessage
        var messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertNotNull(sentMessage);
    }

    @Test
    @DisplayName("Deve ocorrer uma falha no envio de confirmação do cadastro do alerta")
    public void shouldFailWhenTryToSendEmailOfConfirmationOnAlertSubscription() {
        var priceAlertTest = PriceAlertRequestTemplate.creation();

        var priceAlert = new PriceAlert();
        priceAlert.setName(priceAlertTest.getName());
        priceAlert.setProductName(priceAlertTest.getProductName());
        priceAlert.setPriceRange(priceAlertTest.getPriceRange());
        priceAlert.setEmail(null);
        // Arrange
        Mockito.doThrow(new MailSendException("Erro ao enviar e-mail de confirmação do alerta. "))
                .when(javaMailSender)
                .send(Mockito.any(SimpleMailMessage.class));
        // Act and Assert
        assertThrows(EmailSendException.class, () -> emailService.sendAlertConfirmation(priceAlert));
    }

    @Test
    @DisplayName("Deve enviar um email se for encontrado match entre o preço e o range de preço do alerta")
    public void shouldSendEmailOfConfirmationOfMatchPrice() {
        // Arrange
        var creation = ProductRequestTemplate.creation();
        var product = new Product();
        product.setName(creation.getName());
        product.setLink(creation.getLink());
        product.setPrice(creation.getPrice());

        Mockito.doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        Mockito.when(matchService.verifyMatches(product)).thenReturn(Optional.of(createPriceAlert()));

        // Act
        emailService.sendMatchEmail(product);

        // Assert
        var messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertNotNull(sentMessage);

    }
    @Test
    @DisplayName("Deve falhar ao tentar enviar email de match entre o preço do produto e o range do alerta")
    public void shouldFailWhenTryToSendEmailOfConfirmationOfMatchPrice(){

        var productTest = ProductRequestTemplate.creation();

        var product = new Product();
        product.setName(productTest.getName());
        product.setLink(productTest.getLink());
        product.setPrice(BigDecimal.valueOf(10000.0));

        Mockito.when(matchService.verifyMatches(product)).thenReturn(Optional.of(createPriceAlert()));
        Mockito.doThrow(new MailSendException("Erro ao enviar e-mail de notificação de match. "))
                .when(javaMailSender)
                .send(Mockito.any(SimpleMailMessage.class));

        assertThrows(EmailSendException.class, ()
                -> emailService.sendMatchEmail(product));

    }
}