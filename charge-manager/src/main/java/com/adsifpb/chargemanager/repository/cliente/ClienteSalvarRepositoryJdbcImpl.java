package com.adsifpb.chargemanager.repository.cliente;

import com.adsifpb.chargemanager.model.cliente.Cliente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteSalvarRepositoryJdbcImpl implements ClienteSalvarRepository {

    private JdbcTemplate jdbcTemplate;

    public ClienteSalvarRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        String sql = """
            INSERT INTO tb_client
            (name, email, phone)
            VALUES (?, ?, ?)
        """;

        jdbcTemplate.update(
                sql,
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone()
        );
        return cliente;
    }
}
