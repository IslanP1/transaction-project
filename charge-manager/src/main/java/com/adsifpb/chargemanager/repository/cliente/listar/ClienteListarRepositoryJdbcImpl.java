package com.adsifpb.chargemanager.repository.cliente.listar;

import com.adsifpb.chargemanager.model.cliente.Cliente;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClienteListarRepositoryJdbcImpl implements ClienteListarRepository{
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<Cliente> clienteRowMapper;

    public ClienteListarRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.clienteRowMapper = new ClienteRowMapper();
    }

    @Override
    public List<Cliente> listarClientes() {
        String sql = """
            SELECT id, name, email, phone
            FROM tb_client
        """;
        try {
            return jdbcTemplate.query(sql, clienteRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Nenhum cliente encontrado");
        }
    }

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
