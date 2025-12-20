-- V1__create_tables.sql

-- Tabela de Clientes
CREATE TABLE tb_client (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20)
);

-- Tabela de Cobranças (Charge)
-- Status baseados no requisito [f4]: PENDING, REGISTERED, CANCELED, PAID
CREATE TABLE tb_charge (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    amount DECIMAL(10, 2) NOT NULL,
    due_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    payment_method VARCHAR(20), -- PIX, BOLETO, CARTAO_CREDITO [f3]
    client_id INTEGER NOT NULL,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES tb_client(id)
);