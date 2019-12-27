
    create table items (
       id bigint not null,
        name varchar(255),
        removed boolean default false,
        quote_id bigint,
        primary key (id)
    )

    create table quote_log (
       id bigint generated by default as identity,
        created_date timestamp,
        error_code integer,
        message varchar(255),
        operation INTEGER,
        quote_id bigint,
        primary key (id)
    )

    create table quotes (
       id bigint not null,
        name varchar(255),
        price double not null,
        removed boolean default false,
        primary key (id)
    )

    create table user (
       id bigint generated by default as identity,
        account_non_expired boolean not null,
        account_non_locked boolean not null,
        credentials_non_expired boolean not null,
        enabled boolean not null,
        password varchar(255),
        username varchar(255) not null,
        primary key (id)
    )

    create table user_custom_authorities (
       user_custom_id bigint not null,
        authorities integer
    )

    alter table quotes 
       add constraint UK8qlopmk0woodfixahyaagup8h unique (name)

    alter table user 
       add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username)

    alter table items 
       add constraint FKbe3iqlqkowix9afty5517q4p4 
       foreign key (quote_id) 
       references quotes

    alter table user_custom_authorities 
       add constraint FKidw3bu5x1wix05rpurl81r4f3 
       foreign key (user_custom_id) 
       references user