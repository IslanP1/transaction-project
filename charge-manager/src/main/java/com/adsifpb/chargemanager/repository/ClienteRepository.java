package com.adsifpb.chargemanager.repository;

import com.adsifpb.chargemanager.model.Cliente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

// Se atentar aos principios SOLID -> Single Responsibility Principle
// Se atentar aos principios de Clean Architecture -> Repositorio deve depender de Model e nao o contrario
// Evitar logica de negocio dentro do repositorio
// Se atentar ao Singleton Pattern -> Repositorio deve ser singleton
@Repository
public class ClienteRepository {
    private final JdbcTemplate jdbcTemplate;

    public ClienteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Cliente> clienteRowMapper = (rs, rowNum) -> {
        String nome = rs.getString("nome");
        String email = rs.getString("email");
        String telefone = rs.getString("telefone");
        return new Cliente(nome, email, telefone);
    };

    public void salvarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, email, telefone) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, cliente.getNome(), cliente.getEmail(), cliente.getTelefone());
    }

//    public void listarClientes() {
//        String sql = "SELECT * FROM clientes";
//        jdbcTemplate.query(sql, (rs, rowNum) -> {
//            System.out.println("Nome: " + rs.getString("nome") + ", Email: " + rs.getString("email") + ", Telefone: " + rs.getString("telefone"));
//            return null;
//        });
//    }
//
//    public void atualizarCliente(String nome, String email, String telefone) {
//        String sql = "UPDATE clientes SET nome = ?, telefone = ? WHERE email = ?";
//        jdbcTemplate.update(sql, nome, telefone, email);
//    }
//
//    public void deletarCliente(String email) {
//        String sql = "DELETE FROM clientes WHERE email = ?";
//        jdbcTemplate.update(sql, email);
//    }
//
//    public String buscarClientePorEmail(String email) {
//        String sql = "SELECT nome FROM clientes WHERE email = ?";
//        return jdbcTemplate.queryForObject(sql, new Object[]{email}, String.class);
//    }
//
//    public boolean clienteExiste(String email) {
//        String sql = "SELECT COUNT(*) FROM clientes WHERE email = ?";
//        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
//        return count != null && count > 0;
//    }
}
