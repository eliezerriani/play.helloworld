# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table produto (
  idproduto                     integer not null,
  descricao                     varchar(100) not null,
  ativo                         integer(1) default 0 not null,
  data_cadastro                 timestamp,
  constraint uq_produto_descricao_idproduto unique (descricao,idproduto),
  constraint pk_produto primary key (idproduto)
);


# --- !Downs

drop table if exists produto;

