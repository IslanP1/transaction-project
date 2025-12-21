package service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ClienteRepository;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public void salvarCliente(String nome, String email, String telefone) {
        clienteRepository.salvarCliente(nome, email, telefone);
    }
}
