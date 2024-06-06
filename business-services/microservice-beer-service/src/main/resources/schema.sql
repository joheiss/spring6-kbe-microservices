-- CREATE DATABASE IF NOT EXISTS beerservice CHARACTER
-- SET
--     utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE TABLE if NOT EXISTS beers (
    id serial PRIMARY KEY,
    name varchar(50),
    style varchar(50),
    upc varchar(36),
    quantity_on_hand integer,
    min_on_hand integer,
    quantity_to_brew integer,
    price decimal(16, 2),
    created_at datetime (6),
    updated_at datetime (6),
    version integer
);

CREATE TABLE if NOT EXISTS breweries (
    id serial PRIMARY KEY,
    name varchar(255),
    created_at datetime (6),
    updated_at datetime (6),
    version integer
);