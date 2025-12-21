package repository;

import model.Cobranca;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class CobrancaRepository {
    private final JdbcTemplate jdbcTemplate;

    public CobrancaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Cobranca> cobrancaRowMapper = (rs, rowNum) -> {
        String id = rs.getString("id");
        String descricao = rs.getString("description");
        String quantia = rs.getString("amount");
        String dataVencimento = rs.getString("due_date");
        Integer status = rs.getInt("status");
        Integer metodoPagamento = rs.getInt("payment_method");
        return new Cobranca(id, descricao, Double.parseDouble(quantia), dataVencimento, status, metodoPagamento);
    };

    public void salvarCobranca(Cobranca cobranca) {
        String sql = "INSERT INTO cobrancas (id, description, amount, due_date, status, payment_method) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, cobranca.getId(), cobranca.getDescricao(), cobranca.getQuantia(), cobranca.getDataVencimento(), cobranca.getStatus(), cobranca.getMetodoPagamento());
    }
}
