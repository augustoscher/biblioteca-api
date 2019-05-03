create table tenant (
    uuid varchar(255) primary key,
    tenant_name varchar(255) not null,
    schema_name varchar(255) not null,
    created_at timestamp,
    updated_at timestamp,
    constraint UC_TENANT_NAME unique (tenant_name) ,
    constraint UC_SCHEMA_NAME unique (schema_name)
);

create table usuario (
    uuid varchar(255) primary key,
    tenant_id varchar(255),
    nome varchar(255),
    login varchar(100) not null,
    senha varchar(100) not null,
    admin boolean,
    created_at timestamp,
    updated_at timestamp,
    constraint UC_LOGIN unique (login),
    foreign key (tenant_id) REFERENCES tenant(uuid)
);
    