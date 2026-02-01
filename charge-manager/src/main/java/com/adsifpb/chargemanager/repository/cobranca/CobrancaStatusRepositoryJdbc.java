package com.adsifpb.chargemanager.repository.cobranca;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CobrancaStatusRepositoryJdbc implements CobrancaStatusRepository{
    private final JdbcTemplate jdbcTemplate;

    public CobrancaStatusRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void atualizarStatus(Long cobrancaId, Long clienteId, Long statusId) {
        String sql = """
            UPDATE tb_charge
            SET status_id = ?
            WHERE id = ?
              AND client_id = ?;
        """;

        int rows = jdbcTemplate.update(
                sql,
                statusId,
                cobrancaId,
                clienteId
        );

        if (rows == 0) {
            throw new IllegalArgumentException(
                    "Cobrança não encontrada ou cliente inválido"
            );
        }
    }
}
