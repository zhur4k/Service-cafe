create table categories
(
    id                 bigint auto_increment
        primary key,
    categories_name    varchar(255) null,
    parent_category_id bigint       null,
    constraint FK_parent_category_category
        foreign key (parent_category_id) references categories (id)
);

create table products
(
    id               bigint auto_increment
        primary key,
    name             varchar(255) null
);

create table product_stock
(
    id         bigint auto_increment
        primary key,
    price      int    not null,
    weight     int    not null,
    product_id bigint null,
    constraint FK_stock_products_product
        foreign key (product_id) references products(id)
);


create table shift
(
    id             bigint auto_increment
        primary key,
    end_date       datetime(6) null,
    start_date     datetime(6) null,
    state_of_shift bit         not null
);

create table collections
(
    id                bigint auto_increment
        primary key,
    date_of_operation datetime(6) null,
    sum_of_operation  int         null,
    type_of_operation bit         null,
    shift_id          bigint      null,
    constraint FK_shift_collections
        foreign key (shift_id) references shift (id)
);

create table orders
(
    id                 bigint auto_increment
        primary key,
    cash_less_paid     int         not null,
    cash_paid          int         not null,
    date               datetime(6) null,
    establishment_paid int         not null,
    number_of_table    int         not null,
    sum_of_change      int         not null,
    shift_id           bigint      null,
    constraint FK_shift_orders
        foreign key (shift_id) references shift (id)
);

create table cash_register
(
    id           bigint auto_increment
        primary key,
    cash_amount  int    not null,
    collection_id bigint null,
    order_id     bigint null,
    constraint UK_4b75tsgurx07kopc6pltphe1m
        unique (collection_id),
    constraint UK_4kdvi0ogf4ciycan4rbqjd247
        unique (order_id),
    constraint FK_collection_cash_register
        foreign key (collection_id) references collections (id),
    constraint FK_order_cash_register
        foreign key (order_id) references orders (id)
);

create table order_items
(
    id                  bigint auto_increment
        primary key,
    category            varchar(255) null,
    date_of_item_change datetime(6)  null,
    discount            int          not null,
    item                bigint       null,
    name_of_items       varchar(255) null,
    price               int          not null,
    quantity            int          not null,
    type_of_item        bit          null,
    unit                varchar(255) null,
    order_id            bigint       null,
    constraint FK_order_order_items
        foreign key (order_id) references orders (id)
);

create table product_stock_movement
(
    id                bigint auto_increment
        primary key,
    balance_weight    int          not null,
    date_of_operation datetime(6)  null,
    description       varchar(255) null,
    price_movement    int          not null,
    price_on_stock    int          not null,
    product           bigint       null,
    product_name      varchar(255) null,
    type_of_operation varchar(255) null,
    weight            int          not null,
    order_items_id    bigint       null,
    constraint FK_order_items_product_stock_movement
        foreign key (order_items_id) references order_items (id)
);

create table units
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table items
(
    id             bigint auto_increment
        primary key,
    date_of_change datetime(6)  null,
    img            varchar(255) null,
    name_of_items  varchar(255) null,
    price          int          not null,
    type_of_item   bit          null,
    view           bit          null,
    categories_id  bigint       null,
    unit_id        bigint       null,
    constraint FK_unit_items
        foreign key (unit_id) references units (id),
    constraint FK_categories_items
        foreign key (categories_id) references categories (id)
);

create table items_in_item
(
    id             bigint auto_increment
        primary key,
    quantity       int    not null,
    item_id        bigint null,
    parent_item_id bigint null,
    constraint FK_parent_item_items_in_item
        foreign key (parent_item_id) references items (id),
    constraint FK_item_items_in_item
        foreign key (item_id) references items (id)
);

create table product_weight
(
    id         bigint auto_increment
        primary key,
    weight     int    not null,
    item_id    bigint null,
    product_id bigint null,
    constraint FK_product_product_weight
        foreign key (product_id) references products (id),
    constraint FK_item_product_weight
        foreign key (item_id) references items (id)
);

create table users
(
    id       bigint auto_increment
        primary key,
    active   bit          not null,
    chat_id  bigint       not null,
    login    varchar(255) null,
    name     varchar(255) null,
    password varchar(255) null
);

create table shift_users
(
    shift_id bigint not null,
    user_id  bigint not null,
    constraint FK_shift_user
        foreign key (user_id) references users (id),
    constraint FK_user_shift
        foreign key (shift_id) references shift (id)
);

create table user_role
(
    user_id bigint                                  not null,
    roles   enum ('ACCOUNTANT', 'ADMIN', 'CASHIER') null,
    constraint FK_user_role
        foreign key (user_id) references users (id)
);

