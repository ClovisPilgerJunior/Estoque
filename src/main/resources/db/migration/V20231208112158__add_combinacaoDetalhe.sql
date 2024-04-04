
    alter table if exists combinacao
       add column combinacao_detalhe_id bigint;

    alter table if exists combinacao_aud
       add column combinacao_detalhe_id bigint;

    create table combinacao_detalhe_aud (
        id bigint not null,
        rev bigint not null,
        revtype smallint,
        aviamento varchar(255),
        peca varchar(255),
        primary key (rev, id)
    );

    create table combinacao_detalhe (
        id bigserial not null,
        aviamento varchar(255),
        peca varchar(255),
        primary key (id)
    );

    alter table if exists fornecedor
       alter column created_date set data type timestamp(6) with time zone;

    alter table if exists fornecedor
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists fornecedor_aud
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists item_ordem_aviamento_aud
       alter column data_saida set data type timestamp(6);

    alter table if exists item_ordem_compra_aud
       alter column data_pedido set data type timestamp(6);

    alter table if exists item_ordem_compra_aud
       alter column data_recebimento set data type timestamp(6);

    alter table if exists item_ordem_compra_aud
       alter column preco_compra set data type float(53);

    alter table if exists item_ordem_compra_aud
       alter column quantidade set data type float(53);

    alter table if exists item_ordem_compra_aud
       alter column valor_total_ordem set data type float(53);

    alter table if exists item_ordem_aviamento
       alter column data_saida set data type timestamp(6);

    alter table if exists item_ordem_compra
       alter column data_pedido set data type timestamp(6);

    alter table if exists item_ordem_compra
       alter column data_recebimento set data type timestamp(6);

    alter table if exists item_ordem_compra
       alter column preco_compra set data type float(53);

    alter table if exists item_ordem_compra
       alter column quantidade set data type float(53);

    alter table if exists item_ordem_compra
       alter column valor_total_ordem set data type float(53);

    alter table if exists ordem_aviamento_aud
       alter column data_ordem_aviamento set data type timestamp(6);

    alter table if exists ordem_aviamento_aud
       alter column preco_unitario_ordem_aviamento set data type float(53);

    alter table if exists ordem_compra_aud
       alter column data_emissao set data type timestamp(6);

    alter table if exists ordem_compra_aud
       alter column data_pedido_ordem_compra set data type timestamp(6);


    alter table if exists ordem_compra_aud
       alter column data_recebimento_ordem_compra set data type timestamp(6);

    alter table if exists ordem_compra_aud
       alter column valor_total set data type float(53);

    alter table if exists ordem_aviamento
       alter column data_ordem_aviamento set data type timestamp(6);

    alter table if exists ordem_aviamento
       alter column preco_unitario_ordem_aviamento set data type float(53);

    alter table if exists ordem_compra
       alter column data_emissao set data type timestamp(6);

    alter table if exists ordem_compra
       alter column data_pedido_ordem_compra set data type timestamp(6);

    alter table if exists ordem_compra
       alter column data_recebimento_ordem_compra set data type timestamp(6);

    alter table if exists ordem_compra
       alter column valor_total set data type float(53);

    alter table if exists produto_capa_aud
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists produto_entrada_aud
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists produto_entrada_aud
       alter column data_entrega set data type timestamp(6);

    alter table if exists produto_entrada_aud
       alter column data_pedido set data type timestamp(6);

    alter table if exists produto_entrada_aud
       alter column preco_compra set data type float(53);

    alter table if exists produto_entrada_aud
       alter column quantidade set data type float(53);

    alter table if exists produto_perda_aud
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists produto_perda_aud
       alter column data set data type timestamp(6);

    alter table if exists produto_saida_aud
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists produto_saida_aud
       alter column data_saida set data type timestamp(6);

    alter table if exists produto_capa
       alter column created_date set data type timestamp(6) with time zone;

    alter table if exists produto_capa
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists produto_entrada
       alter column created_date set data type timestamp(6) with time zone;

    alter table if exists produto_entrada
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists produto_entrada
       alter column data_entrega set data type timestamp(6);

    alter table if exists produto_entrada
       alter column data_pedido set data type timestamp(6);

    alter table if exists produto_entrada
       alter column preco_compra set data type float(53);

    alter table if exists produto_entrada
       alter column quantidade set data type float(53);

    alter table if exists produto_perda
       alter column created_date set data type timestamp(6) with time zone;

    alter table if exists produto_perda
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists produto_perda
       alter column data set data type timestamp(6);

    alter table if exists produto_saida
       alter column created_date set data type timestamp(6) with time zone;

    alter table if exists produto_saida
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists produto_saida
       alter column data_saida set data type timestamp(6);

    alter table if exists revisao
       alter column revisao_data set data type timestamp(6);

    alter table if exists unidade_produtiva_aud
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists unidade_produtiva
       alter column created_date set data type timestamp(6) with time zone;

    alter table if exists unidade_produtiva
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists users
       alter column created_date set data type timestamp(6) with time zone;

    alter table if exists users
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists users_aud
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists combinacao
       add constraint FK36dmyr2ktli4nwbk0pdkfosoh
       foreign key (combinacao_detalhe_id)
       references combinacao_detalhe;

    alter table if exists combinacao_detalhe_aud
       add constraint FKdl73l7vs91g9aoc67gdcrq5df
       foreign key (rev)
       references revisao;
