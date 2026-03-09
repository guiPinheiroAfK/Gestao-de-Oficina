-- Script de configuração do banco de dados para a Gestao-de-Oficina

-- Criação das tabelas
CREATE TABLE IF NOT EXISTS veiculos (
    placa VARCHAR(10) PRIMARY KEY,
    marca VARCHAR(50) NOT NULL DEFAULT 'Não Informada',
    modelo VARCHAR(100) NOT NULL,
    ano INT NOT NULL,
    tipo VARCHAR(10) NOT NULL
    );

CREATE TABLE IF NOT EXISTS pecas (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    estoque INT NOT NULL DEFAULT 0
    );

CREATE TABLE IF NOT EXISTS ordens_servico (
    id SERIAL PRIMARY KEY,
    veiculo_placa VARCHAR(10) REFERENCES veiculos(placa),
    valor_total DECIMAL(10, 2),
    status VARCHAR(20) DEFAULT 'ABERTA',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS itens_os (
    os_id INT REFERENCES ordens_servico(id) ON DELETE CASCADE,
    peca_id INT REFERENCES pecas(id),
    PRIMARY KEY (os_id, peca_id)
    );

-- Dados iniciais para teste (opcional)
INSERT INTO veiculos (placa, marca, modelo, ano, tipo)
VALUES ('GUI2O07', 'Nissan', '370z', 2013, 'CARRO')
    ON CONFLICT (placa) DO NOTHING;

VALUES ('KAM1O07', 'HONDA', 'CB 600F', 2011, 'MOTO')
    ON CONFLICT (placa) DO NOTHING;

INSERT INTO pecas (nome, valor, estoque)
VALUES ('Filtro de Óleo', 45.90, 10);