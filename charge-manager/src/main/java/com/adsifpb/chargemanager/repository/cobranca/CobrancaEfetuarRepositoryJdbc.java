package com.adsifpb.chargemanager.repository.cobranca;

import com.adsifpb.chargemanager.model.cobranca.Cobranca;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CobrancaEfetuarRepositoryJdbc implements CobrancaEfetuarRepository {
    private JdbcTemplate jdbcTemplate;

    public CobrancaEfetuarRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cobranca efetuar(Cobranca cobranca) {
        String sql = """
            INSERT INTO tb_charge
            (description, amount, due_date, status_id, payment_method_id, client_id)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        jdbcTemplate.update(
                sql,
                cobranca.getDescricao(),
                cobranca.getQuantia(),
                cobranca.getDataVencimento(),
                cobranca.getStatusId(),
                cobranca.getMetodoPagamentoId(),
                cobranca.getClienteId()
        );

        return cobranca;
    }
}
