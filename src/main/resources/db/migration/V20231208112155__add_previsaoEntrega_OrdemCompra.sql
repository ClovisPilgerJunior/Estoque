-- Adicionar a coluna 'nomeSolicitante' à tabela 'ordem_compra'
ALTER TABLE ordem_compra ADD COLUMN data_previsao_entrega varchar(255);

-- Adicionar a coluna 'nomeSolicitante' à tabela 'ordem_compra_aud'
ALTER TABLE ordem_compra_aud ADD COLUMN data_previsao_entrega varchar(255);