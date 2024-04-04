
    create table aviamento (
        id bigserial not null,
        descricao varchar(255),
        primary key (id)
    );

    create table aviamento_aud (
        id bigint not null,
        rev bigint not null,
        revtype smallint,
        descricao varchar(255),
        primary key (rev, id)
    );

    create table combinacao (
        id bigserial not null,
        titulo varchar(255),
        aviamento_id bigint,
        ordem_aviamento_id bigint,
        peca_id bigint,
        primary key (id)
    );

    create table combinacao_aud (
        id bigint not null,
        rev bigint not null,
        revtype smallint,
        titulo varchar(255),
        aviamento_id bigint,
        ordem_aviamento_id bigint,
        peca_id bigint,
        primary key (rev, id)
    );

    alter table if exists fornecedor
       alter column created_date set data type timestamp(6) with time zone;

    alter table if exists fornecedor
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists fornecedor_aud
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists item_ordem_aviamento_aud
       alter column data_saida set data type timestamp(6);

    alter table if exists item_ordem_aviamento_aud
       add column ordem_aviamento_id bigint;

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

    alter table if exists item_ordem_aviamento
       add column ordem_aviamento_id bigint;

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

    create table peca (
        id bigserial not null,
        descricao varchar(255),
        primary key (id)
    );

    create table peca_aud (
        id bigint not null,
        rev bigint not null,
        revtype smallint,
        descricao varchar(255),
        primary key (rev, id)
    );

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

    alter table if exists produto_perda_aud
       alter column quantidade set data type bigint;

    alter table if exists produto_saida_aud
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists produto_saida_aud
       alter column data_saida set data type timestamp(6);

    alter table if exists produto_saida_aud
       alter column quantidade set data type bigint;

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

    alter table if exists produto_perda
       alter column quantidade set data type bigint;

    alter table if exists produto_saida
       alter column created_date set data type timestamp(6) with time zone;

    alter table if exists produto_saida
       alter column last_modified_date set data type timestamp(6) with time zone;

    alter table if exists produto_saida
       alter column data_saida set data type timestamp(6);

    alter table if exists produto_saida
       alter column quantidade set data type bigint;

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

    alter table if exists aviamento_aud
       add constraint FKj247il2v76vrwu0mey68nbkhr
       foreign key (rev)
       references revisao;

    alter table if exists combinacao
       add constraint FKcb43ndeb4qxdd139w542bhpml
       foreign key (aviamento_id)
       references aviamento;

    alter table if exists combinacao
       add constraint FK11bu06i4mgw9wy1phvtq6t77v
       foreign key (ordem_aviamento_id)
       references ordem_aviamento;

    alter table if exists combinacao
       add constraint FK7988m5bkxpu9ncwvfnn6kp825
       foreign key (peca_id)
       references peca;

    alter table if exists combinacao_aud
       add constraint FK8y063jh4d8ysqclsoy2f3c52t
       foreign key (rev)
       references revisao;

    alter table if exists item_ordem_aviamento
       add constraint FKrhupvq7gjoqtx9pb4gbc2d0mb
       foreign key (ordem_aviamento_id)
       references ordem_aviamento;

    alter table if exists item_ordem_aviamento
       add constraint FKo3c54oj7f0ri06w98rq2qbf2c
       foreign key (unidade_produtiva_id)
       references unidade_produtiva;

    alter table if exists peca_aud
       add constraint FKh2tk423p0uni9o7t9i5bxene7
       foreign key (rev)
       references revisao;
