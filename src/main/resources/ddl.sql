-- auto-generated definition
create table user
(
    id           int auto_increment
        primary key,
    email        varchar(50)                         not null,
    name         varchar(30)                         not null,
    address      varchar(200)                        null,
    created_time timestamp default CURRENT_TIMESTAMP not null,
    updated_time timestamp default CURRENT_TIMESTAMP not null,
    constraint uidx_phone
        unique (email)
) engine = InnoDB
  DEFAULT CHARSET = utf8mb4;

create index idx_name
    on user (name);

