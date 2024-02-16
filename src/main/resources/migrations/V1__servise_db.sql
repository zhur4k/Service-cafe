create table categories
(
    id                 bigint auto_increment
        primary key,
    categories_name    varchar(255) null,
    parent_category_id bigint       null,
    constraint FK9il7y6fehxwunjeepq0n7g5rd
        foreign key (parent_category_id) references categories (id)
);

create table product_stock
(
    price      int    not null,
    weight     int    not null,
    id         bigint auto_increment
        primary key,
    product_id bigint null,
    constraint UK_hj4kvinsv4h5gi8xi09xbdl46
        unique (product_id)
);

create table products
(
    id               bigint auto_increment
        primary key,
    product_stock_id bigint       null,
    name             varchar(255) null,
    constraint UK_65kldw1nc93dejxd0fw5fad2b
        unique (product_stock_id),
    constraint FK9egwgg5jubb4jbcqfdxwio9m9
        foreign key (product_stock_id) references product_stock (id)
);

alter table product_stock
    add constraint FKpg19826xp8orgacfm7s1lol6v
        foreign key (product_id) references products (id);

create table shift
(
    state_of_shift bit         not null,
    end_date       datetime(6) null,
    id             bigint auto_increment
        primary key,
    start_date     datetime(6) null
);

create table collections
(
    sum_of_operation  int         null,
    type_of_operation bit         null,
    date_of_operation datetime(6) null,
    id                bigint auto_increment
        primary key,
    shift_id          bigint      null,
    constraint FK5u8kt2l90w72k6ufvnsajlwcg
        foreign key (shift_id) references shift (id)
);

create table orders
(
    cash_less_paid     int         not null,
    cash_paid          int         not null,
    establishment_paid int         not null,
    number_of_table    int         not null,
    sum_of_change      int         not null,
    date               datetime(6) null,
    id                 bigint auto_increment
        primary key,
    shift_id           bigint      null,
    constraint FK5jt6rk6bhf5yj4rdycbakdvsv
        foreign key (shift_id) references shift (id)
);

create table cash_register
(
    cash_amount  int    not null,
    colection_id bigint null,
    id           bigint auto_increment
        primary key,
    order_id     bigint null,
    constraint UK_4b75tsgurx07kopc6pltphe1m
        unique (colection_id),
    constraint UK_4kdvi0ogf4ciycan4rbqjd247
        unique (order_id),
    constraint FK6bt8q9l7pqgj2nrad9ki1wcl8
        foreign key (colection_id) references collections (id),
    constraint FK7bql4t1gnr1lqsm368l00y0q2
        foreign key (order_id) references orders (id)
);

create table units
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table items
(
    price          int          not null,
    type_of_item   bit          null,
    unit_price     int          not null,
    view           bit          null,
    categories_id  bigint       null,
    date_of_change datetime(6)  null,
    id             bigint auto_increment
        primary key,
    unit_id        bigint       null,
    unique_code    varchar(13)  null,
    code           varchar(255) null,
    img            varchar(255) null,
    name_of_items  varchar(255) null,
    constraint FKhujkfxarw6632hriim8qxq4bk
        foreign key (unit_id) references units (id),
    constraint FKsptnnkmn559targf3jy160dtt
        foreign key (categories_id) references categories (id)
);

create table items_in_item
(
    quantity       int    not null,
    id             bigint auto_increment
        primary key,
    item_id        bigint null,
    parent_item_id bigint null,
    constraint FKcqxtixp941uawl0b9538p1j3y
        foreign key (parent_item_id) references items (id),
    constraint FKjwyusnlyfuav1u8kc4fq1jp3r
        foreign key (item_id) references items (id)
);

create table order_items
(
    discount            int         not null,
    markup              int         not null,
    price               int         not null,
    quantity            int         not null,
    date_of_item_change datetime(6) null,
    id                  bigint auto_increment
        primary key,
    items_id            bigint      null,
    order_id            bigint      null,
    constraint FKbioxgbv59vetrxe0ejfubep1w
        foreign key (order_id) references orders (id),
    constraint FKtmh8xasqlekqomdmqn7kc59wq
        foreign key (items_id) references items (id)
);

create table product_stock_movement
(
    balance_weight    int          not null,
    price_movement    int          not null,
    price_on_stock    int          not null,
    weight            int          not null,
    date_of_operation datetime(6)  null,
    id                bigint auto_increment
        primary key,
    order_item_id     bigint       null,
    product_id        bigint       null,
    description       varchar(255) null,
    type_of_operation varchar(255) null,
    constraint FKdphfk6b6tx5wto99xvj4i99jm
        foreign key (product_id) references products (id),
    constraint FKk8hoid3ds6y5d1qrauxmic1ig
        foreign key (order_item_id) references order_items (id)
);

create table product_weight
(
    weight     int    not null,
    id         bigint auto_increment
        primary key,
    item_id    bigint null,
    product_id bigint null,
    constraint FK9k3rl8gv4nig66q03j6jybplf
        foreign key (product_id) references products (id),
    constraint FKmjybumttlggu5gjbq25diimjc
        foreign key (item_id) references items (id)
);

create table users
(
    active   bit          not null,
    chat_id  bigint       not null,
    id       bigint auto_increment
        primary key,
    login    varchar(255) null,
    name     varchar(255) null,
    password varchar(255) null
);

create table shift_users
(
    shift_id bigint not null,
    user_id  bigint not null,
    constraint FKcf9vcwpfi2cvnh0tg1la2el78
        foreign key (user_id) references users (id),
    constraint FKga8b1fsvdkvdkxwg1gvdc4s00
        foreign key (shift_id) references shift (id)
);

create table user_role
(
    user_id bigint                                  not null,
    roles   enum ('ACCOUNTANT', 'ADMIN', 'CASHIER') null,
    constraint FKj345gk1bovqvfame88rcx7yyx
        foreign key (user_id) references users (id)
);

