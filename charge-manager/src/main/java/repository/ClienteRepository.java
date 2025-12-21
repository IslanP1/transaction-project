package repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepository {
    private final JdbcTemplate jdbcTemplate;

    public ClienteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvarCliente(String nome, String email, String telefone) {
        String sql = "INSERT INTO clientes (nome, email, telefone) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, nome, email, telefone);
    }

    public void listarClientes() {
        String sql = "SELECT * FROM clientes";
        jdbcTemplate.query(sql, (rs, rowNum) -> {
            System.out.println("Nome: " + rs.getString("nome") + ", Email: " + rs.getString("email") + ", Telefone: " + rs.getString("telefone"));
            return null;
        });
    }

    public void atualizarCliente(String nome, String email, String telefone) {
        String sql = "UPDATE clientes SET nome = ?, telefone = ? WHERE email = ?";
        jdbcTemplate.update(sql, nome, telefone, email);
    }

    public void deletarCliente(String email) {
        String sql = "DELETE FROM clientes WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }

    public String buscarClientePorEmail(String email) {
        String sql = "SELECT nome FROM clientes WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, String.class);
    }

    public boolean clienteExiste(String email) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        return count != null && count > 0;
    }
}
