
    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;

    create sequence revisao_seq start with 1 increment by 1;

    create table fornecedor (
        ativo boolean not null,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        email varchar(255),
        empresa varchar(255) unique,
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id)
    );

    create table fornecedor_aud (
        ativo boolean,
        revtype smallint,
        tipo_empresa smallint check (tipo_empresa between 0 and 1),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        email varchar(255),
        empresa varchar(255),
        endereco varchar(255),
        last_modified_by varchar(255),
        nome varchar(255),
        telefone varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigint not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id, rev)
    );

    create table item_ordem_compra (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53),
        valor_total_ordem float(53),
        data_pedido timestamp(6),
        data_recebimento timestamp(6),
        id bigserial not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        observacao varchar(255),
        produto_capa_desc varchar(255),
        primary key (id)
    );

    create table ordem_compra_aud (
        revtype smallint,
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigint not null,
        numero_nota_ordem bigint,
        rev bigint not null,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id, rev)
    );

    create table ordem_compra (
        status_ordem smallint check (status_ordem between 0 and 6),
        valor_total float(53),
        data_emissao timestamp(6),
        data_pedido_ordem_compra timestamp(6),
        data_previsao_entrega timestamp(6),
        data_recebimento_ordem_compra timestamp(6),
        fornecedor_id bigint,
        id bigserial not null,
        numero_nota_ordem bigint,
        nome_solicitante varchar(255),
        ordem_observacao varchar(255),
        primary key (id)
    );

    create table produto_capa_aud (
        ativo boolean,
        cod_sistema integer,
        medida_unidade smallint check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        revtype smallint,
        tipo_produto smallint check (tipo_produto between 0 and 2),
        fornecedor_id bigint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        maximo bigint,
        minimo bigint,
        rev bigint not null,
        description varchar(255),
        last_modified_by varchar(255),
        primary key (id, rev)
    );

    create table produto_entrada_aud (
        preco_compra float(53),
        quantidade float(53),
        revtype smallint,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id, rev)
    );

    create table produto_perda_aud (
        revtype smallint,
        data timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id, rev)
    );

    create table produto_saida_aud (
        revtype smallint,
        setor smallint check (setor between 0 and 4),
        data_saida timestamp(6),
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        produto_capa_id bigint,
        quantidade bigint,
        rev bigint not null,
        unidade_produtiva_id bigint,
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id, rev)
    );

    create table produto_capa (
        ativo boolean not null,
        cod_sistema integer,
        medida_unidade smallint not null check (medida_unidade between 0 and 10),
        resuprimento smallint check (resuprimento between 0 and 4),
        tipo_produto smallint not null check (tipo_produto between 0 and 2),
        created_date timestamp(6) with time zone not null,
        fornecedor_id bigint,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        maximo bigint,
        minimo bigint,
        created_by varchar(255),
        description varchar(255) unique,
        last_modified_by varchar(255),
        primary key (id)
    );

    create table produto_entrada (
        preco_compra float(53) check (preco_compra>=0),
        quantidade float(53) check (quantidade>=0),
        created_date timestamp(6) with time zone not null,
        data_entrega timestamp(6),
        data_pedido timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        numero_nota bigint,
        ordem_compra_id bigint,
        produto_capa_id bigint not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        primary key (id)
    );

    create table produto_perda (
        created_date timestamp(6) with time zone not null,
        data timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        created_by varchar(255),
        last_modified_by varchar(255),
        motivo varchar(255),
        primary key (id)
    );

    create table produto_saida (
        setor smallint not null check (setor between 0 and 4),
        created_date timestamp(6) with time zone not null,
        data_saida timestamp(6),
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        produto_capa_id bigint not null,
        quantidade bigint check (quantidade>=0),
        unidade_produtiva_id bigint,
        created_by varchar(255),
        last_modified_by varchar(255),
        observacao varchar(255),
        retirado_por varchar(255),
        primary key (id)
    );

    create table profiles (
        profiles integer,
        user_id integer not null
    );

    create table profiles_aud (
        profiles integer not null,
        revtype smallint,
        user_id integer not null,
        rev bigint not null,
        primary key (profiles, user_id, rev)
    );

    create table revisao (
        revisao_data timestamp(6),
        revisao_id bigint not null,
        ip varchar(255),
        usuario varchar(255),
        primary key (revisao_id)
    );

    create table unidade_produtiva_aud (
        ativo boolean,
        revtype smallint,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        nome varchar(255),
        servico varchar(255),
        primary key (id, rev)
    );

    create table unidade_produtiva (
        ativo boolean not null,
        created_date timestamp(6) with time zone not null,
        id bigserial not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        nome varchar(255) not null unique,
        servico varchar(255),
        primary key (id)
    );

    create table users (
        ativo boolean not null,
        id serial not null,
        created_date timestamp(6) with time zone not null,
        last_modified_date timestamp(6) with time zone not null,
        created_by varchar(255),
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_aud (
        ativo boolean,
        id integer not null,
        revtype smallint,
        last_modified_date timestamp(6) with time zone,
        rev bigint not null,
        last_modified_by varchar(255),
        name varchar(255),
        password varchar(255),
        primary key (id, rev)
    );

    alter table if exists fornecedor_aud 
       add constraint FKb0xtx8so9lipejhgndn56raad 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra_aud 
       add constraint FK5o2t763qutovjpja70u229t0m 
       foreign key (rev) 
       references revisao;

    alter table if exists item_ordem_compra 
       add constraint FKai1gelv99e3n9mgu0abf8t15i 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists item_ordem_compra 
       add constraint FK1qgyhflxmm8o0x0193h8o7056 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists ordem_compra_aud 
       add constraint FK6jwewhiwm9ag79pq3ae4a3txf 
       foreign key (rev) 
       references revisao;

    alter table if exists ordem_compra 
       add constraint FKa322dy1p6asd2rnpi0v1psrcx 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_capa_aud 
       add constraint FKe7csg4mef0e8c20kjoqq3kt9x 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_entrada_aud 
       add constraint FK75ugnwg0y11q5fet0f06pqhum 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_perda_aud 
       add constraint FKhneolsdoqi1ve85ocsaqvnsni 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_saida_aud 
       add constraint FKrx77qrsfsp53rx1indkvo7y49 
       foreign key (rev) 
       references revisao;

    alter table if exists produto_capa 
       add constraint FKdfx9c4dpjcmhhfu489uw99m5b 
       foreign key (fornecedor_id) 
       references fornecedor;

    alter table if exists produto_entrada 
       add constraint FK1gn2u101j9u99jmt7h6an4fj1 
       foreign key (ordem_compra_id) 
       references ordem_compra;

    alter table if exists produto_entrada 
       add constraint FKntyatok8kdnrane299jlh31k5 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_perda 
       add constraint FKeeo483woi23migqtf53xecuh1 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FK6c7jc6irqsdcgkihr2d9pjcdt 
       foreign key (produto_capa_id) 
       references produto_capa;

    alter table if exists produto_saida 
       add constraint FKk1m54px4u5r9s17ocarsoeqjw 
       foreign key (unidade_produtiva_id) 
       references unidade_produtiva;

    alter table if exists profiles 
       add constraint FK410q61iev7klncmpqfuo85ivh 
       foreign key (user_id) 
       references users;

    alter table if exists profiles_aud 
       add constraint FKicabrwem54c318yirowr3s0r 
       foreign key (rev) 
       references revisao;

    alter table if exists unidade_produtiva_aud 
       add constraint FK62t19mbp9qpw4kbfp571tgk6g 
       foreign key (rev) 
       references revisao;

    alter table if exists users_aud 
       add constraint FKm6lm3q3tj9y3t5j5plwywf0qa 
       foreign key (rev) 
       references revisao;
