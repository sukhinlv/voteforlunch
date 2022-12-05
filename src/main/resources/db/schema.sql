create table MEAL
(
    ID   BIGINT auto_increment primary key,
    NAME VARCHAR(255) not null unique
);

create table RESTAURANT
(
    ID   BIGINT auto_increment primary key,
    NAME VARCHAR(255) not null unique
);

create table MENU
(
    ID            BIGINT auto_increment primary key,
    DATE_OF_MENU  DATE   not null,
    RESTAURANT_ID BIGINT not null,
    constraint UC_MENU_DATE_OF_MENU unique (DATE_OF_MENU, RESTAURANT_ID),
    constraint UC_RESTAURANT_ID foreign key (RESTAURANT_ID) references RESTAURANT on delete cascade
);

create table MEAL_PRICE
(
    ID      BIGINT auto_increment primary key,
    PRICE   INTEGER not null,
    MEAL_ID BIGINT  not null,
    MENU_ID BIGINT  not null,
    constraint UC_MEAL_ID foreign key (MEAL_ID) references MEAL,
    constraint UC_MENU_ID foreign key (MENU_ID) references MENU on delete cascade
);

create table USERS
(
    ID         BIGINT auto_increment primary key,
    EMAIL      VARCHAR(255) not null unique,
    ENABLED    BOOLEAN   default TRUE           not null,
    NAME       VARCHAR(255)                     not null,
    PASSWORD   VARCHAR(255)                     not null,
    REGISTERED TIMESTAMP default LOCALTIMESTAMP not null
);

create table USER_ROLE
(
    USER_ID BIGINT not null,
    ROLE    INTEGER,
    constraint UC_USER_ID foreign key (USER_ID) references USERS
);

create table VOTE
(
    ID            BIGINT auto_increment primary key,
    VOTE_DATE     DATE   not null,
    VOTE_TIME     TIME   not null,
    RESTAURANT_ID BIGINT not null,
    USER_ID       BIGINT not null,
    constraint UC_VOTE_USER_ID_VOTE_DATE unique (USER_ID, VOTE_DATE),
    constraint UC_RESTAURANT_ID foreign key (RESTAURANT_ID) references RESTAURANT on delete cascade,
    constraint UC_USER_ID foreign key (USER_ID) references USERS on delete cascade
);
