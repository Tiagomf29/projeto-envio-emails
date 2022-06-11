package br.com.envioEmail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import config.Autenticacao;

public class EnvioDeEmailHotmail {

	  static String emailDestinatario = "tiagomf92@gmail.com";
	  static String nomeDestinatario = "Teste";
	  static String emailRemetente = "tiagomf29@hotmail.com";
	  static String nomeRemetente = "Teste";
	  static String assunto = "Assunto do e-mail";
	  static String body = "Corpo da mensagem";

	  static String protocolo = "smtp";
	  static String servidor = "smtp.office365.com";  // do painel de controle do SMTP
	  static String username = "tiagomf29@hotmail.com"; // do painel de controle do SMTP
	  static String senha = Autenticacao.senhaHotmail; // do painel de controle do SMTP
	  static String porta = "587";   // do painel de controle do SMTP

	  public static void main(String[] args)
	  {
	    Properties props = new Properties();
	    props.put("mail.transport.protocol", protocolo);
	    props.put("mail.smtp.host", servidor);
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.port", porta);
	   
	    props.put("mail.smtp.starttls.required", true); 
	    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

	    Session session = Session.getDefaultInstance(props, null);
	    session.setDebug(false);

	    try
	    {
	      InternetAddress iaFrom = new InternetAddress(emailRemetente, nomeRemetente);
	      InternetAddress[] iaTo = new InternetAddress[1];
	      InternetAddress[] iaReplyTo = new InternetAddress[1];

	      iaReplyTo[0] = new InternetAddress(emailDestinatario, nomeDestinatario);
	      iaTo[0] = new InternetAddress(emailDestinatario, nomeDestinatario);

	      MimeMessage msg = new MimeMessage(session);

	      if (iaReplyTo != null)
	        msg.setReplyTo(iaReplyTo);
	      if (iaFrom != null)
	        msg.setFrom(iaFrom);
	      if (iaTo.length > 0)
	        msg.setRecipients(Message.RecipientType.TO, iaTo);
	      msg.setSubject(assunto);
	      msg.setSentDate(new Date());

	      msg.setContent(body, "text/html");

	      Transport tr = session.getTransport(protocolo);
	      
	      System.out.println("Enviando...");
	      tr.connect(servidor, username, senha);

	      msg.saveChanges();

	      tr.sendMessage(msg, msg.getAllRecipients());
	      tr.close();
	      
	      System.out.println("Hotmail - Enviado com sucesso!");

	    }
	    catch (UnsupportedEncodingException e)
	    {
	      e.printStackTrace();
	    }
	    catch (MessagingException e)
	    {
	      e.printStackTrace();
	    }
	  }
	
}
