package com.eduq.model;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import com.eduq.model.entity.Email;

import java.util.*;

@Service
public class EmailFactory {
	final String username = "eduqstudent@gmail.com";
	final String password = "EduqStudent123";
	private String mailSMTPServer;
	private String mailSMTPServerPort;
	String to;
	String message;
	String subject;
	
	EmailFactory() { //Para o GMAIL 
		mailSMTPServer = "smtp.gmail.com";
		mailSMTPServerPort = "465";
	}
	
	EmailFactory(String mailSMTPServer, String mailSMTPServerPort) { //Para outro Servidor
		this.mailSMTPServer = mailSMTPServer;
		this.mailSMTPServerPort = mailSMTPServerPort;
	}
	
	// Envio de e-mail no cadastro de uma escola
	public void sendMail(Email email) {
		Properties props = new Properties();
		to = email.getEmailEscola();
		
		props.put("mail.transport.protocol", "smtp"); //define protocolo de envio como SMTP
		props.put("mail.smtp.starttls.enable","true"); 
		props.put("mail.smtp.host", mailSMTPServer); //server SMTP do GMAIL
		props.put("mail.smtp.auth", "true"); //ativa autenticacao
		props.put("mail.smtp.user", username); //usuario ou seja, a conta que esta enviando o email (tem que ser do GMAIL)
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", mailSMTPServerPort); //porta
		props.put("mail.smtp.socketFactory.port", mailSMTPServerPort); //mesma porta para o socket
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		
		Session session = Session.getInstance(props, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, "EduqStudent123");
		    }
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(" [Não Responda] Pré cadastro EduQ Student Life");
			
			String msgContent = "<h1 style='font-size:24px;color:#5d666f;margin:0;padding:0;font-weight:bold'>Olá " + email.getNomeEscola() +"!</h1><hr/>"
					+ "<div style='padding:24px 40px 48px 40px;background-color:#ffffff'>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>Recebemos seus dados e está tudo certo!</p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>Estamos enviando este e-mail para que você possa completar seu cadastro.</p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>Por favor, clique no botão abaixo e informe os dados indicados. Logo após, você será levado para a página de login.</p></div>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>"+ "<a style='background-color:#21c25e;border:none;color:white;padding:15px 32px;text-align:center;text-decoration:none;display:inline-block;font-size:16px;' href='http://localhost:8080/completarCadastro'>Continuar</a>" + "</p>"
					+ "<div><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr><td><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'><td valign='top' align='center' style='padding:8px'></td></tr>"
					+ "</tbody></table><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'>"
					+ "<td valign='top' align='left' style='padding:40px 40px 0 40px;background-color:#ffffff;border-radius:5px'>"
					+ "<h2 style='font-size:24px;color:#5d666f;margin:0;padding:0;font-weight:bold'>Surgiu alguma dúvida?</h2></td></tr><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'>"
					+ "<td valign='top' align='left' style='padding:24px 40px 48px 40px;background-color:#ffffff;border-radius:5px'><p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>Caso tenha alguma dúvida sobre a aplicação, consulte a seção <font style='color:#21c25e'>'Fale conosco'</font> na página principal do site.</p></td></tr></tbody></table></td></tr></tbody></table></div>";
			
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msgContent, "text/html;  charset=utf-8");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			message.setContent(multipart);
			
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Envio de e-mail no com uma dúvida de uma escola
	public void sendMailDuvida(Email email) {
		Properties props = new Properties();
		to = "eduqstudent@gmail.com";
		
		props.put("mail.transport.protocol", "smtp"); //define protocolo de envio como SMTP
		props.put("mail.smtp.starttls.enable","true"); 
		props.put("mail.smtp.host", mailSMTPServer); //server SMTP do GMAIL
		props.put("mail.smtp.auth", "true"); //ativa autenticacao
		props.put("mail.smtp.user", username); //usuario ou seja, a conta que esta enviando o email (tem que ser do GMAIL)
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", mailSMTPServerPort); //porta
		props.put("mail.smtp.socketFactory.port", mailSMTPServerPort); //mesma porta para o socket
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		
		Session session = Session.getInstance(props, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, "EduqStudent123");
		    }
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(email.getEmailEscola()));
			message.setSubject("[Dúvida] EduQ Student Life : " + email.getTituloDuvida());
			
			String msgContent = "<h1 style='font-size:24px;color:#5d666f;margin:0;padding:0;font-weight:bold'>Olá, " + email.getNomeEscola() + "!</h1><hr/>"
					+ "<div style='padding:24px 40px 48px 40px;background-color:#ffffff'>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>" + email.getDescricaoDuvida() + "</p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>Escola: " + email.getNomeEscola() +"</p>"
					+ "<div><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr><td><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'><td valign='top' align='center' style='padding:8px'></td></tr>"
					+ "</tbody></table><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'>"
					+ "<td valign='top' align='left' style='padding:40px 40px 0 40px;background-color:#ffffff;border-radius:5px'>"
					+ "</td></tr><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'>"
					+ "</tr></tbody></table></td></tr></tbody></table></div>";
			
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msgContent, "text/html;  charset=utf-8");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			message.setContent(multipart);
			
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Envio de e-mail no cadastro de um professor por uma escola
	public void sendMailCadastroProfessor(Email email) {
		Properties props = new Properties();
		to = email.getEmailProfessor();
		
		props.put("mail.transport.protocol", "smtp"); //define protocolo de envio como SMTP
		props.put("mail.smtp.starttls.enable","true"); 
		props.put("mail.smtp.host", mailSMTPServer); //server SMTP do GMAIL
		props.put("mail.smtp.auth", "true"); //ativa autenticacao
		props.put("mail.smtp.user", username); //usuario ou seja, a conta que esta enviando o email (tem que ser do GMAIL)
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", mailSMTPServerPort); //porta
		props.put("mail.smtp.socketFactory.port", mailSMTPServerPort); //mesma porta para o socket
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		
		Session session = Session.getInstance(props, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, "EduqStudent123");
		    }
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(email.getEmailEscola()));
			message.setSubject("[Cadastro] EduQ Student Life");
			
			String msgContent = "<h1 style='font-size:24px;color:#5d666f;margin:0;padding:0;font-weight:bold'>Olá " + email.getNomeProfessor() + "!</h1><hr/>"
					+ "<div style='padding:24px 40px 48px 40px;background-color:#ffffff'>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>"
					+ "Informamos que você foi cadastrado como professor na plataforma EduQ Student Life pela escola <strong>" + email.getNomeEscola() + ".</strong></p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>Para acessar sua conta, basta acessar o link abaixo e inserir os dados indicados.</p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>Seguem abaixo suas informações para se autenticar na plataforma:</p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'><strong>Código de acesso: " + email.getCodigoProfessor() + "</strong></p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'><strong>Senha: " + email.getSenhaProfessor() + "</strong></p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>"+ "<a style='background-color:#21c25e;border:none;color:white;padding:15px 32px;text-align:center;text-decoration:none;display:inline-block;font-size:16px;' href='http://localhost:8080/entrarProfessor'>Entrar</a>" + "</p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'><strong>Obs.</strong>: Por questões de segurança, altere sua senha em seu primeiro acesso.</p>"
					+ "<div><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr><td><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'><td valign='top' align='center' style='padding:8px'></td></tr>"
					+ "</tbody></table><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'>"
					+ "<td valign='top' align='left' style='padding:40px 40px 0 40px;background-color:#ffffff;border-radius:5px'>"
					+ "</td></tr><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'>"
					+ "</tr></tbody></table></td></tr></tbody></table></div>";
			
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msgContent, "text/html;  charset=utf-8");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			message.setContent(multipart);
			
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Envio de e-mail no reset de senha para uma Escola
	public void sendMailSenhaEscola(Email email) {
		Properties props = new Properties();
		to = email.getEmailEscola();
		
		props.put("mail.transport.protocol", "smtp"); //define protocolo de envio como SMTP
		props.put("mail.smtp.starttls.enable","true"); 
		props.put("mail.smtp.host", mailSMTPServer); //server SMTP do GMAIL
		props.put("mail.smtp.auth", "true"); //ativa autenticacao
		props.put("mail.smtp.user", username); //usuario ou seja, a conta que esta enviando o email (tem que ser do GMAIL)
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", mailSMTPServerPort); //porta
		props.put("mail.smtp.socketFactory.port", mailSMTPServerPort); //mesma porta para o socket
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		
		Session session = Session.getInstance(props, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, "EduqStudent123");
		    }
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("[Restauração de senha] EduQ Student Life");
			
			String msgContent = "<h1 style='font-size:24px;color:#5d666f;margin:0;padding:0;font-weight:bold'>Olá " + email.getNomeEscola() + "!</h1><hr/>"
					+ "<div style='padding:24px 40px 48px 40px;background-color:#ffffff'>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>Uma nova senha foi gerada para a sua conta.</p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>Para acessar sua conta, basta acessar o link abaixo, inserindo seu código do MEC e a senha indicada abaixo.</p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'><strong>Senha: " + email.getSenhaEscola() + "</strong></p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>"+ "<a style='background-color:#21c25e;border:none;color:white;padding:15px 32px;text-align:center;text-decoration:none;display:inline-block;font-size:16px;' href='http://localhost:8080/entrar'>Entrar</a></p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'><strong>Obs.</strong>: Por questões de segurança, altere sua senha assim que realizar seu acesso.</p>"
					+ "<div><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr><td><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'><td valign='top' align='center' style='padding:8px'></td></tr>"
					+ "</tbody></table><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'>"
					+ "<td valign='top' align='left' style='padding:40px 40px 0 40px;background-color:#ffffff;border-radius:5px'>"
					+ "</td></tr><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'>"
					+ "</tr></tbody></table></td></tr></tbody></table></div>";
			
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msgContent, "text/html;  charset=utf-8");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			message.setContent(multipart);
			
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void sendMailSenhaProfessor(Email email) {
		Properties props = new Properties();
		to = email.getEmailProfessor();
		
		props.put("mail.transport.protocol", "smtp"); //define protocolo de envio como SMTP
		props.put("mail.smtp.starttls.enable","true"); 
		props.put("mail.smtp.host", mailSMTPServer); //server SMTP do GMAIL
		props.put("mail.smtp.auth", "true"); //ativa autenticacao
		props.put("mail.smtp.user", username); //usuario ou seja, a conta que esta enviando o email (tem que ser do GMAIL)
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", mailSMTPServerPort); //porta
		props.put("mail.smtp.socketFactory.port", mailSMTPServerPort); //mesma porta para o socket
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		
		Session session = Session.getInstance(props, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, "EduqStudent123");
		    }
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("[Restauração de senha] EduQ Student Life");
			
			String msgContent = "<h1 style='font-size:24px;color:#5d666f;margin:0;padding:0;font-weight:bold'>Olá " + email.getNomeProfessor() + "!</h1><hr/>"
					+ "<div style='padding:24px 40px 48px 40px;background-color:#ffffff'>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>Uma nova senha foi gerada para a sua conta.</p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>Para acessar sua conta, basta acessar o link abaixo, inserir seu código de acesso e a senha indicada abaixo.</p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'><strong>Senha: " + email.getSenhaProfessor() + "</strong></p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'>"+ "<a style='background-color:#21c25e;border:none;color:white;padding:15px 32px;text-align:center;text-decoration:none;display:inline-block;font-size:16px;' href='http://localhost:8080/entrarProfessor'>Entrar</a></p>"
					+ "<p style='font-size:14px;color:#5d666f;margin:0;padding:0;font-weight:normal;line-height:24px;text-align:left;color:#5d666f'><strong>Obs.</strong>: Por questões de segurança, altere sua senha assim que realizar seu acesso.</p>"
					+ "<div><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr><td><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'><td valign='top' align='center' style='padding:8px'></td></tr>"
					+ "</tbody></table><table border='0' cellpadding='0' cellspacing='0' width='100%' style='margin:0;padding:0;max-width:600px'>"
					+ "<tbody><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'>"
					+ "<td valign='top' align='left' style='padding:40px 40px 0 40px;background-color:#ffffff;border-radius:5px'>"
					+ "</td></tr><tr style='margin:0;padding:0;font-family:'Open sans',sans-serif'>"
					+ "</tr></tbody></table></td></tr></tbody></table></div>";
			
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msgContent, "text/html;  charset=utf-8");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			message.setContent(multipart);
			
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
