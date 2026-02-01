package com.adsifpb.chargemanager.repository.cobranca;

import com.adsifpb.chargemanager.model.cobranca.Cobranca;

public interface CobrancaEfetuarRepository {
    Cobranca efetuar(Cobranca cobranca);
}
