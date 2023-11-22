CREATE TABLE produto_capa (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   cod_sistema INTEGER,
   description VARCHAR(255),
   tipo_produto SMALLINT,
   medida_unidade SMALLINT,
   fornecedor_id BIGINT,
   minimo BIGINT,
   maximo BIGINT,
   resuprimento SMALLINT,
   ativo BOOLEAN NOT NULL,
   CONSTRAINT pk_produtocapa PRIMARY KEY (id)
);

CREATE TABLE fornecedor (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   empresa VARCHAR(255),
   nome VARCHAR(255),
   tipo_empresa SMALLINT,
   email VARCHAR(255),
   telefone VARCHAR(255),
   endereco VARCHAR(255),
   ativo BOOLEAN NOT NULL,
   CONSTRAINT pk_fornecedor PRIMARY KEY (id)
);

CREATE TABLE produto_entrada (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   numero_nota BIGINT,
   data_pedido TIMESTAMP WITHOUT TIME ZONE,
   data_entrega TIMESTAMP WITHOUT TIME ZONE,
   preco_compra DOUBLE PRECISION,
   quantidade BIGINT,
   observacao VARCHAR(255),
   produto_capa_id BIGINT,
   CONSTRAINT pk_produtoentrada PRIMARY KEY (id)
);

ALTER TABLE produto_entrada ADD CONSTRAINT FK_PRODUTOENTRADA_ON_PRODUTO_CAPA_ID FOREIGN KEY (produto_capa_id) REFERENCES produto_capa (id);
ALTER TABLE produto_capa ADD CONSTRAINT FK_PRODUTOCAPA_ON_FORNECEDOR FOREIGN KEY (fornecedor_id) REFERENCES fornecedor (id);
ALTER TABLE fornecedor ADD CONSTRAINT uc_fornecedor_empresa UNIQUE (empresa);

ALTER TABLE produto_capa ADD CONSTRAINT uc_produtocapa_desc UNIQUE (description);




CREATE TABLE produto_perda (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   data TIMESTAMP WITHOUT TIME ZONE,
   quantidade BIGINT,
   motivo VARCHAR(255),
   produto_capa_id BIGINT NOT NULL,
   CONSTRAINT pk_produtoperda PRIMARY KEY (id)
);

ALTER TABLE produto_perda ADD CONSTRAINT FK_PRODUTOPERDA_ON_PRODUTO_CAPA FOREIGN KEY (produto_capa_id) REFERENCES produto_capa (id);

CREATE TABLE unidade_produtiva (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   nome VARCHAR(255),
   servico VARCHAR(255),
   ativo BOOLEAN NOT NULL,
   CONSTRAINT pk_unidadeprodutiva PRIMARY KEY (id)
);

ALTER TABLE unidade_produtiva ADD CONSTRAINT uc_unidadeprodutiva_nome UNIQUE (nome);

CREATE TABLE produto_saida (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   data_saida TIMESTAMP WITHOUT TIME ZONE,
   quantidade BIGINT,
   retirado_por VARCHAR(255),
   setor SMALLINT NOT NULL,
   unidade_produtiva_id BIGINT NOT NULL,
   observacao VARCHAR(255),
   produto_capa_id BIGINT NOT NULL,
   CONSTRAINT pk_produtosaida PRIMARY KEY (id)
);

ALTER TABLE produto_saida ADD CONSTRAINT FK_PRODUTOSAIDA_ON_PRODUTO_CAPA FOREIGN KEY (produto_capa_id) REFERENCES produto_capa (id);

ALTER TABLE produto_saida ADD CONSTRAINT FK_PRODUTOSAIDA_ON_UNIDADE_PRODUTIVA FOREIGN KEY (unidade_produtiva_id) REFERENCES unidade_produtiva (id);


