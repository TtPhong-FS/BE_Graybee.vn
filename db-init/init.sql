SET GLOBAL host_cache_size = 0;

CREATE DATABASE IF NOT EXISTS graybee;

use graybee;

CREATE TABLE IF NOT EXISTS regex_patterns(
	id int unsigned primary key auto_increment,
    type_name varchar(50) not null unique,
    pattern varchar(200) not null,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO regex_patterns (type_name, pattern) VALUES
('cpu', 'i[3579](-|\\s)?\\d{4,5}[A-Za-z]*|ryzen\\s?\\d{3,5}[0-9a-zA-Z]*'),
('motherboard', '[A-Z]+\\d{3,4}[A-Za-z,a-z]*'),
('vga', '[Rr][Tt][Xx] \\d{4}|[Gg][Tt][Xx] \\d{3,4}|[Rr][Xx] \\d{3,4}|[Gg][Tt] \\d{3,4}'),
('ram', '\\d+GB'),
('ssd', '\\d+GB'),
('storage', '\\d+GB');


CREATE TABLE IF NOT EXISTS categories (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(35) NOT NULL UNIQUE,
    status VARCHAR(30) NOT NULL,
    product_count int unsigned default 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS manufacturers (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    manufacturer_name VARCHAR(50) NOT NULL UNIQUE,
    status VARCHAR(30) NOT NULL,
	product_count int unsigned default 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

