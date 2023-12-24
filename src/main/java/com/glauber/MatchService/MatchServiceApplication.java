package com.glauber.MatchService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
//TODO CRIAR TODOS OS EXCEPTION HANDLERS
//TODO CONSERTAR TODOS OS TESTES QUE QUEBRARAM
//TODO VERIFICAR A POSSIVEL CORREÇÃO DE BUGS COMO PRODUTOS REPETIDOS.
@SpringBootApplication
@EnableKafka
public class MatchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchServiceApplication.class, args);
	}

}
