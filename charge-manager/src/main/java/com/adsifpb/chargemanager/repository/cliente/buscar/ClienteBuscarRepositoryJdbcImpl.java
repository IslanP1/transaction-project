package com.adsifpb.chargemanager.repository.cliente.buscar;

import com.adsifpb.chargemanager.model.cliente.Cliente;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ClienteBuscarRepositoryJdbcImpl implements ClienteBuscarRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Cliente> clienteRowMapper;

    public ClienteBuscarRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.clienteRowMapper = new ClienteRowMapper();
    }

    @Override
    public Cliente buscarCliente(Long clienteId) {
        String sql = """
            SELECT id, name, email, phone
            FROM tb_client
            WHERE id = ?
        """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    clienteRowMapper,
                    clienteId
            );
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Cliente não encontrado com id: " + clienteId);
        }
    }

    /**
     * Classe responsável APENAS por mapear ResultSet -> Cliente
     */
    private static class ClienteRowMapper implements RowMapper<Cliente> {
        @Override
        public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Cliente(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone")
            );
        }
    }
}
