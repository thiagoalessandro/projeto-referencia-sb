create table tbl_configuracao
(
    id         bigserial not null
        constraint tbl_configuracao_pkey
            primary key,
    nome       varchar(25),
    valor      varchar(255),
    cd_usu_atu varchar(25) not null,
    dh_atu     timestamp not null,
    id_sit     varchar(1) not null,
    constraint uk_configuracao_nome unique (nome)
);

create table tbl_log_trace
(
    id         bigserial not null
        constraint tbl_log_trace_pkey
            primary key,
    dominio    varchar(15),
    operacao   varchar(15),
    chave      bigint,
    trace_id   varchar(15),
    cd_usu_atu varchar(25) not null,
    dh_atu     timestamp not null
);

create index idx_trace_id on tbl_log_trace (trace_id);
