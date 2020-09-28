create table tbl_revinfo
(
    id         bigserial not null
        constraint pk_revinfo
            primary key,
    ip         varchar(20),
    timestamp  bigint    not null,
    cd_usu_atu varchar(15)
);

create table tbl_configuracao
(
    id         bigserial   not null
        constraint pk_configuracao
            primary key,
    nome       varchar(25) not null
        constraint uk_configuracao_nome
            unique,
    valor      varchar(50) not null,
    id_sit     varchar(1),
    dh_atu     timestamp   not null,
    cd_usu_atu varchar(25) not null
);

create table tbl_configuracao_aud
(
    id         bigint not null,
    rev        bigint not null
        constraint fk_configuracao_aud
            references tbl_revinfo,
    revtype    smallint,
    nome       varchar(25),
    valor      varchar(50),
    cd_usu_atu varchar(25),
    dh_atu     timestamp,
    id_sit     varchar(1),
    constraint pk_configuracao_aud
        primary key (id, rev)
);

create table tbl_log_transacional
(
    id           bigserial   not null
        constraint pk_log_transacional
            primary key,
    chave        bigint,
    dominio      varchar(15),
    operacao     varchar(15),
    rev          bigint
        constraint fk_log_transacional_aud
            references tbl_revinfo,
    req_path     varchar(200),
    resp_status  smallint,
    resp_errors  varchar(255),
    resp_time_ms int,
    req_method   varchar(10),
    req_query    varchar(10),
    exception    text,
    ip           varchar(20),
    trace_id     varchar(15) not null,
    dh_ini       timestamp   not null,
    dh_fim       timestamp   not null,
    cd_usu       varchar(25) not null
);

create sequence hibernate_sequence;


