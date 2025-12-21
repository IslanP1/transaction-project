package com.adsifpb.chargemanager.service.cobranca;

import com.adsifpb.chargemanager.model.cobranca.Cobranca;
import org.springframework.stereotype.Service;
import com.adsifpb.chargemanager.repository.cobranca.CobrancaEfetuarRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CobrancaEfetuarService {
    private final CobrancaEfetuarRepository cobrancaEfetuarRepository;

    public CobrancaEfetuarService(CobrancaEfetuarRepository cobrancaEfetuarRepository) {
        this.cobrancaEfetuarRepository = cobrancaEfetuarRepository;
    }

    @Transactional
    public Cobranca efetuar(Cobranca cobranca) {
        return cobrancaEfetuarRepository.efetuar(cobranca);
    }
}
