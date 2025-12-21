package com.adsifpb.chargemanager.service.email;


import com.adsifpb.chargemanager.model.cliente.Cliente;
import com.adsifpb.chargemanager.repository.cliente.ClienteSalvarRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final ClienteSalvarRepository clienteSalvarRepository;
    private final Cliente cliente;

    public EmailServiceImpl(JavaMailSender mailSender, ClienteSalvarRepository clienteSalvarRepository, Cliente cliente) {
        this.mailSender = mailSender;
        this.clienteSalvarRepository = clienteSalvarRepository;
        this.cliente = cliente;
    }

    @Override
    public void enviarStatusCobrancaAlterado(Long cobrancaId, Long clienteId, Long statusId) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(cliente.getEmail());
        message.setSubject("Atualização de status da cobrança");
        message.setText("""
            Olá %s,

            O status da sua cobrança (%d) foi atualizado.
            Novo status: %d

            Atenciosamente,
            Sistema de Cobranças
        """.formatted(cliente.getNome(), cobrancaId, statusId));

        mailSender.send(message);
    }
}
