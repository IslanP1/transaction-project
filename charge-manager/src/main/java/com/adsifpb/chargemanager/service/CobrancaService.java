package com.adsifpb.chargemanager.service;

import com.adsifpb.chargemanager.model.Cobranca;
import org.springframework.stereotype.Service;
import com.adsifpb.chargemanager.repository.CobrancaRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CobrancaService {
    private final CobrancaRepository cobrancaRepository;

    public CobrancaService(CobrancaRepository cobrancaRepository) {
        this.cobrancaRepository = cobrancaRepository;
    }

    @Transactional
    public Cobranca efetuar(Cobranca cobranca) {
        return cobrancaRepository.efetuar(cobranca);
    }
}
