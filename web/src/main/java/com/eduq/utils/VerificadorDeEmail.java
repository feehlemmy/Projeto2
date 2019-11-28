package com.eduq.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.eduq.model.EmailFactory;
import com.eduq.model.entity.Email;
import com.eduq.model.service.EmailService;

@Component
@EnableScheduling
public class VerificadorDeEmail {
	
	private final long SEGUNDO = 1000;
	private final long MINUTO = SEGUNDO * 60;
	private final long CINCO_MINUTOS = MINUTO * 5;
	private final long DEZ_MINUTOS = MINUTO * 10;
	private final long QUINZE_MINUTOS = MINUTO * 15;
	private final long VINTE_MINUTOS = MINUTO * 20;
	private final long VINTE_E_CINCO_MINUTOS = MINUTO * 25;
	
	@Autowired
	private EmailService service;
	
	@Autowired(required = false)
	private EmailFactory emailFactory;
	  
	@Scheduled(fixedDelay = VINTE_E_CINCO_MINUTOS)
	public void verificarDuvidas() {
		Email email = buscarEmailDuvida();
		
		if (email != null) {
			emailFactory.sendMailDuvida(email);
			finalizarEmail(email);
		}
	}
	
	@Scheduled(fixedDelay = QUINZE_MINUTOS)
	public void verificarEmailSenha() {
		Email email = buscarEmailSenha();
		
		emailFactory.sendMailSenhaEscola(email);
		
		finalizarEmail(email);
	}
	
	@Scheduled(fixedDelay = VINTE_MINUTOS)
	public void verificarEmailSenhaProfessor() {
		Email email = buscarEmailSenhaProfessor();
		
		emailFactory.sendMailSenhaProfessor(email);
		
		finalizarEmail(email);
	}

	@Scheduled(fixedDelay = DEZ_MINUTOS)
	public void verificarEmailCadastroProfessor() {
		Email email = buscarEmailProfessor();
		
		emailFactory.sendMailCadastroProfessor(email);
		
		finalizarEmail(email);
	}
	
	@Scheduled(fixedDelay = CINCO_MINUTOS)
	public void verificarEmailCadastroEscola() {
		Email email = buscarEmailEscola();
		
		emailFactory.sendMail(email);
		
		finalizarEmail(email);
	}

	private Email buscarEmailEscola() {
		List<Email> emails = new ArrayList<Email>();
		Email email = new Email();
		
		emails = service.buscarPorNaoEnviadaEscola();
		email = emails.get(0);
		
		return email;
	}
	
	private Email buscarEmailSenha() {
		List<Email> emails = new ArrayList<Email>();
		Email email = new Email();
		
		emails = service.buscarPorNaoEnviadaSenhaEscola();
		email = emails.get(0);
		
		return email;
	}
	
	private Email buscarEmailSenhaProfessor() {
		List<Email> emails = new ArrayList<Email>();
		Email email = new Email();
		
		emails = service.buscarPorNaoEnviadaSenhaProfessor();
		email = emails.get(0);
		
		return email;
	}

	public Email buscarEmailDuvida() {
		List<Email> emails = new ArrayList<Email>(); 
		Email email = new Email();
		
		emails = service.buscarPorNaoEnviada();
		
		if (emails == null) {
			return null;
		} else {
			email = emails.get(0);
			return email;
		}
	}
	
	public Email buscarEmailProfessor() {
		List<Email> emails = new ArrayList<Email>();
		Email email = new Email();
		
		emails = service.buscarPorNaoEnviadaProfessor();
		email = emails.get(0);
		
		return email;
	}
	
	public void finalizarEmail(Email email) {
		email.setEnviado("sim");
		service.editar(email);
	}
}
