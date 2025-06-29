use graybee;

CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id BIGINT DEFAULT NULL,
	FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE SET NULL,
    name VARCHAR(100) NOT NULL UNIQUE,
    slug VARCHAR(100) DEFAULT NULL,
    is_active BOOLEAN DEFAULT TRUE,
	category_type enum('CATEGORY', 'SUBCATEGORY', 'BRAND', 'TAG') default 'CATEGORY',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_parent_id ON categories(parent_id);

CREATE TABLE attributes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    label VARCHAR(100) NOT NULL,
    input_type VARCHAR(30) NOT NULL,
    unit VARCHAR(30) DEFAULT NULL,
    is_required BOOLEAN DEFAULT FALSE,
    options JSON DEFAULT NULL,
	is_active BOOLEAN DEFAULT TRUE
);

create table categories_attributes(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	category_id BIGINT NOT NULL,
	FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
    attribute_id BIGINT NOT NULL,
	FOREIGN KEY (attribute_id) REFERENCES attributes(id) ON DELETE CASCADE
);

CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT DEFAULT NULL,
    brand_id BIGINT DEFAULT NULL,
	FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
	FOREIGN KEY (brand_id) REFERENCES categories(id) ON DELETE SET NULL,
    name VARCHAR(200) NOT NULL UNIQUE,
    slug VARCHAR(200) NOT NULL UNIQUE,
    conditions ENUM('NEW', 'OLD') DEFAULT 'NEW',
    warranty TINYINT NOT NULL,
    price DOUBLE NOT NULL DEFAULT 0 CHECK (price >= 0),
    discount_percent TINYINT DEFAULT 0 check (discount_percent >=0),
    final_price DOUBLE NOT NULL DEFAULT 0 check (final_price >=0),
    thumbnail VARCHAR(255) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('INACTIVE', 'PUBLISHED', 'OUT_OF_STOCK', 'REMOVED', 'PENDING', 'DRAFT', 'COMING_SOON') DEFAULT 'DRAFT'
);

create table products_categories(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
	UNIQUE (product_id, tag_id),
	FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES categories(id) ON DELETE CASCADE
);

CREATE TABLE product_attribute_values (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    attribute_id BIGINT NOT NULL,
    value TEXT DEFAULT NULL,
    UNIQUE (product_id, attribute_id),
	FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (attribute_id) REFERENCES attributes(id) ON DELETE CASCADE
);

create table product_images(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    image_url VARCHAR(256) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

create table product_descriptions(
    product_id BIGINT primary key,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
	description TEXT DEFAULT NULL
);

create table inventories(
    product_id BIGINT primary key,
	FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE cascade,
    quantity INT DEFAULT 0,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uid VARCHAR(15) DEFAULT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('SUPER_ADMIN', 'ADMIN', 'CUSTOMER') NOT NULL DEFAULT 'CUSTOMER',
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    is_super_admin BOOLEAN NOT NULL DEFAULT FALSE,
	last_login_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

insert INTO account(uid, role, email, password, is_active, is_super_admin) values
(2250148810, 'SUPER_ADMIN', 'nam71441@gmail.com', '$2a$10$NKoHGXu9yqwpeH3wQgHQh.Vb81Z2TXaDvC/8tUkzk3F5NtUUweLc6', true, true),
(2250148811, 'CUSTOMER', 'nam714410@gmail.com', '$2a$10$3rig0jbSkNE17sMVd5V.lOgmK87auHYVWKtid5BdK17586meSwOfu', true, true),
(3034561040, 'ADMIN', 'phongtt004@gmail.com','$2a$10$qzpm300aU2hcL.nx4UYDhOO0dcTWh75hVtXEvpjqLWp1Ki0ZtBTPe', true, true);

CREATE TABLE profile (
    account_id BIGINT NOT NULL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) DEFAULT NULL,
    gender enum('MALE', 'FEMALE', 'OTHER') DEFAULT 'OTHER',
    birthday DATE NOT NULL,
	FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);

CREATE TABLE customer (
    account_id BIGINT NOT NULL PRIMARY KEY,
    total_orders INT DEFAULT 0,
	total_spent double DEFAULT 0,
    last_order_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);

CREATE TABLE address (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(account_id) ON DELETE CASCADE,
    recipient_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    district VARCHAR(100) NOT NULL,
    commune VARCHAR(50) NOT NULL,
    is_default BOOLEAN NOT NULL DEFAULT FALSE
);

create table forgot_password(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	account_id BIGINT NOT NULL,
	FOREIGN KEY (account_id) REFERENCES account(id) on delete cascade,
    otp INT NOT NULL,
    is_verify BOOLEAN DEFAULT FALSE,
    expiration TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table favorites(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	account_id BIGINT NOT NULL,
	product_id BIGINT NOT NULL,
	FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
	FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    unique (account_id, product_id)
);

create table review_comments (
	id BIGINT primary key auto_increment,
    product_id BIGINT  NOT NULL,
    account_id BIGINT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
	comment VARCHAR(500) DEFAULT NULL,
    rating int DEFAULT 0 check (rating <= 5),
    published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(20) UNIQUE NOT NULL,
	account_id BIGINT DEFAULT NULL,
	session_id VARCHAR(50) DEFAULT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    email VARCHAR(200) DEFAULT NULL,
    note VARCHAR(200) DEFAULT NULL,
    total_amount DOUBLE NOT NULL DEFAULT 0 CHECK (total_amount >= 0),
    status ENUM('PENDING', 'CONFIRMED', 'PROCESSING', 'CANCELLED', 'RETURNED', 'COMPLETED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE order_details(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) on delete cascade,
    product_id BIGINT NOT NULL,
    unique (product_id, order_id),
    FOREIGN KEY (product_id) REFERENCES products(id) on delete cascade,
    price_at_time DOUBLE NOT NULL DEFAULT 0 CHECK (price_at_time >= 0),
    quantity INT DEFAULT 0 CHECK (quantity > 0),
    subtotal DOUBLE NOT NULL DEFAULT 0 CHECK (subtotal >= 0)
);

create table carts(
	id BIGINT primary key AUTO_INCREMENT,
	account_id BIGINT DEFAULT NULL,
	FOREIGN KEY (account_id) REFERENCES account(id) on delete cascade,
    session_id VARCHAR(50) DEFAULT NULL,
    total_amount DOUBLE NOT NULL DEFAULT 0 CHECK (total_amount >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table cart_items(
	id BIGINT primary key AUTO_INCREMENT,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
	quantity INT DEFAULT 0,
    total_amount DOUBLE NOT NULL DEFAULT 0 CHECK (total_amount >= 0),
	FOREIGN KEY (cart_id) REFERENCES carts(id) on delete cascade,
	FOREIGN KEY (product_id) REFERENCES products(id) on delete cascade
);

CREATE TABLE payments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT  NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    payment_method ENUM('COD', 'BANKING', 'MOMO', 'VNPAY', 'ZALOPAY') DEFAULT 'COD',
    payment_status ENUM('UNPAID', 'PENDING', 'FAILED', 'REFUNDED', 'PAID') DEFAULT 'UNPAID',
    total_amount DOUBLE NOT NULL DEFAULT 0 CHECK (total_amount >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE deliveries (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    shipping_method ENUM('STANDARD', 'ECONOMY', 'FAST') DEFAULT 'STANDARD',
    delivery_type ENUM('HOME_DELIVERY', 'STORE_PICKUP') NOT NULL DEFAULT 'HOME_DELIVERY',
    status ENUM('PENDING', 'SHIPPING', 'DELIVERED', 'FAILED', 'RETURNED') DEFAULT 'PENDING',
    recipient_name VARCHAR(100) NOT NULL,
    recipient_phone VARCHAR(12) NOT NULL,
    shipping_address VARCHAR(200) NOT NULL,
    estimated_delivery_date DATE NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
