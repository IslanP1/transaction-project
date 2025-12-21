package com.adsifpb.chargemanager.service;

import com.adsifpb.chargemanager.model.Cobranca;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adsifpb.chargemanager.repository.CobrancaRepository;

@Service
public class CobrancaService {
    private final CobrancaRepository cobrancaRepository;

    public CobrancaService(CobrancaRepository cobrancaRepository) {
        this.cobrancaRepository = cobrancaRepository;
    }

    @Transactional
    public void emitirCobranca(Cobranca cobranca) {
        cobrancaRepository.salvarCobranca(cobranca);
    }
}
