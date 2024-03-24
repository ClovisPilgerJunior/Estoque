-- Alterar o tipo de dados do campo 'quantidade' para 'float' na tabela 'item_ordem_compra'
ALTER TABLE item_ordem_compra ALTER COLUMN quantidade TYPE float USING quantidade::float;

-- Alterar o tipo de dados do campo 'quantidade' para 'float' na tabela 'item_ordem_compra_aud'
ALTER TABLE item_ordem_compra_aud ALTER COLUMN quantidade TYPE float USING quantidade::float;

-- Alterar o tipo de dados do campo 'quantidade' para 'float' na tabela 'produto_entrada'
ALTER TABLE produto_entrada ALTER COLUMN quantidade TYPE float USING quantidade::float;

-- Alterar o tipo de dados do campo 'quantidade' para 'float' na tabela 'produto_entrada_aud'
ALTER TABLE produto_entrada_aud ALTER COLUMN quantidade TYPE float USING quantidade::float;

-- Alterar o tipo de dados do campo 'quantidade' para 'float' na tabela 'produto_perda'
ALTER TABLE produto_perda ALTER COLUMN quantidade TYPE float USING quantidade::float;

-- Alterar o tipo de dados do campo 'quantidade' para 'float' na tabela 'produto_perda_aud'
ALTER TABLE produto_perda_aud ALTER COLUMN quantidade TYPE float USING quantidade::float;

-- Alterar o tipo de dados do campo 'quantidade' para 'float' na tabela 'produto_saida'
ALTER TABLE produto_saida ALTER COLUMN quantidade TYPE float USING quantidade::float;

-- Alterar o tipo de dados do campo 'quantidade' para 'float' na tabela 'produto_saida_aud'
ALTER TABLE produto_saida_aud ALTER COLUMN quantidade TYPE float USING quantidade::float;
