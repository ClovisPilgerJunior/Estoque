-- Inserir um fornecedor ativo
INSERT INTO fornecedor (empresa, nome, tipo_empresa, email, telefone, endereco, ativo)
VALUES ('Ramatex', 'João', 1, 'joao@ramatex.com', '(48) 99999-0000', 'Rua das Palmeiras', true);

-- Inserir um fornecedor inativo
INSERT INTO fornecedor (empresa, nome, tipo_empresa, email, telefone, endereco, ativo)
VALUES ('ABC Corp', 'Maria', 0, 'maria@abc.com', '(123) 456-7890', 'Avenida Principal', false);

-- Inserir outro fornecedor ativo
INSERT INTO fornecedor (empresa, nome, tipo_empresa, email, telefone, endereco, ativo)
VALUES ('XYZ Ltda', 'Carlos', 1, 'carlos@xyz.com', '(987) 654-3210', 'Rua da Indústria', true);

-- Produto Capa 1
INSERT INTO produto_capa (cod_sistema, description, tipo_produto, medida_unidade, fornecedor_id, minimo, maximo, resuprimento, ativo)
VALUES (null, 'Produto Capa 1', 0, 1, 1, 10, 100, 1, false);

-- Produto Capa 2
INSERT INTO produto_capa (cod_sistema, description, tipo_produto, medida_unidade, fornecedor_id, minimo, maximo, resuprimento, ativo)
VALUES (null, 'Produto Capa 2', 0, 2, 2, 20, 200, 2, true);

-- Produto Capa 3
INSERT INTO produto_capa (cod_sistema, description, tipo_produto, medida_unidade, fornecedor_id, minimo, maximo, resuprimento, ativo)
VALUES (null, 'Produto Capa 3', 1, 3, 3, 30, 300, 0, true);

-- Produto Capa 4
INSERT INTO produto_capa (cod_sistema, description, tipo_produto, medida_unidade, fornecedor_id, minimo, maximo, resuprimento, ativo)
VALUES (null, 'Produto Capa 4', 2, 4, 2, 40, 400, 0, true);

-- Produto Capa 5
INSERT INTO produto_capa (cod_sistema, description, tipo_produto, medida_unidade, fornecedor_id, minimo, maximo, resuprimento, ativo)
VALUES (null, 'Produto Capa 5', 0, 5, 3, 50, 500, 1, true);

-- Inserir um registro de ProdutoEntrada
INSERT INTO produto_entrada (numero_nota, data_pedido, data_entrega, preco_compra, quantidade, observacao, produto_capa_id)
VALUES (12345, '2023-10-08', '2023-10-15', 49.99, 100, 'Este é um produto de exemplo', 1);

-- Inserir outro registro de ProdutoEntrada com valores diferentes
INSERT INTO produto_entrada (numero_nota, data_pedido, data_entrega, preco_compra, quantidade, observacao, produto_capa_id)
VALUES (67890, '2023-09-15', '2023-09-22', 29.99, 50, 'Este é outro produto de exemplo', 2);

-- Inserir um registro de ProdutoPerda
INSERT INTO produto_perda (data, quantidade, motivo, produto_capa_id)
VALUES ('2023-10-08', 5, 'Produto danificado', 1);

-- Inserir outro registro de ProdutoPerda com valores diferentes
INSERT INTO produto_perda (data, quantidade, motivo, produto_capa_id)
VALUES ('2023-09-15', 2, 'Produto vencido', 2);


INSERT
INTO
  unidade_produtiva
  (nome, servico, ativo)
VALUES
  ('A', 'COSTURA', TRUE);

INSERT
INTO
  unidade_produtiva
  (nome, servico, ativo)
VALUES
  ('B', 'COSTURA', TRUE);

INSERT
INTO
  unidade_produtiva
  (nome, servico, ativo)
VALUES
  ('C', 'COSTURA', TRUE);


-- Inserir um registro de ProdutoSaida
INSERT INTO produto_saida (data_saida, quantidade, retirado_por, setor, unidade_produtiva_id, observacao, produto_capa_id)
VALUES ('2023-10-08', 3, 'João', 2, 1, 'Produto retirado para uso interno', 1);

-- Inserir outro registro de ProdutoSaida com valores diferentes
INSERT INTO produto_saida (data_saida, quantidade, retirado_por, setor, unidade_produtiva_id, observacao, produto_capa_id)
VALUES ('2023-09-15', 1, 'Maria', 1, 2, 'Produto retirado para teste', 2);

INSERT INTO users (name, password, ativo)
VALUES ('admin', 'admin', TRUE);

INSERT INTO users (name, password, ativo)
VALUES ('user', 'user', TRUE);

INSERT INTO users (name, password, ativo)
VALUES ('user1', 'user2', FALSE);

INSERT INTO profiles (user_id, profiles) VALUES(1, 0);

INSERT INTO profiles (user_id, profiles) VALUES(2, 2);
INSERT INTO profiles (user_id, profiles) VALUES(2, 3);

INSERT INTO profiles (user_id, profiles) VALUES(3, 2);
INSERT INTO profiles (user_id, profiles) VALUES(3, 3);