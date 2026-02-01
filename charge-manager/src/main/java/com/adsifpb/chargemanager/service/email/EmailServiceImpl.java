package com.adsifpb.chargemanager.service.email;


import com.adsifpb.chargemanager.model.cliente.Cliente;
import com.adsifpb.chargemanager.repository.cliente.buscar.ClienteBuscarRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final ClienteBuscarRepository clienteBuscarRepository;

    public EmailServiceImpl(JavaMailSender mailSender, ClienteBuscarRepository clienteBuscarRepository) {
        this.mailSender = mailSender;
        this.clienteBuscarRepository = clienteBuscarRepository;
    }

    @Override
    public void enviarStatusCobrancaAlterado(Long cobrancaId, Long clienteId, Long statusId) {
        try {
            // Busca o cliente pelo ID
            Cliente cliente = clienteBuscarRepository.buscarCliente(clienteId);

            if (cliente == null || cliente.getEmail() == null || cliente.getEmail().isBlank()) {
                System.out.println("Cliente não encontrado ou sem email. ClienteId: " + clienteId);
                return;
            }

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
            System.out.println("Email enviado com sucesso para: " + cliente.getEmail());
        } catch (Exception e) {
            System.err.println("ERRO ao enviar email: " + e.getMessage());
            System.err.println("ATENÇÃO: Configure uma App Password do Gmail em application.properties");
            System.err.println("Acesse: https://myaccount.google.com/apppasswords");
            e.printStackTrace();
        }
    }
}
