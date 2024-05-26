create sequence roles_id_seq
    as integer;

alter sequence roles_id_seq owner to postgres;

create sequence t_user_roles_id_seq
    as integer;

alter sequence t_user_roles_id_seq owner to postgres;

create table t_users
(
    id            serial
        constraint t_users_pk
            primary key,
    firstname     varchar                                       not null,
    lastname      varchar                                       not null,
    email         varchar                                       not null
        constraint t_users_pk2
            unique,
    password      varchar,
    address       varchar,
    cin           varchar default '00000000'::character varying not null,
    creation_date date
);

alter table t_users
    owner to postgres;

create table t_roles
(
    id          integer default nextval('roles_id_seq'::regclass) not null
        constraint roles_pk
            primary key,
    name        varchar                                           not null,
    description varchar
);

alter table t_roles
    owner to postgres;

alter sequence roles_id_seq owned by t_roles.id;

create table t_users_roles
(
    id      integer default nextval('t_user_roles_id_seq'::regclass) not null
        constraint t_user_roles_pk
            primary key,
    user_id integer                                                  not null
        constraint t_user_roles_t_users_id_fk
            references t_users
            on update cascade on delete cascade,
    role_id integer                                                  not null
        constraint t_user_roles_t_roles_id_fk
            references t_roles
);

alter table t_users_roles
    owner to postgres;

alter sequence t_user_roles_id_seq owned by t_users_roles.id;

create table t_discussion
(
    id            serial
        constraint t_discussion_pk
            primary key,
    user_id       integer not null
        constraint t_discussion_t_users_id_fk
            references t_users
            on update cascade on delete cascade,
    creation_date date    not null
);

alter table t_discussion
    owner to postgres;

create table t_question_answer
(
    question      varchar not null,
    answer        varchar not null,
    discussion_id integer not null
        constraint t_question_answer_t_discussion_id_fk
            references t_discussion
            on update cascade on delete cascade,
    id            serial,
    creation_date date
);

alter table t_question_answer
    owner to postgres;

create table t_context
(
    id          serial
        constraint t_context_pk
            primary key,
    pdf_name    varchar not null,
    description varchar
);

alter table t_context
    owner to postgres;