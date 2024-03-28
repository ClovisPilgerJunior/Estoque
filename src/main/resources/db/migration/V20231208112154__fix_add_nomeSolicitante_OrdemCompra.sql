-- Adicionar a coluna 'nomeSolicitante' à tabela 'ordem_compra'
ALTER TABLE ordem_compra RENAME COLUMN nomesolicitante TO nome_solicitante;

-- Adicionar a coluna 'nomeSolicitante' à tabela 'ordem_compra_aud'
ALTER TABLE ordem_compra_aud RENAME COLUMN nomesolicitante TO nome_solicitante;