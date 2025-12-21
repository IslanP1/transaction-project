-- V1__create_tables.sql

-- Tabela de Clientes
CREATE TABLE tb_client (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20)
);

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

-- Tabela de Cobranças (Charge)
-- Status baseados no requisito [f4]: PENDING, REGISTERED, CANCELED, PAID
CREATE TABLE tb_charge (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    amount DECIMAL(10, 2) NOT NULL,
    due_date DATE NOT NULL,
    status_id INTEGER NOT NULL SET DEFAULT 1, -- Referência para tb_charge_status [f4]
    payment_method VARCHAR(20), -- PIX, BOLETO, CARTAO_CREDITO [f3]
    client_id INTEGER NOT NULL,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES tb_client(id) ON DELETE RESTRICT,
    CONSTRAINT fk_charge_status FOREIGN KEY (status_id) REFERENCES tb_charge_status(id) ON DELETE RESTRICT
);
