SET GLOBAL host_cache_size = 0;

CREATE DATABASE IF NOT EXISTS graybee;

use graybee;

CREATE TABLE regex_patterns(
	id int unsigned primary key auto_increment,
    name varchar(50) not null unique,
    pattern varchar(200) not null,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO regex_patterns (name, pattern) VALUES
('cpu', 'i[3579](-|\\s)?\\d{4,5}[A-Za-z]*|ryzen\\s?\\d{3,5}[0-9a-zA-Z]*'),
('motherboard', '[A-Z]+\\d{3,4}[A-Za-z,a-z]*'),
('vga', '[Rr][Tt][Xx] \\d{4}|[Gg][Tt][Xx] \\d{3,4}|[Rr][Xx] \\d{3,4}|[Gg][Tt] \\d{3,4}'),
('ram', '\\d+GB'),
('ssd', '\\d+GB'),
('storage', '\\d+GB');

CREATE TABLE categories (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(35) NOT NULL UNIQUE,
	status ENUM('ACTIVE', 'INACTIVE', 'DELETED', 'DRAFT', 'PENDING') default 'PENDING',
    product_count int unsigned default 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE manufacturers (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(35) NOT NULL UNIQUE,
	status ENUM('ACTIVE', 'INACTIVE', 'DELETED', 'DRAFT', 'PENDING') default 'PENDING',
	product_count int unsigned default 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE categories_manufacturers (
    id INT unsigned PRIMARY KEY AUTO_INCREMENT,
    category_id INT UNSIGNED NOT NULL,
    manufacturer_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(id) ON DELETE CASCADE,
	UNIQUE (category_id, manufacturer_id)
);

DELIMITER $$
CREATE TRIGGER update_category_count
AFTER DELETE ON products
FOR EACH ROW
BEGIN
    UPDATE categories c
    SET product_count = (
        SELECT COUNT(*)
        FROM products p
        WHERE p.category_id = OLD.category_id
    )
    WHERE id = OLD.category_id;
END $$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER update_manufacturer_count
AFTER DELETE ON products
FOR EACH ROW
BEGIN
    UPDATE manufacturers m
    SET product_count = (
        SELECT COUNT(*)
        FROM products p
        WHERE p.manufacturer_id = OLD.manufacturer_id
    )
    WHERE id = OLD.manufacturer_id;
END $$
DELIMITER ;

CREATE TABLE subcategories(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(35) NOT NULL UNIQUE,
	status ENUM('ACTIVE', 'INACTIVE', 'DELETED', 'DRAFT', 'PENDING') default 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE categories_subcategories (
    id INT unsigned PRIMARY KEY AUTO_INCREMENT,
    category_id INT unsigned NOT NULL,
    subcategory_id INT unsigned NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
    FOREIGN KEY (subcategory_id) REFERENCES subcategories(id) ON DELETE CASCADE,
    UNIQUE (category_id, subcategory_id)
);

CREATE TABLE tags(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE
);

create table subcategories_tags(
	id INT unsigned PRIMARY KEY AUTO_INCREMENT,
    subcategory_id INT unsigned NOT NULL,
    tag_id INT unsigned NOT NULL,
    FOREIGN KEY (subcategory_id) REFERENCES subcategories(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE,
    UNIQUE (subcategory_id, tag_id)
);

create table product_sequence(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	category_code varchar(25) not null,
    yearmonth varchar(10) not null,
    last_number int UNSIGNED not null
);

CREATE TABLE products (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    category_id INT UNSIGNED not NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id) ,
    manufacturer_id INT UNSIGNED not NULL,
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(id),
	code varchar(100) not null unique,
    name VARCHAR(200) NOT NULL UNIQUE,
    conditions ENUM('NEW', 'OLD') DEFAULT 'NEW',
    warranty TINYINT UNSIGNED NOT NULL,
    weight FLOAT DEFAULT 0,
    dimension VARCHAR(50) DEFAULT NULL,
    price DECIMAL(10,2) NOT NULL DEFAULT 0.00 CHECK (price >= 0.00),
    discount_percent tinyint unsigned default 0 check (discount_percent >=0),
    final_price DECIMAL(10,2) NOT NULL default 0.00 check (final_price >=0.00),
    color VARCHAR(35) DEFAULT NULL,
    in_stock tinyint default 0,
    thumbnail varchar(250) default null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE', 'PUBLISHED', 'OUT_OF_STOCK', 'DELETED', 'PENDING', 'DRAFT', 'COMING_SOON') default 'DRAFT'
);

create table product_statistics(
	id int unsigned primary key auto_increment,
	product_id BIGINT UNSIGNED NOT NULL UNIQUE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
	view_count int unsigned default 0,
    purchase_count int unsigned default 0,
    has_promotion tinyint default 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE carousel_group (
    id int unsigned PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) unique,
    type varchar(35) not null,
    category_name VARCHAR(35) default null,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE carousel_items (
    id int unsigned PRIMARY KEY AUTO_INCREMENT,
    carousel_group_id int unsigned not null,
    product_id BIGINT UNSIGNED NOT NULL,
    position INT DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,
	FOREIGN KEY (carousel_group_id) REFERENCES carousel_group(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    unique(carousel_group_id, product_id)
);

create table products_tags(
	id INT unsigned PRIMARY KEY AUTO_INCREMENT,
    product_id bigint unsigned NOT NULL,
    tag_id INT unsigned NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE,
    UNIQUE (product_id, tag_id)
);

create table products_subcategories(
	id INT unsigned PRIMARY KEY AUTO_INCREMENT,
    product_id bigint unsigned NOT NULL,
    subcategory_id INT unsigned NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (subcategory_id) REFERENCES subcategories(id) ON DELETE CASCADE,
    UNIQUE (product_id, subcategory_id)
);

create table product_descriptions(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT UNSIGNED NOT NULL UNIQUE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
	description text default null
);

CREATE TABLE product_images (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT UNSIGNED NOT NULL UNIQUE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    image_url VARCHAR(500) DEFAULT NULL
);

create table promotions(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name varchar(150) not null,
    price_value double  default 0 check(price_value >= 0),
    quantity tinyint default 1
);

create table inventories(
	id int unsigned primary key auto_increment,
    product_id bigint unsigned not null unique,
	FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE cascade,
    quantity int not null default 0,
    status ENUM('ACTIVE', 'INACTIVE', 'OUT_OF_STOCK', 'DELETED') default 'ACTIVE',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE roles(
 	id tinyint unsigned primary key auto_increment,
	name varchar(20) not null unique,
	user_count int unsigned default 0,
	status ENUM('ACTIVE', 'INACTIVE', 'DELETED') default 'ACTIVE',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
 );

 CREATE TABLE permissions (
    id smallint unsigned PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description varchar(150) default null,
    user_count int unsigned default 0,
	status ENUM('ACTIVE', 'INACTIVE', 'DELETED') default 'ACTIVE',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE users (
    id INT unsigned PRIMARY KEY AUTO_INCREMENT,
    uid int unsigned not null unique,
    role_id tinyint unsigned NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id),
    fullname VARCHAR(100) DEFAULT null,
    phone_number VARCHAR(12) UNIQUE NOT NULL,
	email VARCHAR(50) UNIQUE default NULL,
    password VARCHAR(250) NOT NULL,
    date_of_birth DATE DEFAULT NULL,
    gender enum('MALE', 'FEMALE') default null,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	is_active BOOLEAN DEFAULT FALSE,
	status enum('ACTIVE', 'INACTIVE', 'DELETED', 'OFFLINE', 'ONLINE', 'BANNED', 'PENDING') default 'PENDING'
);

create table favourites(
	id INT unsigned PRIMARY KEY AUTO_INCREMENT,
	user_uid INT unsigned,
	product_id bigint unsigned,
	FOREIGN KEY (user_uid) REFERENCES users(uid) ON DELETE CASCADE,
	FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    unique (user_uid, product_id)
);

create table address(
	id int unsigned primary key auto_increment,
	user_uid int unsigned default null,
    session_id varchar(50) default null,
    fullname varchar(50) not null,
    phone_number varchar(12) not null,
    city varchar(50) not null,
    district varchar(100) not null,
    commune varchar(50) not null,
    street_address varchar(150) not null,
    is_default BOOLEAN DEFAULT FALSE,
	FOREIGN KEY (user_uid) REFERENCES users(uid) ON DELETE CASCADE
);

create table review_comments (
	id int unsigned primary key auto_increment,
    product_id bigint unsigned not null,
    user_id int unsigned not null,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
	comment varchar(500) default null,
    rating float default 0 check (rating <=5),
    published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles_permissions (
	id tinyint unsigned primary key auto_increment,
    role_id tinyint unsigned NOT NULL,
    permission_id smallint unsigned NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE,
    unique (role_id, permission_id)
);

CREATE TABLE users_permissions (
	id int unsigned primary key auto_increment,
    user_id int unsigned NOT NULL,
    permission_id smallint unsigned NOT NULL,
    unique (user_id, permission_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions (id) ON DELETE CASCADE
);

CREATE TABLE discounts (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL UNIQUE,
    discount_value DECIMAL(10,2) NOT NULL DEFAULT 0.00 CHECK (discount_value >= 0.00),
    discount_type ENUM('PERCENT', 'FIXED') DEFAULT 'FIXED',
    description varchar(100) default null,
    expiration TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE', 'COMING_SOON', 'EXPIRES') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    user_uid INT UNSIGNED DEFAULT NULL,
	session_id varchar(50) default null,
    FOREIGN KEY (user_uid) REFERENCES users(uid) ON DELETE CASCADE,
    is_guest BOOLEAN DEFAULT FALSE,
    address_id int unsigned default NULL,
    FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE SET NULL,
    note VARCHAR(200) DEFAULT NULL,
    discount_id int unsigned DEFAULT NULL,
	FOREIGN KEY (discount_id) REFERENCES discounts(id) ON DELETE SET NULL,
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00 CHECK (total_amount >= 0.00),
    issue_invoices BOOLEAN DEFAULT FALSE,
    status ENUM('PENDING', 'CONFIRMED', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED', 'RETURNED', 'COMPLETED') NOT NULL DEFAULT 'PENDING',
    is_confirmed boolean default false,
    is_cancelled boolean default false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE order_details(
	id bigint unsigned PRIMARY KEY AUTO_INCREMENT,
    order_id bigint unsigned NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) on delete cascade,
    product_id bigint unsigned NOT NULL,
    unique (product_id, order_id),
    FOREIGN KEY (product_id) REFERENCES products(id) on delete cascade,
    price_at_time DECIMAL(10,2) NOT NULL DEFAULT 0.00 CHECK (price_at_time >= 0.00),
    quantity INT DEFAULT 0 CHECK (quantity > 0),
    subtotal DECIMAL(10,2) NOT NULL DEFAULT 0.00 CHECK (subtotal >= 0.00)
);

create table carts(
	id int unsigned primary key AUTO_INCREMENT,
    user_uid int unsigned default null,
	FOREIGN KEY (user_uid) REFERENCES users(uid) on delete set null,
    session_id varchar(50) default null,
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00 CHECK (total_amount >= 0.00),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table cart_items(
	id int unsigned primary key AUTO_INCREMENT,
    cart_id int unsigned not null,
    product_id bigint unsigned not null,
	quantity INT DEFAULT 0,
    total DECIMAL(10,2) NOT NULL DEFAULT 0.00 CHECK (total >= 0.00),
	FOREIGN KEY (cart_id) REFERENCES carts(id) on delete cascade,
	FOREIGN KEY (product_id) REFERENCES products(id) on delete cascade
);

DELIMITER $$
CREATE TRIGGER update_cart_total_on_empty
AFTER DELETE ON cart_items
FOR EACH ROW
BEGIN
    DECLARE item_count INT;

    SELECT COUNT(*) INTO item_count FROM cart_items WHERE cart_id = OLD.cart_id;

    IF item_count = 0 THEN
        UPDATE carts
        SET total_amount = 0
        WHERE id = OLD.cart_id;
    END IF;
END $$
DELIMITER ;

CREATE TABLE payments (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    payment_method ENUM('COD', 'BANKING', 'MOMO', 'VNPAY', 'ZALOPAY') DEFAULT 'COD',
    payment_status ENUM('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED', 'PAID') DEFAULT 'PENDING',
    transaction_id VARCHAR(20) UNIQUE DEFAULT NULL,
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00 CHECK (total_amount >= 0.00),
    currency_code CHAR(3) DEFAULT 'VND',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE deliveries (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    tracking_number VARCHAR(20) UNIQUE DEFAULT NULL,
    shipping_method ENUM('STANDARD_SHIPPING', 'ECONOMY_SHIPPING', 'FAST_DELIVERY') DEFAULT 'STANDARD_SHIPPING',
    delivery_type ENUM('HOME_DELIVERY', 'STORE_PICKUP') NOT NULL DEFAULT 'HOME_DELIVERY',
    delivery_status ENUM('PENDING', 'SHIPPED', 'IN_TRANSIT', 'DELIVERED', 'CANCELLED', 'COMPLETED') DEFAULT 'PENDING',
    shipping_address VARCHAR(200) NOT NULL,
    estimated_delivery_date DATE NOT NULL,
	order_date DATE NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
