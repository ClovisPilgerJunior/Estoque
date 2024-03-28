-- Adicionar a coluna 'nomeSolicitante' à tabela 'ordem_compra'
ALTER TABLE ordem_compra ADD COLUMN nomeSolicitante varchar(255);

-- Adicionar a coluna 'nomeSolicitante' à tabela 'ordem_compra_aud'
ALTER TABLE ordem_compra_aud ADD COLUMN nomeSolicitante varchar(255);