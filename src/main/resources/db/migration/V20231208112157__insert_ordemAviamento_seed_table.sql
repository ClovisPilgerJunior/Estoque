INSERT
INTO
  ordem_aviamento
  (numeroop, data_ordem_aviamento, desc_ordem_aviamento, quantidade_ordem_aviamento, preco_unitario_ordem_aviamento, tecido_ordem_aviamento)
VALUES
  (34764, NOW(), 'FJQ01042024 TESTE', 200, 12, 'FLEECE');

INSERT
INTO
  ordem_aviamento
  (numeroop, data_ordem_aviamento, desc_ordem_aviamento, quantidade_ordem_aviamento, preco_unitario_ordem_aviamento, tecido_ordem_aviamento)
VALUES
  (34765, NOW(), 'FJQ01042024 TESTE 2', 100, 10, 'ROMANTIC');

INSERT
INTO
  item_ordem_aviamento
  (produto_capa_id, data_saida, quantidade, retirado_por, setor, unidade_produtiva_id, observacao, ordem_aviamento_id)
VALUES
  (915, NOW(), 20, 'Jose', 0, 1, 'teste', 1);

INSERT
INTO
  item_ordem_aviamento
  (produto_capa_id, data_saida, quantidade, retirado_por, setor, unidade_produtiva_id, observacao, ordem_aviamento_id)
VALUES
  (910, NOW(), 180, 'Jose', 0, 1, 'teste 2', 1);

INSERT
INTO
  aviamento
  (descricao)
VALUES
  ('MARROM');

INSERT
INTO
  aviamento
  (descricao)
VALUES
  ('MARROM');

INSERT
INTO
  peca
  (descricao)
VALUES
  ('CARAMELO - 2745');

INSERT
INTO
  peca
  (descricao)
VALUES
  ('BEGE CLARO - 296');

INSERT
INTO
  combinacao
  (titulo, peca_id, aviamento_id, ordem_aviamento_id)
VALUES
  ('BANDERINHA', 1, 1, 1);