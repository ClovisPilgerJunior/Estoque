-- Inserir um fornecedor ativo
INSERT INTO fornecedor (empresa, nome, tipo_empresa, email, telefone, endereco, ativo, created_date, last_modified_date, created_by, last_modified_by)
VALUES ('Ramatexyr', 'João', 1, 'joao@ramatex.com', '(48) 99999-0000', 'Rua das Palmeiras', true,  NOW(), NOW(), 'system', 'system');

-- Inserir um fornecedor inativo
INSERT INTO fornecedor (empresa, nome, tipo_empresa, email, telefone, endereco, ativo, created_date, last_modified_date, created_by, last_modified_by)
VALUES ('ABC Corp', 'Maria', 0, 'maria@abc.com', '(123) 456-7890', 'Avenida Principal', false,  NOW(), NOW(), 'system', 'system');

-- Inserir outro fornecedor ativo
INSERT INTO fornecedor (empresa, nome, tipo_empresa, email, telefone, endereco, ativo, created_date, last_modified_date, created_by, last_modified_by)
VALUES ('XYZ Ltda', 'Carlos', 1, 'carlos@xyz.com', '(987) 654-3210', 'Rua da Indústria', true,  NOW(), NOW(), 'system', 'system');

-- Produto Capa 1
INSERT INTO produto_capa (cod_sistema, description, tipo_produto, medida_unidade, fornecedor_id, minimo, maximo, resuprimento, ativo, created_date, last_modified_date, created_by, last_modified_by)
VALUES (null, 'Produto Capa 1', 0, 1, 1, 10, 100, 1, false, NOW(), NOW(), 'system', 'system');

-- Produto Capa 2
INSERT INTO produto_capa (cod_sistema, description, tipo_produto, medida_unidade, fornecedor_id, minimo, maximo, resuprimento, ativo, created_date, last_modified_date, created_by, last_modified_by)
VALUES (null, 'Produto Capa 2', 0, 2, 2, 20, 200, 2, true, NOW(), NOW(), 'system', 'system');

-- Produto Capa 3
INSERT INTO produto_capa (cod_sistema, description, tipo_produto, medida_unidade, fornecedor_id, minimo, maximo, resuprimento, ativo, created_date, last_modified_date, created_by, last_modified_by)
VALUES (null, 'Produto Capa 3', 1, 3, 3, 30, 300, 0, true, NOW(), NOW(), 'system', 'system');

-- Produto Capa 4
INSERT INTO produto_capa (cod_sistema, description, tipo_produto, medida_unidade, fornecedor_id, minimo, maximo, resuprimento, ativo, created_date, last_modified_date, created_by, last_modified_by)
VALUES (null, 'Produto Capa 4', 2, 4, 2, 40, 400, 0, true, NOW(), NOW(), 'system', 'system');

-- Produto Capa 5
INSERT INTO produto_capa (cod_sistema, description, tipo_produto, medida_unidade, fornecedor_id, minimo, maximo, resuprimento, ativo, created_date, last_modified_date, created_by, last_modified_by)
VALUES (null, 'Produto Capa 5', 0, 5, 3, 50, 500, 1, true, NOW(), NOW(), 'system', 'system');

-- Inserir um registro de ProdutoEntrada
INSERT INTO produto_entrada (numero_nota, data_pedido, data_entrega, preco_compra, quantidade, observacao, produto_capa_id, created_date, last_modified_date, created_by, last_modified_by)
VALUES (12345, '2023-10-08', '2023-10-15', 49.99, 100, 'Este é um produto de exemplo', 1, NOW(), NOW(), 'system', 'system');

-- Inserir outro registro de ProdutoEntrada com valores diferentes
INSERT INTO produto_entrada (numero_nota, data_pedido, data_entrega, preco_compra, quantidade, observacao, produto_capa_id, created_date, last_modified_date, created_by, last_modified_by)
VALUES (67890, '2023-09-15', '2023-09-22', 29.99, 50, 'Este é outro produto de exemplo', 2, NOW(), NOW(), 'system', 'system');

-- Inserir um registro de ProdutoPerda
INSERT INTO produto_perda (data, quantidade, motivo, produto_capa_id, created_date, last_modified_date, created_by, last_modified_by)
VALUES ('2023-10-08', 5, 'Produto danificado', 1, NOW(), NOW(), 'system', 'system');

-- Inserir outro registro de ProdutoPerda com valores diferentes
INSERT INTO produto_perda (data, quantidade, motivo, produto_capa_id, created_date, last_modified_date, created_by, last_modified_by)
VALUES ('2023-09-15', 2, 'Produto vencido', 2, NOW(), NOW(), 'system', 'system');


INSERT
INTO
  unidade_produtiva
  (nome, servico, ativo, created_date, last_modified_date, created_by, last_modified_by)
VALUES
  ('A', 'COSTURA', TRUE, NOW(), NOW(), 'system', 'system');

INSERT
INTO
  unidade_produtiva
  (nome, servico, ativo, created_date, last_modified_date, created_by, last_modified_by)
VALUES
  ('B', 'COSTURA', TRUE, NOW(), NOW(), 'system', 'system');

INSERT
INTO
  unidade_produtiva
  (nome, servico, ativo, created_date, last_modified_date, created_by, last_modified_by)
VALUES
  ('C', 'COSTURA', TRUE, NOW(), NOW(), 'system', 'system');


-- Inserir um registro de ProdutoSaida
INSERT INTO produto_saida (data_saida, quantidade, retirado_por, setor, unidade_produtiva_id, observacao, produto_capa_id, created_date, last_modified_date, created_by, last_modified_by)
VALUES ('2023-10-08', 3, 'João', 2, 1, 'Produto retirado para uso interno', 1, NOW(), NOW(), 'system', 'system');

-- Inserir outro registro de ProdutoSaida com valores diferentes
INSERT INTO produto_saida (data_saida, quantidade, retirado_por, setor, unidade_produtiva_id, observacao, produto_capa_id, created_date, last_modified_date, created_by, last_modified_by)
VALUES ('2023-09-15', 1, 'Maria', 1, 2, 'Produto retirado para teste', 2, NOW(), NOW(), 'system', 'system');

INSERT INTO users (name, password, ativo, created_date, last_modified_date, created_by, last_modified_by)
VALUES ('admin', 'admin', TRUE, NOW(), NOW(), 'system', 'system');

INSERT INTO users (name, password, ativo, created_date, last_modified_date, created_by, last_modified_by)
VALUES ('user', 'user', TRUE, NOW(), NOW(), 'system', 'system');

INSERT INTO users (name, password, ativo, created_date, last_modified_date, created_by, last_modified_by)
VALUES ('user1', 'user2', FALSE, NOW(), NOW(), 'system', 'system');

INSERT INTO profiles (user_id, profiles) VALUES(1, 0);
INSERT INTO profiles (user_id, profiles) VALUES(1, 3);

INSERT INTO profiles (user_id, profiles) VALUES(2, 2);
INSERT INTO profiles (user_id, profiles) VALUES(2, 3);
INSERT INTO profiles (user_id, profiles) VALUES(2, 7);
INSERT INTO profiles (user_id, profiles) VALUES(2, 8);


INSERT INTO profiles (user_id, profiles) VALUES(3, 2);
INSERT INTO profiles (user_id, profiles) VALUES(3, 3);
