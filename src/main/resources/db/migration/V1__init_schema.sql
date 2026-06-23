-- Shivam Printers Database Schema

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(500),
    city VARCHAR(100),
    state VARCHAR(100),
    profile_image_url VARCHAR(500),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
    created_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE services (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE,
    slug VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    short_description VARCHAR(500),
    icon VARCHAR(100),
    base_price NUMERIC(12, 2),
    image_url VARCHAR(500),
    featured BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    sort_order INTEGER DEFAULT 0,
    created_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE portfolio_categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    slug VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    created_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE portfolios (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    slug VARCHAR(200) NOT NULL UNIQUE,
    description TEXT,
    image_url VARCHAR(500) NOT NULL,
    thumbnail_url VARCHAR(500),
    client VARCHAR(150),
    project_date TIMESTAMPTZ,
    featured BOOLEAN NOT NULL DEFAULT FALSE,
    published BOOLEAN NOT NULL DEFAULT TRUE,
    category_id BIGINT REFERENCES portfolio_categories(id),
    created_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    customer_id BIGINT NOT NULL REFERENCES users(id),
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    total_amount NUMERIC(12, 2) NOT NULL,
    notes TEXT,
    requirement_file_url VARCHAR(500),
    expected_delivery_date TIMESTAMPTZ,
    created_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    service_id BIGINT REFERENCES services(id),
    service_name VARCHAR(200) NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price NUMERIC(12, 2) NOT NULL,
    total_price NUMERIC(12, 2) NOT NULL,
    specifications TEXT,
    created_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE invoices (
    id BIGSERIAL PRIMARY KEY,
    invoice_number VARCHAR(50) NOT NULL UNIQUE,
    order_id BIGINT NOT NULL UNIQUE REFERENCES orders(id),
    customer_id BIGINT NOT NULL REFERENCES users(id),
    status VARCHAR(30) NOT NULL DEFAULT 'ISSUED',
    subtotal NUMERIC(12, 2) NOT NULL,
    tax_amount NUMERIC(12, 2) DEFAULT 0,
    total_amount NUMERIC(12, 2) NOT NULL,
    pdf_url VARCHAR(500),
    due_date TIMESTAMPTZ,
    paid_date TIMESTAMPTZ,
    created_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE testimonials (
    id BIGSERIAL PRIMARY KEY,
    client_name VARCHAR(150) NOT NULL,
    company VARCHAR(150),
    designation VARCHAR(150),
    content TEXT NOT NULL,
    avatar_url VARCHAR(500),
    rating INTEGER NOT NULL DEFAULT 5,
    featured BOOLEAN NOT NULL DEFAULT FALSE,
    published BOOLEAN NOT NULL DEFAULT TRUE,
    created_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE blog_posts (
    id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(250) NOT NULL,
    slug VARCHAR(250) NOT NULL UNIQUE,
    excerpt TEXT,
    content TEXT NOT NULL,
    cover_image_url VARCHAR(500),
    author VARCHAR(100),
    tags VARCHAR(500),
    published BOOLEAN NOT NULL DEFAULT FALSE,
    published_at TIMESTAMPTZ,
    created_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE contact_inquiries (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL,
    phone VARCHAR(20),
    subject VARCHAR(150),
    message TEXT NOT NULL,
    service VARCHAR(100),
    status VARCHAR(30) NOT NULL DEFAULT 'NEW',
    created_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    link VARCHAR(500),
    read BOOLEAN NOT NULL DEFAULT FALSE,
    created_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_orders_customer ON orders(customer_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_portfolios_category ON portfolios(category_id);
CREATE INDEX idx_portfolios_featured ON portfolios(featured);
CREATE INDEX idx_blog_posts_slug ON blog_posts(slug);
CREATE INDEX idx_services_slug ON services(slug);
CREATE INDEX idx_notifications_user ON notifications(user_id);
