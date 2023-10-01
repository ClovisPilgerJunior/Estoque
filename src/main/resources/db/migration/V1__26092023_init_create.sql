CREATE TABLE produto_capa (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   desc VARCHAR(255),
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

ALTER TABLE fornecedor ADD CONSTRAINT uc_fornecedor_empresa UNIQUE (empresa);

ALTER TABLE produto_capa ADD CONSTRAINT uc_produtocapa_desc UNIQUE (desc);

ALTER TABLE produto_capa ADD CONSTRAINT FK_PRODUTOCAPA_ON_FORNECEDOR FOREIGN KEY (fornecedor_id) REFERENCES fornecedor (id);

-- Inserir um fornecedor ativo
INSERT INTO fornecedor (empresa, nome, tipo_empresa, email, telefone, endereco, ativo)
VALUES ('Ramatex', 'João', 1, 'joao@ramatex.com', '(48) 99999-0000', 'Rua das Palmeiras', true);

-- Inserir um fornecedor inativo
INSERT INTO fornecedor (empresa, nome, tipo_empresa, email, telefone, endereco, ativo)
VALUES ('ABC Corp', 'Maria', 0, 'maria@abc.com', '(123) 456-7890', 'Avenida Principal', false);

-- Inserir outro fornecedor ativo
INSERT INTO fornecedor (empresa, nome, tipo_empresa, email, telefone, endereco, ativo)
VALUES ('XYZ Ltda', 'Carlos', 1, 'carlos@xyz.com', '(987) 654-3210', 'Rua da Indústria', true);

