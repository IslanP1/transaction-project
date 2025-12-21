-- Tabela de Clientes
CREATE TABLE tb_client (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) NOT NULL UNIQUE,
                           phone VARCHAR(20)
);

-- Tabela de Status da Cobrança
CREATE TABLE tb_charge_status (
                                  id SERIAL PRIMARY KEY,
                                  code VARCHAR(30) NOT NULL UNIQUE, -- PENDING, REGISTERED, PAID...
                                  description VARCHAR(255) NOT NULL
);

INSERT INTO tb_charge_status (code, description) VALUES
                                                     ('PENDING', 'Cobrança pendente'),
                                                     ('REGISTERED', 'Cobrança registrada'),
                                                     ('PAID', 'Cobrança paga'),
                                                     ('CANCELED', 'Cobrança cancelada');

-- Tabela de Métodos de Pagamento
CREATE TABLE tb_charge_payment_method (
                                          id SERIAL PRIMARY KEY,
                                          code VARCHAR(30) NOT NULL UNIQUE, -- PIX, BOLETO, CARTAO_CREDITO...
                                          description VARCHAR(255) NOT NULL
);

INSERT INTO tb_charge_payment_method (code, description) VALUES
                                                             ('PIX', 'Pagamento via PIX'),
                                                             ('BOLETO', 'Pagamento via Boleto Bancário'),
                                                             ('CARTAO_CREDITO', 'Pagamento via Cartão de Crédito');

-- Tabela de Cobranças
CREATE TABLE tb_charge (
                           id SERIAL PRIMARY KEY,
                           description VARCHAR(255),
                           amount DECIMAL(10, 2) NOT NULL,
                           due_date DATE NOT NULL,
                           status_id INTEGER NOT NULL DEFAULT 1, -- PENDING
                           payment_method_id INTEGER NOT NULL,
                           client_id INTEGER NOT NULL,

                           CONSTRAINT fk_client
                               FOREIGN KEY (client_id)
                                   REFERENCES tb_client(id)
                                   ON DELETE RESTRICT,

                           CONSTRAINT fk_charge_status
                               FOREIGN KEY (status_id)
                                   REFERENCES tb_charge_status(id)
                                   ON DELETE RESTRICT,

                           CONSTRAINT fk_payment_method
                               FOREIGN KEY (payment_method_id)
                                   REFERENCES tb_charge_payment_method(id)
                                   ON DELETE RESTRICT
);
