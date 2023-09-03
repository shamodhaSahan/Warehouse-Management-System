Drop database if exists wms;
create database if not exists wms;
Show databases;
Use wms;


create table operator
(
    operatorId   varchar(4)  not null primary key,
    operatorName varchar(50) null,
    password     varchar(25) null,
    nic          varchar(15) null,
    address      text        null,
    email        varchar(50) null
);

create table user
(
    userId    varchar(4)  not null primary key,
    name      varchar(50) null,
    address   text        null,
    nic       varchar(15) null,
    telNumber varchar(15) null,
    email     varchar(50) null
);

create table warehousestatus
(
    whsId      varchar(4)    not null,
    fullLength double        null,
    fullWidth  double        null,
    fullPrice  decimal(20, 2) null,
    constraint primary key (whsId)
);

create table warehouse
(
    warehouseCode varchar(4) not null,
    userId        varchar(4) not null,
    whsId         varchar(4) not null,
    length        double     null,
    width         double     null,
    receiveDate   date       null,
    description   text       null,
    primary key (warehouseCode,userId,whsId),
    constraint foreign key (userId) references user (userId)
            on update cascade on delete cascade,
    constraint foreign key (whsId) references warehousestatus (whsId)
            on update cascade on delete cascade
);

create table `return`
(
    warehouseCode varchar(4)    not null primary key,
    rerurnedDate  date          null,
    price         decimal(20, 2) null,
    constraint foreign key (warehouseCode) references warehouse (warehouseCode)
        on update cascade on delete cascade
);