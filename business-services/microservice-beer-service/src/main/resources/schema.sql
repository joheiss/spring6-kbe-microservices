CREATE TABLE if NOT EXISTS beers (
    id varchar(36) not null PRIMARY KEY,
    name varchar(50),
    style varchar(50),
    upc varchar(36),
    quantity_on_hand integer,
    price decimal(16, 2),
    created_at datetime (6),
    updated_at datetime (6),
    version integer
);

CREATE TABLE if NOT EXISTS breweries (
    id varchar(36) not null PRIMARY KEY,
    name varchar(255),
    created_at datetime (6),
    updated_at datetime (6),
    version integer
);